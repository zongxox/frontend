package com.example.demo.servicec;

import com.example.demo.dao.CodeType0121DAO;
import com.example.demo.mapper.CodeType0121Repository;
import com.example.demo.res.CodeType0121Res;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CodeType0121Service {
    @Autowired
    private CodeType0121Repository codeType0121Repository;



    //頁面初始化查詢
    public Map<String, List<CodeType0121Res>> initSelect() {

        List<CodeType0121Res> authorRes = convert(codeType0121Repository.initSelect("author"));
        List<CodeType0121Res> statusRes = convert(codeType0121Repository.initSelect("status"));
        List<CodeType0121Res> categoryRes = convert(codeType0121Repository.initSelect("category"));

        Map<String, List<CodeType0121Res>> map = new HashMap<>();
        map.put("authorList", authorRes);
        map.put("statusList", statusRes);
        map.put("categoryList", categoryRes);
        return map;
    }

    private List<CodeType0121Res> convert(List<CodeType0121DAO> list){
        List<CodeType0121Res> resList = new ArrayList<>();
        for(CodeType0121DAO dao : list){
            CodeType0121Res res = new CodeType0121Res();
            BeanUtils.copyProperties(dao, res);
            resList.add(res);
        }
        return resList;
    }








}
