package com.belleileperfumee.belle_ile_parfumee.service;

import com.belleileperfumee.belle_ile_parfumee.entity.Account;
import com.belleileperfumee.belle_ile_parfumee.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // Créer un nouveau compte (inscription)
    public Account createAccount(Account account) {
        // Vérifier si l'email existe déjà
        if (accountRepository.existsByEmail(account.getEmail())) {
            return null; // Email déjà utilisé
        }

        // TODO: Ajouter le hashage du mot de passe ici plus tard (BCrypt)
        return accountRepository.save(account);
    }

    // Connexion (vérifier email + password)
    public Account login(String email, String password) {
        Optional<Account> account = accountRepository.findByEmail(email);

        if (account.isPresent()) {
            // TODO: Comparer avec le hash du mot de passe plus tard
            if (account.get().getPassword().equals(password)) {
                return account.get();
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