package com.belleileperfumee.belle_ile_parfumee.service;

import com.belleileperfumee.belle_ile_parfumee.entity.Client;
import com.belleileperfumee.belle_ile_parfumee.entity.Order;
import com.belleileperfumee.belle_ile_parfumee.entity.OrderLine;
import com.belleileperfumee.belle_ile_parfumee.repository.ClientRepository;
import com.belleileperfumee.belle_ile_parfumee.repository.OrderRepository;
import com.belleileperfumee.belle_ile_parfumee.repository.OrderLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private ClientRepository clientRepository;  // ✅ AJOUTER

    // Créer une nouvelle commande
    public Order createOrder(Order order) {
        // Définir la date de commande si elle n'est pas définie
        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDate.now());
        }

        // ✅ RÉCUPÉRER LE CLIENT DEPUIS LA BASE DE DONNÉES
        Optional<Client> clientOpt = clientRepository.findById(order.getEmail());
        if (clientOpt.isEmpty()) {
            return null; // Client n'existe pas
        }

        // ✅ ASSOCIER LE CLIENT À LA COMMANDE
        order.setClient(clientOpt.get());

        return orderRepository.save(order);
    }

    // Récupérer toutes les commandes
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Récupérer une commande par son numéro
    public Optional<Order> getOrderByCommandNumber(String commandNumber) {
        return orderRepository.findByCommandNumber(commandNumber);
    }

    // Récupérer toutes les commandes d'un client
    public List<Order> getOrdersByClientEmail(String clientEmail) {
        return orderRepository.findByClient_Email(clientEmail);
    }

    // Récupérer les commandes entre deux dates
    public List<Order> getOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }

    // Calculer le prix total d'une commande
    public BigDecimal calculateOrderTotal(String commandNumber) {
        List<OrderLine> orderLines = orderLineRepository.findById_CommandNumber(commandNumber);

        BigDecimal total = BigDecimal.ZERO;
        for (OrderLine line : orderLines) {
            BigDecimal lineTotal = line.getUnitPrice()
                    .multiply(BigDecimal.valueOf(line.getQuantity()));
            total = total.add(lineTotal);
        }

        return total;
    }

    // Supprimer une commande
    public boolean deleteOrder(String commandNumber) {
        if (orderRepository.existsById(commandNumber)) {
            orderRepository.deleteById(commandNumber);
            return true;
        }
        return false;
    }
}