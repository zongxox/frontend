package com.example.demo.servicec;

import com.example.demo.dao.*;
import com.example.demo.entity.FruitJpa;
import com.example.demo.mapper.Fruit0129Repository;
import com.example.demo.req.*;
import com.example.demo.res.FruitInit0129Res;
import com.example.demo.res.FruitQuery0129Res;
import com.example.demo.res.FruitUpdQuery0129Res;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class FruitJpa0129Service {
    @Autowired
    private Fruit0129Repository f;

    //初始化查詢
    public List<FruitInit0129Res> init() {
        List<FruitInit0129DAO> init = f.init();
        List<FruitInit0129Res> res = new ArrayList<>();
        for (FruitInit0129DAO s : init) {
            FruitInit0129Res fruitInit0129Res = new FruitInit0129Res();
            BeanUtils.copyProperties(s, fruitInit0129Res);
            res.add(fruitInit0129Res);
        }
        return res;
    }

    //查詢按鈕
    public List<FruitQuery0129Res> query(FruitQuery0129Req req) {
        List<FruitQuery0129DAO> dao = f.query(req);
        List<FruitQuery0129Res> res = new ArrayList<>();
        for (FruitQuery0129DAO s : dao) {
            FruitQuery0129Res fruitQuery0129Res = new FruitQuery0129Res();
            BeanUtils.copyProperties(s, fruitQuery0129Res);
            res.add(fruitQuery0129Res);
        }
        return res;
    }

    //刪除
    public int del(FruitDel0129Req req) {
        FruitDel0129DAO dao = new FruitDel0129DAO();
        BeanUtils.copyProperties(req, dao);
        int rows = f.deleteById(dao);
        return rows;
    }

    //新增
    public int insert(FruitIns0129Req req) {
        FruitJpa fruitJpa = new FruitJpa();
        BeanUtils.copyProperties(req, fruitJpa);
        fruitJpa.setFruitType(String.join(",", req.getFruitType()));
        int rows = f.insert(fruitJpa);
        return rows;
    }


    //跳轉修改畫面查詢
    public FruitUpdQuery0129Res updQuery(FruitUpdQuery0129Req req) {
        FruitUpdQuery0129DAO dao = f.updQuery(req);
        FruitUpdQuery0129Res res = new FruitUpdQuery0129Res();
        BeanUtils.copyProperties(dao, res);
        return res;
    }

    //修改
    public int update(FruitUpd0129Req req) {
        FruitUpd0129DAO dao = FruitUpd0129DAO.builder()
                .id(Long.valueOf(req.getId()))
                .fruitName(req.getFruitName())
                .fruitCode(req.getFruitCode())
                .fruitType(String.join(",",req.getFruitType()))
                .price(Integer.valueOf(req.getPrice()))
                .quantity(Integer.valueOf(req.getQuantity()))
                .origin(req.getOrigin())
                .remark(req.getRemark())
                .creUser(req.getCreUser())
                .creDate(LocalDateTime.parse(req.getCreDate()))
                .updUser(req.getUpdUser())
                .updDate(LocalDateTime.parse(req.getUpdDate()))
                .build();
        int rows = f.update(dao);
        return rows;
    }
}
