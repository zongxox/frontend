package com.example.demo.servicec;

import com.example.demo.dao.PostQuery0121DAO;
import com.example.demo.dao.PostUpdQuery0121DAO;
import com.example.demo.mapper.PostRepository;
import com.example.demo.req.*;
import com.example.demo.res.PostQuery0121Res;
import com.example.demo.res.PostUpdQuery0121Res;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class Post0121Service {
    @Autowired
    private PostRepository postRepository;



    //查詢按鈕
    public List<PostQuery0121Res> query(PostQuery0121Req req){
        List<PostQuery0121DAO> dao = postRepository.query(req);
        List<PostQuery0121Res> sostQuery0121Res = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");

        for (PostQuery0121DAO s : dao){
            PostQuery0121Res res = new PostQuery0121Res();
            BeanUtils.copyProperties(s, res);

            // LocalDateTime -> String (給前端顯示)
            if (res.getCreatedTime() != null) {
                res.setCreatedTimeStr(res.getCreatedTime().format(formatter));
            }

            // updatedTime：只要時間
            if (res.getUpdatedTime() != null) {
                res.setUpdatedTimeStr(res.getUpdatedTime().format(formatter2));
            }

            sostQuery0121Res.add(res);
        }
        return sostQuery0121Res;
    }


    //刪除
    public int delete(PostDel0121Req req) {
        return postRepository.deleteById(req);
    }


    //註冊按鈕
    public int insert(PostIns0121Req req){
        int rows = postRepository.insert(req);
        return rows;
    }

    //跳轉修改先查詢
    public PostUpdQuery0121Res updQuery(PostUpdQuery0121Req req){
        PostUpdQuery0121DAO dao = postRepository.updQuery(req);
        PostUpdQuery0121Res res = new PostUpdQuery0121Res();
        BeanUtils.copyProperties(dao, res);
        return res;
    }

    //修改
    public int update(PostUpd0121Req req){
        int rows = postRepository.update(req);
        return rows;
    }


}
