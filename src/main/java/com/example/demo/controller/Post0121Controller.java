package com.example.demo.controller;

import com.example.demo.req.*;
import com.example.demo.res.PostQuery0121Res;
import com.example.demo.res.PostUpdQuery0121Res;
import com.example.demo.servicec.Post0121Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/post/0121")
public class Post0121Controller {
    @Autowired
    private Post0121Service post0121Service;

    //查詢按鈕
    @PostMapping("/query")
    public List<PostQuery0121Res> query(@RequestBody PostQuery0121Req req){
        return post0121Service.query(req);
    }

    //刪除
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable String id) {
        PostDel0121Req req = new PostDel0121Req();
        req.setId(Long.valueOf(id));
        return post0121Service.delete(req);
    }

    //註冊按鈕
    @PostMapping("/insert")
    public int insert(@RequestBody PostIns0121Req req){
        int rows = post0121Service.insert(req);
        return  rows;
    }


    //跳轉修改先查詢
    @PostMapping("/updQuery")
    public PostUpdQuery0121Res updQuery(@RequestBody PostUpdQuery0121Req req){
        return post0121Service.updQuery(req);
    }

    //修改按鈕
    @PostMapping("/update")
    public int update(@RequestBody PostUpd0121Req req) {
        return post0121Service.update(req);
    }
}
