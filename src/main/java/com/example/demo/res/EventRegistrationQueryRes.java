package com.example.demo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRegistrationQueryRes {
    // PK (複合主鍵)
    private Long memberId;
    private String eventCode;
    private String registerTime;

    // 其他欄位
    private String eventName;     // 下拉式
    private String statusCode;    // 單選框 (REG / CANCEL)
    private String phone;
    private String email;
    private String note;
    private String optionCodes;   // 多選框 (例: OPT_MEAL,OPT_DRINK)

    private String cancelTime;
    private String updateTime;
}
