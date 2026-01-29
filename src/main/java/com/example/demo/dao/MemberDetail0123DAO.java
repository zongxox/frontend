package com.example.demo.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDetail0123DAO {
    private Long memberId;
    private String name;
    private String gender;
    private String phone;
    private String idNumber;

    private Date birthDate;
    private String address;
    private String hobby;
}
