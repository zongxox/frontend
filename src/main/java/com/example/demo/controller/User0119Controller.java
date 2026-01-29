package com.example.demo.controller;

import com.example.demo.req.UserDel0119Req;
import com.example.demo.req.UserIns0119Req;
import com.example.demo.req.UserQuery0119Req;
import com.example.demo.req.UserUpd0119Req;
import com.example.demo.res.UserInit0119Res;
import com.example.demo.res.UserQuery0119Res;
import com.example.demo.res.UserUpdQuery0119Res;
import com.example.demo.servicec.User0119Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user/0119")
public class User0119Controller {
    @Autowired
    private User0119Service userService;

    //初始化查詢
    @GetMapping("/initSelect")
    public List<UserInit0119Res> selectAllIdAndAddress(){
        return userService.initSelect();
    }

    //查詢按鈕
    @PostMapping("/query")
    public List<UserQuery0119Res> query(@RequestBody UserQuery0119Req req){
        return userService.query(req);
    }

    //刪除
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable String id) {
        UserDel0119Req req = new UserDel0119Req();
        req.setId(id);
        return userService.delete(req);
    }


    //註冊按鈕
    @PostMapping("/insert")
    public int insert(@RequestBody UserIns0119Req req){
        int rows = userService.insert(req);
        return  rows;
    }

    //跳轉修改先查詢
    @GetMapping("/updQuery/{id}")
    public UserUpdQuery0119Res updQuery(@PathVariable Integer id){
        return userService.updQuery(id);
    }

    @PostMapping("/update")
    public int update(@RequestBody UserUpd0119Req req) {
        return userService.update(req);
    }


}
