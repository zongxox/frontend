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
public class FruitUpd0129Req {
    private String id;

    private String fruitName;

    private String fruitCode;

    private List<String> fruitType;

    private String price;

    private String quantity;

    private String origin;

    private String remark;

    private String creUser;

    private String creDate;

    private String updUser;

    private String updDate;
}
