package com.belleileperfumee.belle_ile_parfumee.service;

import com.belleileperfumee.belle_ile_parfumee.entity.Account;
import com.belleileperfumee.belle_ile_parfumee.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // ✅ BCrypt déjà là

    // Créer un nouveau compte
    public Account createAccount(Account account) {
        // Vérifier si l'email existe déjà
        if (emailExists(account.getEmail())) {
            return null;
        }

        // ✅ HASHER le mot de passe avant de sauvegarder
        String hashedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(hashedPassword);

        return accountRepository.save(account);
    }

    // Vérifier les identifiants lors du login
    public Account login(String email, String password) {
        Optional<Account> accountOpt = accountRepository.findByEmail(email);

        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();

            // ✅ VÉRIFIER le mot de passe avec BCrypt
            if (passwordEncoder.matches(password, account.getPassword())) {
                return account; // Login réussi
            }
        }

        return null; // Email ou mot de passe incorrect
    }

    // Vérifier si un email existe
    public boolean emailExists(String email) {
        return accountRepository.existsByEmail(email);
    }

    // Récupérer un compte par email
    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
}