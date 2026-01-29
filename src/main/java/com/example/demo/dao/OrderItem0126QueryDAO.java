package com.example.demo.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem0126QueryDAO {

    private Long id;
    private Long orderId;
    private Long itemId;

    // 商品代號 P001...
    private String productName;

    // 數量代號 Q01...
    private String quantity;

    private Integer unitPrice;
    private Integer discount;

    // 狀態代號 NEW/CANCEL...
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
