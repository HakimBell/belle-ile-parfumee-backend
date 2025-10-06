package com.belleileperfumee.belle_ile_parfumee.controller;

import com.belleileperfumee.belle_ile_parfumee.entity.Account;
import com.belleileperfumee.belle_ile_parfumee.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Inscription - Créer un nouveau compte
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Account account) {
        Map<String, String> response = new HashMap<>();

        // Vérifier si l'email existe déjà
        if (accountService.emailExists(account.getEmail())) {
            response.put("message", "Email déjà utilisé");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        Account createdAccount = accountService.createAccount(account);
        if (createdAccount != null) {
            response.put("message", "Compte créé avec succès");
            response.put("email", createdAccount.getEmail());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        response.put("message", "Erreur lors de la création du compte");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Connexion - Vérifier email et mot de passe
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        Map<String, String> response = new HashMap<>();

        String email = credentials.get("email");
        String password = credentials.get("password");

        Account account = accountService.login(email, password);

        if (account != null) {
            response.put("message", "Connexion réussie");
            response.put("email", account.getEmail());
            response.put("role", account.getRole());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("message", "Email ou mot de passe incorrect");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // Vérifier si un email existe
    @GetMapping("/exists/{email}")
    public ResponseEntity<Map<String, Boolean>> emailExists(@PathVariable String email) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", accountService.emailExists(email));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Récupérer un compte par email
    @GetMapping("/{email}")
    public ResponseEntity<Account> getAccountByEmail(@PathVariable String email) {
        Optional<Account> account = accountService.getAccountByEmail(email);
        return account.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
