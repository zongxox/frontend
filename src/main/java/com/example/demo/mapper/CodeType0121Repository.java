package com.example.demo.mapper;

import com.example.demo.dao.CodeType0121DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CodeType0121Repository {

    @Autowired
    private DataSource dataSource;


    /*頁面初始化查詢*/
    public List<CodeType0121DAO> initSelect(String type) {
        String sql = "SELECT CODE, CONTENT FROM code_type WHERE TYPE = ?";
        List<CodeType0121DAO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);//把sql準備好之後,讓她變成一個物件
            ps.setString(1, type);
            rs = ps.executeQuery();//查詢出的結果

            while (rs.next()) {//將每一筆結果放到 dao
                CodeType0121DAO dao = new CodeType0121DAO();
                dao.setCode(rs.getString("CODE"));
                dao.setContent(rs.getString("CONTENT"));
                list.add(dao);//把每一筆的res 循環結果放到list
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查詢失敗");
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }




}




