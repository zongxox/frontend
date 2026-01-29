package com.example.demo.controller;

import com.example.demo.req.*;
import com.example.demo.res.*;
import com.example.demo.servicec.EventRegistration0123Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Content-Disposition")
@RestController
@RequestMapping("/eventRegistration/0123")
public class EventRegistration0123Controller {
    @Autowired
    private EventRegistration0123Service eventRegistration0123Service;

    @GetMapping("/initSelect")
    public List<EventRegistrationInit0123Res> init(){
        return eventRegistration0123Service.init();
    }

    @GetMapping("/initSelect1")
    public List<EventRegistrationInit10123Res> init1(){
        return eventRegistration0123Service.init1();
    }

    @GetMapping("/initSelect2")
    public List<EventRegistrationInit20123Res> init2(){
        return eventRegistration0123Service.init2();
    }

    //查詢按鈕
    @PostMapping("/query")
    public List<EventRegistrationQueryRes> query(@RequestBody EventRegistrationQuery0123Req req){
        return eventRegistration0123Service.query(req);
    }

    //刪除
    @DeleteMapping("/delete/{memberId}")
    public int delete(@PathVariable String memberId) {
        EventRegistrationDel0123Req req = new EventRegistrationDel0123Req();
        req.setMemberId(Long.valueOf(memberId));
        return eventRegistration0123Service.delete(req);
    }

    //註冊按鈕
    @PostMapping("/insert")
    public int insert(@RequestBody EventRegistrationIns0123Req req){
        int rows = eventRegistration0123Service.insert(req);
        return  rows;
    }




    //跳轉修改先查詢
    @PostMapping("/updQuery")
    public EventRegistrationUpdQuery0123Res updQuery(@RequestBody EventRegistrationUpdQuery0123Req req){
        return eventRegistration0123Service.updQuery(req);
    }

    //修改按鈕
    @PostMapping("/update")
    public int update(@RequestBody EventRegistrationUpd0123Req req) {
        return eventRegistration0123Service.update(req);
    }

    //下載
    @GetMapping("/download/{memberId}")
    public ResponseEntity<Resource> download(@PathVariable Long memberId) {
        return eventRegistration0123Service.download(memberId);
    }

    //上傳
    @PostMapping("/upload/{memberId}")
    public ResponseEntity<String> upload(@PathVariable Long memberId, @RequestParam("file") MultipartFile file) {
        eventRegistration0123Service.upload(memberId, file);
        return ResponseEntity.ok("上傳成功");
    }


}
