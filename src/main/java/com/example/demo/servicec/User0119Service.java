package com.example.demo.servicec;

import com.example.demo.dao.UserInit0119DAO;
import com.example.demo.dao.UserQuery0119DAO;
import com.example.demo.dao.UserUpdQuery0119DAO;
import com.example.demo.mapper.UserRepository;
import com.example.demo.req.UserDel0119Req;
import com.example.demo.req.UserIns0119Req;
import com.example.demo.req.UserQuery0119Req;
import com.example.demo.req.UserUpd0119Req;
import com.example.demo.res.UserInit0119Res;
import com.example.demo.res.UserQuery0119Res;
import com.example.demo.res.UserUpdQuery0119Res;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class User0119Service {
    @Autowired
    private UserRepository userRepository;

    public User0119Service(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //頁面初始化查詢
    public List<UserInit0119Res> initSelect() {
        List<UserInit0119DAO> userDAO = userRepository.initSelect();
        List<UserInit0119Res> userRes = new ArrayList<>();
        for (UserInit0119DAO dao : userDAO) {
            UserInit0119Res userInit0119Res = new UserInit0119Res();
            BeanUtils.copyProperties(dao, userInit0119Res);
            userRes.add(userInit0119Res);
        }

        return userRes;
    }


    //查詢按鈕
    public List<UserQuery0119Res> query(UserQuery0119Req req) {
        List<UserQuery0119DAO> dao = userRepository.query(req);
        List<UserQuery0119Res> userRes = new ArrayList<>();
        for (UserQuery0119DAO dao1 : dao) {
            UserQuery0119Res userQuery0119Res = new UserQuery0119Res();
            BeanUtils.copyProperties(dao1, userQuery0119Res);
            userRes.add(userQuery0119Res);
        }
        return userRes;
    }

    //刪除
    public int delete(UserDel0119Req req) {
        return userRepository.deleteById(req);
    }

    //註冊按鈕
    public int insert(UserIns0119Req req){
        int rows = userRepository.insert(req);
        return rows;
    }

    //跳轉修改先查詢
    public UserUpdQuery0119Res updQuery(Integer id){
        UserUpdQuery0119DAO dao = userRepository.updQuery(id);
        UserUpdQuery0119Res res = new UserUpdQuery0119Res();
        BeanUtils.copyProperties(dao, res);
        return res;
    }

    //修改
    public int update(UserUpd0119Req req){
        int rows = userRepository.update(req);
        return rows;
    }


}
