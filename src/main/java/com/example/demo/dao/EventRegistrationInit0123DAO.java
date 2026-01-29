package com.example.demo.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRegistrationInit0123DAO {
    private String statusCode;
    private String statusContent;

    private String eventNameCode;
    private String eventNameContent;

    private String optionCodesCode;
    private String optionCodesContent;


}
