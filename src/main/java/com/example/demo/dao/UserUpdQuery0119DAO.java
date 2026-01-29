package com.example.demo.dao;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserUpdQuery0119DAO {
    private String name;
    private String account;
    private String password;
    private String phone;
    private String email;
    private String zipcodes;
    private String address;
    private String creUser;
    private LocalDateTime creDate;
    private String updUser;
    private LocalDateTime updDate;

}
