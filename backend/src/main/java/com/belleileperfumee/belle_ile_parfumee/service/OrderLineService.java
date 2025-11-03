package com.belleileperfumee.belle_ile_parfumee.service;

import com.belleileperfumee.belle_ile_parfumee.entity.Order;
import com.belleileperfumee.belle_ile_parfumee.entity.OrderLine;
import com.belleileperfumee.belle_ile_parfumee.entity.Product;
import com.belleileperfumee.belle_ile_parfumee.repository.OrderLineRepository;
import com.belleileperfumee.belle_ile_parfumee.repository.OrderRepository;
import com.belleileperfumee.belle_ile_parfumee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderLineService {

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // Créer une ligne de commande
    public OrderLine createOrderLine(OrderLine orderLine) {
        // On doit récupérer productCode et commandNumber depuis le JSON
        // mais ils sont dans l'objet id qui peut être null

        String productCode = null;
        String commandNumber = null;

        // Si l'id existe dans le JSON, on récupère les valeurs
        if (orderLine.getId() != null) {
            productCode = orderLine.getId().getProductCode();
            commandNumber = orderLine.getId().getCommandNumber();
        }

        // Sinon on essaie de les récupérer depuis product et order s'ils sont présents
        if (productCode == null && orderLine.getProduct() != null) {
            productCode = orderLine.getProduct().getProductCode();
        }
        if (commandNumber == null && orderLine.getOrder() != null) {
            commandNumber = orderLine.getOrder().getCommandNumber();
        }

        // Si on n'a toujours pas les infos, on ne peut pas continuer
        if (productCode == null || commandNumber == null) {
            return null;
        }

        // ✅ RÉCUPÉRER L'ORDER DEPUIS LA BASE DE DONNÉES
        Optional<Order> orderOpt = orderRepository.findById(commandNumber);
        if (orderOpt.isEmpty()) {
            return null; // Order n'existe pas
        }

        // ✅ RÉCUPÉRER LE PRODUCT DEPUIS LA BASE DE DONNÉES
        Optional<Product> productOpt = productRepository.findById(productCode);
        if (productOpt.isEmpty()) {
            return null; // Product n'existe pas
        }

        // ✅ CRÉER UN NOUVEL ORDERLINE PROPRE
        OrderLine newOrderLine = new OrderLine();

        // Créer l'ID composite
        OrderLine.OrderLineId id = new OrderLine.OrderLineId();
        id.setProductCode(productCode);
        id.setCommandNumber(commandNumber);
        newOrderLine.setId(id);

        // Associer les objets
        newOrderLine.setProduct(productOpt.get());
        newOrderLine.setOrder(orderOpt.get());
        newOrderLine.setQuantity(orderLine.getQuantity());
        newOrderLine.setUnitPrice(orderLine.getUnitPrice());

        return orderLineRepository.save(newOrderLine);
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