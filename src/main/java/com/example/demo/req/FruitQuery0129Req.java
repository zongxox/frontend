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
public class FruitQuery0129Req {
    private String fruitName;
    private List<String> fruitType;
    private Integer price;
    private String origin;
}
