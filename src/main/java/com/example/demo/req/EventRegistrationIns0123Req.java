package com.example.demo.req;

import com.example.demo.dao.MemberDetail0123DAO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRegistrationIns0123Req {
    // PK (複合主鍵)
    private Long memberId;
    private String eventCode;
    private Timestamp registerTime;

    // 其他欄位
    private String eventName;     // 下拉式
    private String statusCode;    // 單選框 (REG / CANCEL)
    private String phone;
    private String email;
    private String note;
    private List<String> optionCodes;   // 多選框 (例: OPT_MEAL,OPT_DRINK)

    private Timestamp cancelTime;
    private Timestamp updateTime;

    private MemberDetail0123DAO memberDetail;
}
