package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fruit") // 資料表名稱，可依實際 DB 調整
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FruitJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fruit_name", nullable = false, length = 50)
    private String fruitName;

    @Column(name = "fruit_code", nullable = false, length = 30, unique = true)
    private String fruitCode;

    @Column(name = "fruit_type", length = 30)
    private String fruitType;

    @Column(name = "price")
    private Integer price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "origin", length = 50)
    private String origin;

    @Column(name = "remark", length = 255)
    private String remark;

    @Column(name = "cre_user", length = 30, updatable = false)
    private String creUser;

    @Column(name = "cre_date", updatable = false)
    private LocalDateTime creDate;

    @Column(name = "upd_user", length = 30)
    private String updUser;

    @Column(name = "upd_date")
    private LocalDateTime updDate;
}
