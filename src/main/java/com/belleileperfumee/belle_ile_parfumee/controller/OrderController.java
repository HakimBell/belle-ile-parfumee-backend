package com.belleileperfumee.belle_ile_parfumee.controller;

import com.belleileperfumee.belle_ile_parfumee.entity.Order;
import com.belleileperfumee.belle_ile_parfumee.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // CREATE - Créer une nouvelle commande
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // READ - Récupérer toutes les commandes
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // READ - Récupérer une commande par numéro
    @GetMapping("/{commandNumber}")
    public ResponseEntity<Order> getOrderByCommandNumber(@PathVariable String commandNumber) {
        Optional<Order> order = orderService.getOrderByCommandNumber(commandNumber);
        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // READ - Récupérer les commandes d'un client
    @GetMapping("/client/{clientEmail}")
    public ResponseEntity<List<Order>> getOrdersByClientEmail(@PathVariable String clientEmail) {
        List<Order> orders = orderService.getOrdersByClientEmail(clientEmail);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // READ - Récupérer les commandes entre deux dates
    @GetMapping("/date-range")
    public ResponseEntity<List<Order>> getOrdersBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Order> orders = orderService.getOrdersBetweenDates(startDate, endDate);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // READ - Calculer le total d'une commande
    @GetMapping("/{commandNumber}/total")
    public ResponseEntity<BigDecimal> calculateOrderTotal(@PathVariable String commandNumber) {
        BigDecimal total = orderService.calculateOrderTotal(commandNumber);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    // DELETE - Supprimer une commande
    @DeleteMapping("/{commandNumber}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String commandNumber) {
        boolean deleted = orderService.deleteOrder(commandNumber);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
