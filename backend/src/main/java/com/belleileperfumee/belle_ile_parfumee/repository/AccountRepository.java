package com.belleileperfumee.belle_ile_parfumee.repository;

import com.belleileperfumee.belle_ile_parfumee.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);
}
