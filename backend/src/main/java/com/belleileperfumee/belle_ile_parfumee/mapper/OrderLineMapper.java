package com.belleileperfumee.belle_ile_parfumee.mapper;

import com.belleileperfumee.belle_ile_parfumee.dto.orderline.OrderLineRequestDTO;
import com.belleileperfumee.belle_ile_parfumee.dto.orderline.OrderLineResponseDTO;
import com.belleileperfumee.belle_ile_parfumee.entity.OrderLine;

public class OrderLineMapper {

    // Convertir RequestDTO → Entity (pour créer/sauvegarder)
    public static OrderLine toEntity(OrderLineRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        OrderLine orderLine = new OrderLine();

        // Créer l'ID composite
        OrderLine.OrderLineId id = new OrderLine.OrderLineId();
        id.setProductCode(dto.getProductCode());
        id.setCommandNumber(dto.getCommandNumber());
        orderLine.setId(id);

        orderLine.setQuantity(dto.getQuantity());
        orderLine.setUnitPrice(dto.getUnitPrice());

        return orderLine;
    }

    // Convertir Entity → ResponseDTO (pour renvoyer au client)
    public static OrderLineResponseDTO toResponseDTO(OrderLine orderLine) {
        if (orderLine == null) {
            return null;
        }

        OrderLineResponseDTO dto = new OrderLineResponseDTO();
        dto.setProductCode(orderLine.getId().getProductCode());
        dto.setCommandNumber(orderLine.getId().getCommandNumber());
        dto.setQuantity(orderLine.getQuantity());
        dto.setUnitPrice(orderLine.getUnitPrice());

        // ✅ On ne renvoie PAS le Product ni l'Order complets !

        return dto;
    }
}