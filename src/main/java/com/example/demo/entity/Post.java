package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    private Long id; // 1 主鍵

    private String title; // 2 標題

    private String content; // 3 內容

    private String category;

    private String code; // key (例如 a01 / tech / 1)

    private String tags; // 6 標籤

    private Integer views; // 7 瀏覽數

    private Integer likes; // 8 按讚數

    private Integer status;

    private LocalDateTime createdTime; // 11 建立時間

    private LocalDateTime updatedTime; // 12 更新時間
}
