package com.example.demo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FruitQuery0120Req {
    private Long id;
    private Integer price;
    private List<Integer> quantity;
    private String fruitName;
}
