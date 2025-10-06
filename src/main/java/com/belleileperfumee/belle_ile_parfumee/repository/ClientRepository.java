package com.belleileperfumee.belle_ile_parfumee.repository;

import com.belleileperfumee.belle_ile_parfumee.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    Optional<Client> findByEmail(String email);

    Optional<Client> findByPhoneNumber(String phoneNumber);
}
