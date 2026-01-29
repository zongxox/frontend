package com.example.demo.controller;

import com.example.demo.req.*;
import com.example.demo.res.FruitInit0129Res;
import com.example.demo.res.FruitQuery0129Res;
import com.example.demo.res.FruitUpdQuery0129Res;
import com.example.demo.servicec.FruitJpa0129Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/FruitJpa/0129")
public class FruitJpa0129Controller {
    @Autowired
    private FruitJpa0129Service FruitJpa0129Service;

    //頁面初始化
    @GetMapping("init")
    public List<FruitInit0129Res> init(){
        return FruitJpa0129Service.init();
    }

    //查詢案衂
    @PostMapping("/query")
    public List<FruitQuery0129Res> query(@RequestBody FruitQuery0129Req req){
        return FruitJpa0129Service.query(req);
    }


    //刪除
    @GetMapping("/delete/{id}")
    public int del(@PathVariable String id){
        FruitDel0129Req req = new FruitDel0129Req();
        req.setId(Long.valueOf(id));
        int rows = FruitJpa0129Service.del(req);
        return rows;
    }

    //新增
    @PostMapping("/insert")
    public int insert(@RequestBody FruitIns0129Req  req){
        int rows = FruitJpa0129Service.insert(req);
        return rows;
    }

    //跳轉修改畫面查詢
    @GetMapping("/updQuery/{id}")
    public FruitUpdQuery0129Res updaQuery(@PathVariable String id){
        FruitUpdQuery0129Req req = new FruitUpdQuery0129Req();
        req.setId(id);
        return FruitJpa0129Service.updQuery(req);
    }
    //修改
    @PostMapping("/update")
    public int update(@RequestBody FruitUpd0129Req req){
        return FruitJpa0129Service.update(req);
    }

}

