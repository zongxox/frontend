package com.example.demo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem0126InsReq {

    private Long orderId;

    private Long itemId;

    private String productName;

    private List<String> quantity;

    private Integer unitPrice;

    private Integer discount;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
