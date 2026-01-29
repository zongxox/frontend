package com.example.demo.req;

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
public class FruitIns0129Req {

    private String fruitName;

    private String fruitCode;

    private List<String> fruitType;

    private Integer price;

    private Integer quantity;

    private String origin;

    private String remark;

    private String creUser;

    private LocalDateTime creDate;

    private String updUser;

    private LocalDateTime updDate;
}
