package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "RECORDS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Data
public class Records {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID") // 主鍵 ID
    private Integer id;

    @Column(name = "USER_ID", nullable = false) // 使用者 ID
    private Integer userId;

    @Column(name = "TITLE", length = 100) // 標題
    private String title;

    @Column(name = "DESCRIPTION") // 詳細說明
    private String description;

    @Column(name = "START_TIME", nullable = false) // 開始時間（手動輸入）
    private LocalDateTime startTime;

    @Column(name = "END_TIME", nullable = false) // 結束時間（手動輸入）
    private LocalDateTime endTime;

    @Column(name = "STATUS") // 狀態（1=啟用，0=停用）
    private String status;

    @Column(name = "LOCATION", length = 100) // 地點
    private String location;

    @Column(name = "CREATED_AT", nullable = false) // 建立時間（手動輸入）
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false) // 更新時間（手動輸入）
    private LocalDateTime updatedAt;
}

