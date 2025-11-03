package com.belleileperfumee.belle_ile_parfumee.controller;

import com.belleileperfumee.belle_ile_parfumee.dto.orderline.OrderLineRequestDTO;
import com.belleileperfumee.belle_ile_parfumee.dto.orderline.OrderLineResponseDTO;
import com.belleileperfumee.belle_ile_parfumee.entity.OrderLine;
import com.belleileperfumee.belle_ile_parfumee.mapper.OrderLineMapper;
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
    public ResponseEntity<OrderLineResponseDTO> createOrderLine(@RequestBody OrderLineRequestDTO requestDTO) {
        // 1. Convertir DTO → Entity
        OrderLine orderLine = OrderLineMapper.toEntity(requestDTO);

        // 2. Créer l'OrderLine
        OrderLine createdOrderLine = orderLineService.createOrderLine(orderLine);

        // 3. Vérifier si la création a réussi
        if (createdOrderLine == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 4. Convertir Entity → ResponseDTO (SANS Product et Order complets)
        OrderLineResponseDTO responseDTO = OrderLineMapper.toResponseDTO(createdOrderLine);

        // 5. Renvoyer le DTO
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // READ - Récupérer toutes les lignes de commande
    @GetMapping
    public ResponseEntity<List<OrderLineResponseDTO>> getAllOrderLines() {
        List<OrderLine> orderLines = orderLineService.getAllOrderLines();

        // Convertir chaque OrderLine → OrderLineResponseDTO
        List<OrderLineResponseDTO> responseDTOs = orderLines.stream()
                .map(OrderLineMapper::toResponseDTO)
                .toList();

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    // READ - Récupérer les lignes d'une commande spécifique
    @GetMapping("/order/{commandNumber}")
    public ResponseEntity<List<OrderLineResponseDTO>> getOrderLinesByCommandNumber(@PathVariable String commandNumber) {
        List<OrderLine> orderLines = orderLineService.getOrderLinesByCommandNumber(commandNumber);

        // Convertir chaque OrderLine → OrderLineResponseDTO
        List<OrderLineResponseDTO> responseDTOs = orderLines.stream()
                .map(OrderLineMapper::toResponseDTO)
                .toList();

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    // READ - Récupérer les commandes contenant un produit
    @GetMapping("/product/{productCode}")
    public ResponseEntity<List<OrderLineResponseDTO>> getOrderLinesByProductCode(@PathVariable String productCode) {
        List<OrderLine> orderLines = orderLineService.getOrderLinesByProductCode(productCode);

        // Convertir chaque OrderLine → OrderLineResponseDTO
        List<OrderLineResponseDTO> responseDTOs = orderLines.stream()
                .map(OrderLineMapper::toResponseDTO)
                .toList();

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
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