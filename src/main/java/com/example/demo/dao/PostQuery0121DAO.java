package com.example.demo.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostQuery0121DAO {

    private Long id;

    private String title;

    private String content;

    private String category;

    private String code;

    private String tags;

    private Integer views;

    private Integer likes;

    private Integer status;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;
}
