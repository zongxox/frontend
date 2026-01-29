package com.example.demo.req;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserUpd0119Req {
    private Integer id;
    private String name;
    private String account;
    private String password;
    private String phone;
    private String email;
    private List<String> zipcodes;
    private String address;
    private String creUser;
    private LocalDateTime creDate;
    private String updUser;
    private LocalDateTime updDate;

}
