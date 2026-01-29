package com.example.demo.servicec;

import com.example.demo.dao.*;
import com.example.demo.mapper.ProductRepository;
import com.example.demo.req.*;
import com.example.demo.res.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class Product0122Service {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductInit0122Res> init(){
        List<ProductInit0122DAO> init = productRepository.init();
        List<ProductInit0122Res> res = new ArrayList<>();
        for (ProductInit0122DAO s : init){
            ProductInit0122Res productInit0122Res = new ProductInit0122Res();
            BeanUtils.copyProperties(s,productInit0122Res);
            res.add(productInit0122Res);
        }
        return res;
    }

    public List<ProductInit10122Res> init1(){
        List<ProductInit0122DAO> init = productRepository.init1();
        List<ProductInit10122Res> res = new ArrayList<>();
        for (ProductInit0122DAO s : init){
            ProductInit10122Res productInit0122Res = new ProductInit10122Res();
            BeanUtils.copyProperties(s,productInit0122Res);
            res.add(productInit0122Res);
        }
        return res;
    }


    public List<ProductInit20122Res> init2(){
        List<ProductInit0122DAO> init = productRepository.init2();
        List<ProductInit20122Res> res = new ArrayList<>();
        for (ProductInit0122DAO s : init){
            ProductInit20122Res productInit0122Res = new ProductInit20122Res();
            BeanUtils.copyProperties(s,productInit0122Res);
            res.add(productInit0122Res);
        }
        return res;
    }


    //查詢按鈕
    public List<ProductQuery0121Res> query(ProducQuery0122Req req){
        List<ProductQuery0122DAO> dao = productRepository.query(req);
        List<ProductQuery0121Res> productQuery0121Res = new ArrayList<>();

        for (ProductQuery0122DAO s : dao){
            ProductQuery0121Res res = new ProductQuery0121Res();
            BeanUtils.copyProperties(s, res);
            productQuery0121Res.add(res);
        }
        return productQuery0121Res;
    }


    //刪除
    public int delete(ProductDel0122Req req) {
        ProductDel0122DAO dao = new ProductDel0122DAO();
        BeanUtils.copyProperties(req, dao);
        return productRepository.deleteById(dao);
    }


    //註冊按鈕
    public int insert(ProductIns0122Req req){
        ProductIns0122DAO dao = new ProductIns0122DAO();
        BeanUtils.copyProperties(req, dao);
        int rows = productRepository.insert(dao);
        return rows;
    }

    //跳轉修改先查詢
    public ProductUpdQuery0122Res updQuery(ProductUpdQuery0122Req req){
        ProductUpdQuery0122DAO dao = productRepository.updQuery(req);
        ProductUpdQuery0122Res res = new ProductUpdQuery0122Res();
        BeanUtils.copyProperties(dao, res);
        return res;
    }

    //修改
    public int update(ProductUpd0122Req req){
        ProductUpd0122DAO dao = new ProductUpd0122DAO();
        BeanUtils.copyProperties(req, dao);
        int rows = productRepository.update(dao);
        return rows;
    }


}
