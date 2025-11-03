package com.belleileperfumee.belle_ile_parfumee.dto.orderline;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderLineResponseDTO {
    private String productCode;
    private String commandNumber;
    private Integer quantity;
    private BigDecimal unitPrice;
    // ✅ On ne renvoie PAS le Product ni l'Order complets
}