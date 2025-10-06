package com.belleileperfumee.belle_ile_parfumee.repository;

import com.belleileperfumee.belle_ile_parfumee.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<Order> findByCommandNumber(String commandNumber);

    List<Order> findByClient_Email(String clientEmail);

    List<Order> findByOrderDate(LocalDate orderDate);

    List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
}
