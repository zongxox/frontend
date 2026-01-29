package com.example.demo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProducQuery0122Req {
    private String name;             // 商品名稱
    private List<String> category;         // 商品分類
    private String brand;            // 商品品牌
    private String status;           // 商品狀態

}
