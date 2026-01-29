package com.example.demo.controller;

import com.example.demo.req.RecordsQuery0128Req;
import com.example.demo.req.RecordsUp0128Req;
import com.example.demo.req.RecordsUpd0128Req;
import com.example.demo.res.ErTest123Res;
import com.example.demo.res.RecordsInt0128Res;
import com.example.demo.res.RecordsQuery0128Res;
import com.example.demo.res.RecordsUp0128Res;
import com.example.demo.servicec.Records0128Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/records/0128")
public class Records0128Controller {
    @Autowired
    private Records0128Service records0128Service;

    //頁面初始化
    @GetMapping("init")
    public List<RecordsInt0128Res> init(){
        return records0128Service.init();
    }

    //查詢案衂
    @PostMapping("/query")
    public List<RecordsQuery0128Res> query(@RequestBody RecordsQuery0128Req req){
        return records0128Service.query(req);
    }

    //修改
    @PostMapping("/update")
    public int update(@RequestBody RecordsUpd0128Req req){
        return records0128Service.update(req);
    }

    //讀取上傳檔案
    @PostMapping("/importExcel")
    public  List<RecordsUp0128Res> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        return  records0128Service.importExcel(file);
    }

    //上傳
    @PostMapping("/insert")
    public int insert(@RequestBody List<RecordsUp0128Req> req){
        return  records0128Service.insert(req);
    }

    //
    @GetMapping("/testQuery")
    public List<ErTest123Res> testQuery(){
        return records0128Service.testQuery();
    }
}

