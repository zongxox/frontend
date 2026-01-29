package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCode {
    private Long id;        // 主鍵
    private String type;    // 類型：category / brand / status
    private String code;    // 代碼
    private String content; // 顯示內容
}
