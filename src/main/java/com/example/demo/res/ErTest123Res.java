package com.example.demo.res;

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
public class ErTest123Res {
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

    private Timestamp cancelTime;
    private Timestamp updateTime;

    private List<MemberDetail0123DAO> memberDetail;
}
