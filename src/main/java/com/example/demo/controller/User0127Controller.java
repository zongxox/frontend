package com.example.demo.controller;

import com.example.demo.CorsConfig.ExcelExportUtil;
import com.example.demo.req.UserDel0127Req;
import com.example.demo.req.UserInd0127Req;
import com.example.demo.req.UserQuery0127Req;
import com.example.demo.req.UserUpd0127Req;
import com.example.demo.res.UserInit0127Res;
import com.example.demo.res.UserQuery0127Res;
import com.example.demo.res.UserUpdQuery0127Res;
import com.example.demo.servicec.User0127Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user/0127")
public class User0127Controller {
    @Autowired
    private User0127Service user0127Service;

    //頁面初始化
    @GetMapping("init")
    public List<UserInit0127Res> init(){
        return user0127Service.init();
    }

    //查詢按鈕
    @PostMapping("/query")
    public List<UserQuery0127Res> query(@RequestBody UserQuery0127Req req){
        return user0127Service.query(req);
    }

    //刪除
    @GetMapping("/del/{id}")
    public int del(@PathVariable String id){
        UserDel0127Req req = new UserDel0127Req();
        req.setId(id);
        return user0127Service.del(req);
    }

    //新增
    @PostMapping("/insert")
    public int insert(UserInd0127Req req){
        return user0127Service.insert(req);
    }

    //跳轉查詢
    @GetMapping("/updQuery/{id}")
    public UserUpdQuery0127Res updQuery(@PathVariable String id){
        return user0127Service.updQuery(id);
    }

    //修改
    @PostMapping("/update")
    public int update(@RequestBody UserUpd0127Req req){
        return user0127Service.update(req);
    }

    //上傳檔案
    @PostMapping("/importExcel")
    public String importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        user0127Service.importExcel(file);
        return "匯入成功";
    }


    //下載
    @PostMapping("/downloadExcel")
    public void downloadExcel(
            @RequestBody UserQuery0127Req req,
            @RequestParam(defaultValue = "xlsx") String excelType,
            HttpServletResponse response
    ) throws Exception {

        List<UserQuery0127Res> rows = user0127Service.downloadList(req);

        boolean isXls = "xls".equalsIgnoreCase(excelType);

        byte[] bytes = ExcelExportUtil.buildApplyDetailExcel(rows, isXls);

        String fileName = UUID.randomUUID().toString();

        if (isXls) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + fileName + ".xls\"");
        } else {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + fileName + ".xlsx\"");
        }

        response.setContentLength(bytes.length);

        try (ServletOutputStream out = response.getOutputStream()) {
            out.write(bytes);
            out.flush();
        }
    }



}

