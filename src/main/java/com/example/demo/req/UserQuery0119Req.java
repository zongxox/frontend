package com.example.demo.req;

import lombok.Data;

import java.util.List;

@Data
public class UserQuery0119Req {
    private Integer id;
    private String name;
    private String phone;
    private List<String> zipcodes;

}
