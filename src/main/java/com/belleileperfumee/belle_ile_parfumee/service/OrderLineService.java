package com.belleileperfumee.belle_ile_parfumee.service;

import com.belleileperfumee.belle_ile_parfumee.entity.OrderLine;
import com.belleileperfumee.belle_ile_parfumee.repository.OrderLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineService {

    @Autowired
    private OrderLineRepository orderLineRepository;

    // Créer une ligne de commande
    public OrderLine createOrderLine(OrderLine orderLine) {
        return orderLineRepository.save(orderLine);
    }

    // Récupérer toutes les lignes d'une commande
    public List<OrderLine> getOrderLinesByCommandNumber(String commandNumber) {
        return orderLineRepository.findById_CommandNumber(commandNumber);
    }

    // Récupérer toutes les commandes contenant un produit
    public List<OrderLine> getOrderLinesByProductCode(String productCode) {
        return orderLineRepository.findById_ProductCode(productCode);
    }

    // Récupérer toutes les lignes de commande
    public List<OrderLine> getAllOrderLines() {
        return orderLineRepository.findAll();
    }

    // Supprimer une ligne de commande
    public boolean deleteOrderLine(OrderLine.OrderLineId id) {
        if (orderLineRepository.existsById(id)) {
            orderLineRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
