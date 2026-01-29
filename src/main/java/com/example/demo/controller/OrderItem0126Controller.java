package com.example.demo.controller;

import com.example.demo.req.OrderItem0126DelReq;
import com.example.demo.req.OrderItem0126InsReq;
import com.example.demo.req.OrderItem0126QueryReq;
import com.example.demo.req.OrderItemUpd0126Req;
import com.example.demo.res.OrderItem0126QueryRes;
import com.example.demo.res.OrderItemInit0126Res;
import com.example.demo.servicec.OrderItem0126Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/orderItem/0126")
public class OrderItem0126Controller {
    @Autowired
    private OrderItem0126Service orderItem0126Service;

    //初始化查詢
    @GetMapping("/initSelect")
    public List<OrderItemInit0126Res> init(){
        return orderItem0126Service.init();
    }

    @GetMapping("/initSelect1")
    public List<OrderItemInit0126Res> init1(){
        return orderItem0126Service.init1();
    }

    @GetMapping("/initSelect2")
    public List<OrderItemInit0126Res> init2(){
        return orderItem0126Service.init2();
    }
    //查詢
    @PostMapping("/query")
    public List<OrderItem0126QueryRes> query(@RequestBody OrderItem0126QueryReq req){
        return orderItem0126Service.query(req);
    }
    //刪除
    @GetMapping("/delete/{id}")
    public int del(@PathVariable String id){
        OrderItem0126DelReq req = new OrderItem0126DelReq();
        req.setId(Long.valueOf(id));
        return orderItem0126Service.del(req);
    }

    //新增
    @PostMapping("/insert")
    public int insert(@RequestBody OrderItem0126InsReq req){
        return orderItem0126Service.insert(req);
    }

    //修改
    @PostMapping("/update")
    public int insert(@RequestBody OrderItemUpd0126Req req){
        return orderItem0126Service.update(req);
    }

    //上傳
    @PostMapping("/importExcel")
    public String importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        orderItem0126Service.importOrderItemExcel(file);
        return "匯入成功";
    }


    //下載
    @GetMapping("/downloadTemplate")
    public void downloadExcel(HttpServletResponse response) throws Exception {
        orderItem0126Service.downloadExcel(response);
    }


}
