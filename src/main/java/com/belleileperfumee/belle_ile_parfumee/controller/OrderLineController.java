package com.belleileperfumee.belle_ile_parfumee.controller;

import com.belleileperfumee.belle_ile_parfumee.entity.OrderLine;
import com.belleileperfumee.belle_ile_parfumee.service.OrderLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderlines")
@CrossOrigin(origins = "*")
public class OrderLineController {

    @Autowired
    private OrderLineService orderLineService;

    // CREATE - Ajouter un produit à une commande
    @PostMapping
    public ResponseEntity<OrderLine> createOrderLine(@RequestBody OrderLine orderLine) {
        OrderLine createdOrderLine = orderLineService.createOrderLine(orderLine);

        // ✅ VÉRIFIER SI LA CRÉATION A RÉUSSI
        if (createdOrderLine != null) {
            return new ResponseEntity<>(createdOrderLine, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Erreur si création échouée
    }

    // READ - Récupérer toutes les lignes de commande
    @GetMapping
    public ResponseEntity<List<OrderLine>> getAllOrderLines() {
        List<OrderLine> orderLines = orderLineService.getAllOrderLines();
        return new ResponseEntity<>(orderLines, HttpStatus.OK);
    }

    // READ - Récupérer les lignes d'une commande spécifique
    @GetMapping("/order/{commandNumber}")
    public ResponseEntity<List<OrderLine>> getOrderLinesByCommandNumber(@PathVariable String commandNumber) {
        List<OrderLine> orderLines = orderLineService.getOrderLinesByCommandNumber(commandNumber);
        return new ResponseEntity<>(orderLines, HttpStatus.OK);
    }

    // READ - Récupérer les commandes contenant un produit
    @GetMapping("/product/{productCode}")
    public ResponseEntity<List<OrderLine>> getOrderLinesByProductCode(@PathVariable String productCode) {
        List<OrderLine> orderLines = orderLineService.getOrderLinesByProductCode(productCode);
        return new ResponseEntity<>(orderLines, HttpStatus.OK);
    }

    // DELETE - Supprimer une ligne de commande
    @DeleteMapping
    public ResponseEntity<Void> deleteOrderLine(@RequestBody OrderLine.OrderLineId id) {
        boolean deleted = orderLineService.deleteOrderLine(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}