package com.example.demo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RecordsUpd0128Req {
    private Integer id;

    private String userId;

    private String title;

    private String description;

    private String startTime;

    private String endTime;

    private String status;

    private String location;

    private String createdAt;

    private String updatedAt;

}

