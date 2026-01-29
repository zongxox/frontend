package com.example.demo.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery0119DAO {
    private Integer id;
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
