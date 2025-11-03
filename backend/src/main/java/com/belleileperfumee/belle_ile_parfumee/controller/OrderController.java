package com.belleileperfumee.belle_ile_parfumee.controller;

import com.belleileperfumee.belle_ile_parfumee.dto.order.OrderRequestDTO;
import com.belleileperfumee.belle_ile_parfumee.dto.order.OrderResponseDTO;
import com.belleileperfumee.belle_ile_parfumee.entity.Order;
import com.belleileperfumee.belle_ile_parfumee.mapper.OrderMapper;
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
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO requestDTO) {
        // 1. Convertir DTO → Entity
        Order order = OrderMapper.toEntity(requestDTO);

        // 2. Créer la commande
        Order createdOrder = orderService.createOrder(order);

        // 3. Vérifier si la création a réussi
        if (createdOrder == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 4. Convertir Entity → ResponseDTO (SANS le Client)
        OrderResponseDTO responseDTO = OrderMapper.toResponseDTO(createdOrder);

        // 5. Renvoyer le DTO
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // READ - Récupérer toutes les commandes
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();

        // Convertir chaque Order → OrderResponseDTO
        List<OrderResponseDTO> responseDTOs = orders.stream()
                .map(OrderMapper::toResponseDTO)
                .toList();

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    // READ - Récupérer une commande par numéro
    @GetMapping("/{commandNumber}")
    public ResponseEntity<OrderResponseDTO> getOrderByCommandNumber(@PathVariable String commandNumber) {
        Optional<Order> order = orderService.getOrderByCommandNumber(commandNumber);

        if (order.isPresent()) {
            // Convertir Entity → ResponseDTO (SANS le Client)
            OrderResponseDTO responseDTO = OrderMapper.toResponseDTO(order.get());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // READ - Récupérer les commandes d'un client
    @GetMapping("/client/{email}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByClientEmail(@PathVariable String email) {
        List<Order> orders = orderService.getOrdersByClientEmail(email);

        // Convertir chaque Order → OrderResponseDTO
        List<OrderResponseDTO> responseDTOs = orders.stream()
                .map(OrderMapper::toResponseDTO)
                .toList();

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
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
