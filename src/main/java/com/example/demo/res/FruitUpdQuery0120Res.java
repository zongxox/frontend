package com.example.demo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FruitUpdQuery0120Res {
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
