package com.belleileperfumee.belle_ile_parfumee.controller;

import com.belleileperfumee.belle_ile_parfumee.config.JwtUtil;
import com.belleileperfumee.belle_ile_parfumee.dto.account.AccountRequestDTO;
import com.belleileperfumee.belle_ile_parfumee.dto.account.AccountResponseDTO;
import com.belleileperfumee.belle_ile_parfumee.dto.account.LoginResponseDTO;
import com.belleileperfumee.belle_ile_parfumee.entity.Account;
import com.belleileperfumee.belle_ile_parfumee.mapper.AccountMapper;
import com.belleileperfumee.belle_ile_parfumee.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUtil jwtUtil; // ✅ AJOUTER JwtUtil

    // Inscription
    @PostMapping("/register")
    public ResponseEntity<AccountResponseDTO> register(@RequestBody AccountRequestDTO requestDTO) {
        Account account = AccountMapper.toEntity(requestDTO);
        Account createdAccount = accountService.createAccount(account);

        if (createdAccount == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        AccountResponseDTO responseDTO = AccountMapper.toResponseDTO(createdAccount);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // Login - MODIFIÉ pour renvoyer un token JWT
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AccountRequestDTO requestDTO) {
        Account account = accountService.login(requestDTO.getEmail(), requestDTO.getPassword());

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // ✅ GÉNÉRER LE TOKEN JWT
        String token = jwtUtil.generateToken(account.getEmail(), account.getRole());

        // ✅ CRÉER LA RÉPONSE AVEC LE TOKEN
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setEmail(account.getEmail());
        responseDTO.setRole(account.getRole());
        responseDTO.setToken(token);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // Get Account by Email
    @GetMapping("/{email}")
    public ResponseEntity<AccountResponseDTO> getAccountByEmail(@PathVariable String email) {
        Optional<Account> account = accountService.getAccountByEmail(email);

        if (account.isPresent()) {
            AccountResponseDTO responseDTO = AccountMapper.toResponseDTO(account.get());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Check if email exists
    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> emailExists(@PathVariable String email) {
        return new ResponseEntity<>(accountService.emailExists(email), HttpStatus.OK);
    }
}