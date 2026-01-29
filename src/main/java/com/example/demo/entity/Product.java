package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private Long id;                 // 商品ID
    private String name;             // 商品名稱
    private String description;      // 商品描述
    private Integer price;        // 商品價格
    private Integer stock;           // 庫存數量
    private String category;         // 商品分類
    private String brand;            // 商品品牌
    private String sku;              // 商品編號(SKU)
    private String status;           // 商品狀態
    private LocalDateTime createdTime; // 建立時間
    private LocalDateTime updatedTime; // 更新時間
}
