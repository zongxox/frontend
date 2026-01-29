package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fruit {
    private Long id;
    private String fruitName;
    private String fruitCode;
    private String fruitType;
    private Integer price;
    private Integer quantity;
    private String origin;
    private String remark;
    private String creUser;
    private LocalDateTime creDate;
    private String updUser;
    private LocalDateTime updDate;
}
