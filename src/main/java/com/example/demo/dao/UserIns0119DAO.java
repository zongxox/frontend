package com.example.demo.dao;

import lombok.Data;

import java.util.List;

@Data
public class UserIns0119DAO {
    private String name;
    private String account;
    private String password;
    private String phone;
    private String email;
    private List<String> zipcodes;
    private String address;
    private String creUser;
    private String creDate;
    private String updUser;
    private String updDate;

}
