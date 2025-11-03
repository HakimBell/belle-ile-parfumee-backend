package com.belleileperfumee.belle_ile_parfumee.mapper;

import com.belleileperfumee.belle_ile_parfumee.dto.order.OrderRequestDTO;
import com.belleileperfumee.belle_ile_parfumee.dto.order.OrderResponseDTO;
import com.belleileperfumee.belle_ile_parfumee.entity.Order;

public class OrderMapper {

    // Convertir RequestDTO → Entity (pour créer/sauvegarder)
    public static Order toEntity(OrderRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Order order = new Order();
        order.setCommandNumber(dto.getCommandNumber());
        order.setEmail(dto.getEmail());
        return order;
    }

    // Convertir Entity → ResponseDTO (pour renvoyer au client)
    public static OrderResponseDTO toResponseDTO(Order order) {
        if (order == null) {
            return null;
        }

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setCommandNumber(order.getCommandNumber());
        dto.setEmail(order.getEmail());
        dto.setOrderDate(order.getOrderDate());
        // ✅ On ne renvoie PAS le Client !
        return dto;
    }
}