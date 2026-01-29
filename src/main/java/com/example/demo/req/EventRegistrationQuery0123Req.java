package com.example.demo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRegistrationQuery0123Req {
    private String registerTime;
    private String cancelTime;
    private String eventName;     // 下拉式
    private String statusCode;    // 單選框 (REG / CANCEL)
    private List<String> optionCodes;   // 多選框 (例: OPT_MEAL,OPT_DRINK)

}
