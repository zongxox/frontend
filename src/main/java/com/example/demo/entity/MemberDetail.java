package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDetail {
    private Long memberId;
    private String name;
    private String gender;
    private String phone;
    private String idNumber;

    private Date birthDate;
    private String address;
    private String hobby;

    private String type;
    private String code;
    private String content;
}
