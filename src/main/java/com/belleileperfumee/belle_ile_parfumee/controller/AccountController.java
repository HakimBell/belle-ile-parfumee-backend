package com.belleileperfumee.belle_ile_parfumee.controller;

import com.belleileperfumee.belle_ile_parfumee.dto.AccountRequestDTO;
import com.belleileperfumee.belle_ile_parfumee.dto.AccountResponseDTO;
import com.belleileperfumee.belle_ile_parfumee.entity.Account;
import com.belleileperfumee.belle_ile_parfumee.mapper.AccountMapper;
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
    public ResponseEntity<AccountResponseDTO> register(@RequestBody AccountRequestDTO requestDTO) {
        // 1. Convertir DTO → Entity
        Account account = AccountMapper.toEntity(requestDTO);

        // 2. Créer le compte (logique métier dans le Service)
        Account createdAccount = accountService.createAccount(account);

        // 3. Vérifier si la création a réussi
        if (createdAccount == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Email déjà utilisé
        }

        // 4. Convertir Entity → ResponseDTO (SANS le password)
        AccountResponseDTO responseDTO = AccountMapper.toResponseDTO(createdAccount);

        // 5. Renvoyer le DTO
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // Connexion - Vérifier email et mot de passe
    @PostMapping("/login")
    public ResponseEntity<AccountResponseDTO> login(@RequestBody AccountRequestDTO requestDTO) {
        // 1. Vérifier les identifiants
        Account account = accountService.login(requestDTO.getEmail(), requestDTO.getPassword());

        // 2. Si login échoué
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // 3. Convertir Entity → ResponseDTO (SANS le password)
        AccountResponseDTO responseDTO = AccountMapper.toResponseDTO(account);

        // 4. Renvoyer le DTO
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
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
    public ResponseEntity<AccountResponseDTO> getAccountByEmail(@PathVariable String email) {
        Optional<Account> account = accountService.getAccountByEmail(email);

        if (account.isPresent()) {
            // Convertir Entity → ResponseDTO (SANS le password)
            AccountResponseDTO responseDTO = AccountMapper.toResponseDTO(account.get());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
