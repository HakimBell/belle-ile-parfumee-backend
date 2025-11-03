package com.belleileperfumee.belle_ile_parfumee.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "order_lines")
@Data
public class OrderLine {

    @EmbeddedId
    private OrderLineId id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @ManyToOne
    @MapsId("productCode")
    @JoinColumn(name = "product_code")
    private Product product;

    @ManyToOne
    @MapsId("commandNumber")
    @JoinColumn(name = "command_number")
    private Order order;

    @Embeddable
    @Data
    public static class OrderLineId implements Serializable {
        @Column(name = "product_code", length = 50)
        private String productCode;

        @Column(name = "command_number", length = 50)
        private String commandNumber;
    }
}
