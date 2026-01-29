package com.example.demo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemUpd0126Req {
    private Long id;

    private Long orderId;

    private Long itemId;

    private String productName;

    private String quantity;

    private Integer unitPrice;

    private Integer discount;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
