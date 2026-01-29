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
public class FruitUpd0120Req {
    private Long id;
    private String fruitName;
    private String fruitCode;
    private String fruitType;
    private Integer price;
    private List<Integer> quantity;
    private String origin;
    private String remark;
    private String creUser;
    private LocalDateTime creDate;
    private String updUser;
    private LocalDateTime updDate;
}
