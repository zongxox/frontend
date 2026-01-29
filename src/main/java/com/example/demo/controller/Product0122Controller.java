package com.example.demo.controller;

import com.example.demo.req.*;
import com.example.demo.res.*;
import com.example.demo.servicec.Product0122Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/product/0122")
public class Product0122Controller {
    @Autowired
    private Product0122Service product0122Service;

    @GetMapping("/initSelect")
    public List<ProductInit0122Res> init(){
        return product0122Service.init();
    }

    @GetMapping("/initSelect1")
    public List<ProductInit10122Res> init1(){
        return product0122Service.init1();
    }

    @GetMapping("/initSelect2")
    public List<ProductInit20122Res> init2(){
        return product0122Service.init2();
    }

    //查詢按鈕
    @PostMapping("/query")
    public List<ProductQuery0121Res> query(@RequestBody ProducQuery0122Req req){
        return product0122Service.query(req);
    }

    //刪除
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable String id) {
        ProductDel0122Req req = new ProductDel0122Req();
        req.setId(Long.valueOf(id));
        return product0122Service.delete(req);
    }

    //註冊按鈕
    @PostMapping("/insert")
    public int insert(@RequestBody ProductIns0122Req req){
        int rows = product0122Service.insert(req);
        return  rows;
    }


    //跳轉修改先查詢
    @PostMapping("/updQuery")
    public ProductUpdQuery0122Res updQuery(@RequestBody ProductUpdQuery0122Req req){
        return product0122Service.updQuery(req);
    }

    //修改按鈕
    @PostMapping("/update")
    public int update(@RequestBody ProductUpd0122Req req) {
        return product0122Service.update(req);
    }
}
