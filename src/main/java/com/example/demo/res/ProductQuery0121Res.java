package com.example.demo.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductQuery0121Res {
    private Long id;                 // 商品ID
    private String name;             // 商品名稱
    private String description;      // 商品描述
    private Integer price;        // 商品價格
    private Integer stock;           // 庫存數量
    private String category;         // 商品分類
    private String brand;            // 商品品牌
    private String sku;              // 商品編號(SKU)
    private String status;           // 商品狀態
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime; // 建立時間
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalDateTime updatedTime; // 更新時間
}
