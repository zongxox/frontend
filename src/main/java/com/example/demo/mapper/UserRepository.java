package com.example.demo.mapper;

import com.example.demo.dao.*;
import com.example.demo.req.UserDel0119Req;
import com.example.demo.req.UserIns0119Req;
import com.example.demo.req.UserQuery0119Req;
import com.example.demo.req.UserUpd0119Req;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private DataSource dataSource;

    /*頁面初始化查詢*/
    public List<UserInit0119DAO> initSelect() {
        String sql = "SELECT ID, ADDRESS FROM user";
        List<UserInit0119DAO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);//把sql準備好之後,讓她變成一個物件
            rs = ps.executeQuery();//查詢出的結果

            while (rs.next()) {//將每一筆結果放到 dao
                UserInit0119DAO userDao = new UserInit0119DAO();
                userDao.setId(rs.getInt("ID"));
                userDao.setAddress(rs.getString("ADDRESS"));
                list.add(userDao);//把每一筆的res 循環結果放到list
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

    /*查詢按鈕*/
    public List<UserQuery0119DAO> query(UserQuery0119Req req) {
        //String sql = "SELECT ID, NAME,ACCOUNT,PASSWORD,PHONE,EMAIL,ZIPCODES,ADDRESS,CRE_USER,CRE_DATE,UPD_USER,UPD_DATE FROM user WHERE ID = ? AND NAME = ?  AND PHONE = ? AND ZIPCODES = ?";
        StringBuilder sql = new StringBuilder(
                "SELECT ID, NAME, ACCOUNT, PASSWORD, PHONE, EMAIL, ZIPCODES, ADDRESS, " +
                        "CRE_USER, CRE_DATE, UPD_USER, UPD_DATE " +
                        "FROM user WHERE 1=1 "
        );//1=1 條件永遠成立

        List<Object> params = new ArrayList<>();

        // id 如果前端傳遞過來 不是null 就將條件加入到sql
        if (req.getId() != null) {
            sql.append(" AND ID = ? ");
            params.add(req.getId());
        }

        // name 如果前端傳遞過來 不是null並且不是空 就將條件加入到sql
        if (req.getName() != null && !req.getName().trim().isEmpty()) {
            sql.append(" AND NAME = ? ");
            params.add(req.getName().trim());
        }

        // 單選 phone 如果前端傳遞過來 不是null並且不是空 就將條件加入到sql
        if (req.getPhone() != null && !req.getPhone().trim().isEmpty()) {
            sql.append(" AND PHONE = ? ");
            params.add(req.getPhone().trim());
        }

        //多選zipcodes 如果前端傳遞過來 不是null並且不是空 就將條件加入到sql
        if (req.getZipcodes() != null && !req.getZipcodes().isEmpty()) {
            sql.append(" AND ZIPCODES IN (");//加入sql
            for (int i = 0; i < req.getZipcodes().size(); i++) {
                if (i > 0) //i > 0 不進入判斷
                    sql.append(", ");// i < 0 就進入 加上逗號
                sql.append("?");//再加上問號 IN (?, ?
                params.add(req.getZipcodes().get(i));//裡面會存放多筆結果
            }
            sql.append(") ");//IN的結尾 最後變成AND ZIPCODES IN (?, ?, ?)
        }


        List<UserQuery0119DAO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql.toString());//把sql準備好之後,讓她變成一個物件

            //將多選框的值 set進去ps裡面
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }



            rs = ps.executeQuery();//查詢出的結果
            while (rs.next()) {//將每一筆結果放到 dao
                UserQuery0119DAO dao = new UserQuery0119DAO();
                dao.setId(rs.getInt("ID"));
                dao.setName(rs.getString("NAME"));
                dao.setAccount(rs.getString("ACCOUNT"));
                dao.setPassword(rs.getString("PASSWORD"));
                dao.setPhone(rs.getString("PHONE"));
                dao.setEmail(rs.getString("EMAIL"));
                dao.setZipcodes(rs.getString("ZIPCODES"));
                dao.setAddress(rs.getString("ADDRESS"));
                dao.setCreUser(rs.getString("CRE_USER"));
                Timestamp creTs = rs.getTimestamp("CRE_DATE");
                dao.setCreDate(creTs == null ? null : creTs.toLocalDateTime());
                dao.setUpdUser(rs.getString("UPD_USER"));
                Timestamp updTs = rs.getTimestamp("UPD_DATE");
                dao.setUpdDate(updTs == null ? null : updTs.toLocalDateTime());
                list.add(dao);//把每一筆的res 循環結果放到list
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查詢失敗: " + e.getMessage(), e);
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

    //刪除
    public int deleteById(UserDel0119Req req) {
        String sql = "DELETE FROM `USER` WHERE ID = ?";

        UserDel0119DAO dao = new UserDel0119DAO();
        BeanUtils.copyProperties(req, dao);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dao.getId());
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("刪除失敗: " + e.getMessage(), e);
        }
    }



    //新增
    public int insert(UserIns0119Req req) {
        String sql = "INSERT INTO USER (NAME, ACCOUNT, PASSWORD, PHONE, EMAIL, ADDRESS, ZIPCODES, CRE_USER, CRE_DATE, UPD_USER, UPD_DATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0;
        UserIns0119DAO dao = new UserIns0119DAO();
        dao.setName(req.getName());
        dao.setAccount(req.getAccount());
        dao.setPassword(req.getPassword());
        dao.setPhone(req.getPhone());
        dao.setEmail(req.getEmail());
        dao.setAddress(req.getAddress());
        dao.setZipcodes(req.getZipcodes());
        dao.setCreUser(req.getCreUser());
        dao.setCreDate(req.getCreDate());
        dao.setUpdUser(req.getUpdUser());
        dao.setUpdDate(req.getUpdDate());
        String zipcodeStr = (dao.getZipcodes() == null) ? "" : String.join(",", dao.getZipcodes());
        try{
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);
            ps.setString(1, dao.getName());
            ps.setString(2, dao.getAccount());
            ps.setString(3, dao.getPassword());
            ps.setString(4, dao.getPhone());
            ps.setString(5, dao.getEmail());
            ps.setString(6, dao.getAddress());
            ps.setString(7, zipcodeStr);
            ps.setString(8,dao.getCreUser());
            ps.setString(9,dao.getCreDate());
            ps.setString(10,dao.getUpdUser());
            ps.setString(11,dao.getUpdDate());
            rows = ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("新增失敗: " + e.getMessage(), e);
        }finally {
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
        return rows;
    }


    //跳轉修改先查詢
    public UserUpdQuery0119DAO updQuery(Integer id) {
        String sql = "SELECT ID, NAME, ACCOUNT, PASSWORD, PHONE, EMAIL, ZIPCODES, ADDRESS, CRE_USER, CRE_DATE, UPD_USER, UPD_DATE  FROM user WHERE ID = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);//把sql準備好之後,讓她變成一個物件
            ps.setInt(1, id);

            rs = ps.executeQuery();//查詢出的結果

            UserUpdQuery0119DAO dao = new UserUpdQuery0119DAO();
            if (rs.next()) {
                dao.setName(rs.getString("NAME"));
                dao.setAccount(rs.getString("ACCOUNT"));
                dao.setPassword(rs.getString("PASSWORD"));
                dao.setPhone(rs.getString("PHONE"));
                dao.setEmail(rs.getString("EMAIL"));
                dao.setZipcodes(rs.getString("ZIPCODES"));
                dao.setAddress(rs.getString("ADDRESS"));
                dao.setCreUser(rs.getString("CRE_USER"));
                Timestamp ts = rs.getTimestamp("CRE_DATE");
                dao.setCreDate(ts == null ? null : ts.toLocalDateTime());
                dao.setUpdUser(rs.getString("UPD_USER"));
                Timestamp ts2 = rs.getTimestamp("UPD_DATE");
                dao.setUpdDate(ts2 == null ? null : ts2.toLocalDateTime());
                return dao;
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
        return null;
    }


    //修改
    public int update(UserUpd0119Req req) {
        int rows = 0;
        UserUpd0119DAO dao = new UserUpd0119DAO();
        dao.setId(req.getId());
        dao.setName(req.getName());
        dao.setAccount(req.getAccount());
        dao.setPassword(req.getPassword());
        dao.setPhone(req.getPhone());
        dao.setEmail(req.getEmail());
        dao.setZipcodes(req.getZipcodes());
        dao.setAddress(req.getAddress());
        dao.setCreUser(req.getCreUser());
        dao.setCreDate(req.getCreDate());
        dao.setUpdUser(req.getUpdUser());
        dao.setUpdDate(req.getUpdDate());

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE `user` SET ");

        int idx = 1;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            boolean hasSet = false;

            if (dao.getName() != null) {
                sql.append("NAME = ?");
                hasSet = true;
            }
            if (dao.getAccount() != null) {
                if (hasSet) sql.append(", ");
                sql.append("ACCOUNT = ?");
                hasSet = true;
            }
            if (dao.getPassword() != null) {
                if (hasSet) sql.append(", ");
                sql.append("PASSWORD = ?");
                hasSet = true;
            }
            if (dao.getPhone() != null) {
                if (hasSet) sql.append(", ");
                sql.append("PHONE = ?");
                hasSet = true;
            }
            if (dao.getEmail() != null) {
                if (hasSet) sql.append(", ");
                sql.append("EMAIL = ?");
                hasSet = true;
            }
            if (dao.getZipcodes() != null) {
                if (hasSet) sql.append(", ");
                sql.append("ZIPCODES = ?");
                hasSet = true;
            }
            if (dao.getAddress() != null) {
                if (hasSet) sql.append(", ");
                sql.append("ADDRESS = ?");
                hasSet = true;
            }


            if (dao.getCreUser() != null) {
                if (hasSet) sql.append(", ");
                sql.append("CRE_USER = ?");
                hasSet = true;
            }
            if (dao.getCreDate() != null) {
                if (hasSet) sql.append(", ");
                sql.append("CRE_DATE = ?");
                hasSet = true;
            }

            if (dao.getUpdUser() != null) {
                if (hasSet) sql.append(", ");
                sql.append("UPD_USER = ?");
                hasSet = true;
            }
            if (dao.getUpdDate() != null) {
                if (hasSet) sql.append(", ");
                sql.append("UPD_DATE = ?");
                hasSet = true;
            }

            // 沒有任何欄位要更新
            if (!hasSet) return 0;

            // WHERE
            sql.append(" WHERE ID = ?");

            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql.toString());

            // 參數順序要跟上面 append 的順序完全一致
            if (dao.getName() != null) {
                ps.setString(idx++, dao.getName());
            }
            if (dao.getAccount() != null) {
                ps.setString(idx++, dao.getAccount());
            }
            if (dao.getPassword() != null) {
                ps.setString(idx++, dao.getPassword());
            }
            if (dao.getPhone() != null) {
                ps.setString(idx++, dao.getPhone());
            }
            if (dao.getEmail() != null) {
                ps.setString(idx++, dao.getEmail());
            }
            if (dao.getZipcodes() != null) {
                ps.setString(idx++, String.join(",", dao.getZipcodes()));
            }
            if (dao.getAddress() != null) {
                ps.setString(idx++, dao.getAddress());
            }

            if (dao.getCreUser() != null) {
                ps.setString(idx++, dao.getCreUser());
            }
            if (dao.getCreDate() != null) {
                ps.setTimestamp(idx++, Timestamp.valueOf(dao.getCreDate()));
            }

            if (dao.getUpdUser() != null ) {
                ps.setString(idx++, dao.getUpdUser());
            }
            if (dao.getUpdDate() != null) {
                ps.setTimestamp(idx++, Timestamp.valueOf(dao.getUpdDate()));
            }

            // WHERE ID
            ps.setInt(idx, dao.getId());

            rows = ps.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("新增失敗: " + e.getMessage(), e);
        }finally {
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
        return rows;

    }


}

