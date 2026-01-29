package com.example.demo.controller;

import com.example.demo.res.CodeType0121Res;
import com.example.demo.servicec.CodeType0121Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/codeType/0121")
public class CodeType0121Controller {
    @Autowired
    private CodeType0121Service codeType0121Service;

    //初始化查詢
    @GetMapping("/initSelect")
    public Map<String, List<CodeType0121Res>> initSelect() {
        return codeType0121Service.initSelect();
    }




}
