package com.example.demo.dao;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserUpd0119DAO {
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
