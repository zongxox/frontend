package com.example.demo.mapper;

import com.example.demo.dao.*;
import com.example.demo.req.UserInd0127Req;
import com.example.demo.req.UserQuery0127Req;
import com.example.demo.req.UserUpdQuery0127Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class User0127Repository {

    @Autowired
    private DataSource dataSource;

    public List<UserInit0127DAO> init() {
        String sql = "SELECT PHONE,ADDRESS,ZIPCODES FROM USER";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<UserInit0127DAO> list = new ArrayList<>();
        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);//把sql準備好之後,讓她變成一個物件
            rs = ps.executeQuery();//查詢出的結果

            while (rs.next()) {
                UserInit0127DAO dao = new UserInit0127DAO();
                dao.setPhone(rs.getString(1));
                dao.setAddress(rs.getString(2));
                dao.setZipcodes(rs.getString(3));
                list.add(dao);
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

    //查詢按鈕
    public List<UserQuery0127DAO> query(UserQuery0127Req req) {
        StringBuilder sql = new StringBuilder("SELECT ID,NAME,PASSWORD,ACCOUNT,PHONE,EMAIL,ADDRESS,ZIPCODES,CRE_USER,CRE_DATE,UPD_USER,UPD_DATE FROM USER WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (req.getName() != null && !req.getName().trim().isEmpty()) {
            sql.append(" AND NAME LIKE ? ");
            params.add("%" + req.getName().trim() + "%");
        }

        if (req.getPhone() != null && !req.getPhone().trim().isEmpty()) {
            sql.append(" AND PHONE = ? ");
            params.add(req.getPhone());
        }

        if (req.getAddress() != null && !req.getAddress().trim().isEmpty()) {
            sql.append(" AND ADDRESS = ? ");
            params.add(req.getAddress());
        }

        if (req.getZipcodes() != null && !req.getZipcodes().isEmpty()) {
            sql.append(" AND ZIPCODES IN (");
            for (int i = 0; i < req.getZipcodes().size(); i++) {
                sql.append("?");
                if (i < req.getZipcodes().size() - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");

            params.addAll(req.getZipcodes());
        }


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<UserQuery0127DAO> list = new ArrayList<>();
        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql.toString());//把sql準備好之後,讓她變成一個物件

            //把 SQL 裡的 ?，一個一個塞值進去
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }


            rs = ps.executeQuery();//查詢出的結果

            while (rs.next()) {
                UserQuery0127DAO dao = new UserQuery0127DAO();
                dao.setId(rs.getInt(1));
                dao.setName(rs.getString(2));
                dao.setPassword(rs.getString(3));
                dao.setAccount(rs.getString(4));
                dao.setPhone(rs.getString(5));
                dao.setEmail(rs.getString(6));
                dao.setAddress(rs.getString(7));
                dao.setZipcodes(rs.getString(8));
                dao.setCreUser(rs.getString(9));
                Timestamp creTs = rs.getTimestamp(10);
                dao.setCreDate(creTs == null ? null : creTs.toLocalDateTime());
                dao.setUpdUser(rs.getString(11));
                Timestamp updTs = rs.getTimestamp(12);
                dao.setUpdDate(updTs == null ? null : updTs.toLocalDateTime());
                list.add(dao);
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
    public int del(UserDel0127DAO dao) {
        String sql = "DELETE FROM USER WHERE ID = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0;
        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);//把sql準備好之後,讓她變成一個物件
            ps.setInt(1, dao.getId());
            rows = ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查詢失敗: " + e.getMessage(), e);
        } finally {
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

    //新增
    public int insert(UserInd0127Req req) {
        String sql = "INSERT INTO USER (NAME, PASSWORD, ACCOUNT, PHONE, EMAIL, ADDRESS, ZIPCODES, CRE_USER, CRE_DATE, UPD_USER, UPD_DATE) " +
                      "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement ps = null;
        UserInd0127DAO dao = new UserInd0127DAO();
        dao.setName(req.getName());
        dao.setPassword(req.getPassword());
        dao.setAccount(req.getAccount());
        dao.setPhone(req.getPhone());
        dao.setEmail(req.getEmail());
        dao.setAddress(req.getAddress());
        String zipcodesStr = String.join(",", req.getZipcodes());
        dao.setZipcodes(zipcodesStr);
        dao.setCreUser(req.getCreUser());
        dao.setCreDate(req.getCreDate());
        dao.setUpdUser(req.getUpdUser());
        dao.setUpdDate(req.getUpdDate());
        int rows = 0;
        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);
            ps.setString(1, dao.getName());
            ps.setString(2, dao.getPassword());
            ps.setString(3, dao.getAccount());
            ps.setString(4, dao.getPhone());
            ps.setString(5, dao.getEmail());
            ps.setString(6, dao.getAddress());
            ps.setString(7, dao.getZipcodes());
            ps.setString(8, dao.getCreUser());
            ps.setTimestamp(9, Timestamp.valueOf(dao.getCreDate()));
            ps.setString(10, dao.getUpdUser());
            ps.setTimestamp(11, Timestamp.valueOf(dao.getCreDate()));
            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查詢失敗: " + e.getMessage(), e);
        } finally {
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

    //跳轉頁面查詢
    public UserUpdQuery0127DAO updQuery(UserUpdQuery0127Req req) {
        String sql = "SELECT NAME, PASSWORD, ACCOUNT, PHONE, EMAIL, ADDRESS, ZIPCODES, CRE_USER, CRE_DATE, UPD_USER, UPD_DATE FROM USER WHERE ID = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserUpdQuery0127DAO dao = new UserUpdQuery0127DAO();
        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);//把sql準備好之後,讓她變成一個物件
            ps.setInt(1,req.getId());
            rs = ps.executeQuery();//查詢出的結果
            if (rs.next()) {
                dao.setName(rs.getString(1));
                dao.setPassword(rs.getString(2));
                dao.setAccount(rs.getString(3));
                dao.setPhone(rs.getString(4));
                dao.setEmail(rs.getString(5));
                dao.setAddress(rs.getString(6));
                dao.setZipcodes(rs.getString(7));
                dao.setCreUser(rs.getString(8));
                Timestamp creTs = rs.getTimestamp(9);
                dao.setCreDate(creTs == null ? null : creTs.toLocalDateTime());
                dao.setUpdUser(rs.getString(10));
                Timestamp updTs = rs.getTimestamp(11);
                dao.setUpdDate(updTs == null ? null : updTs.toLocalDateTime());
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
        return dao;
    }


    //修改
    public int update(UserUpd0127DAO dao){
        int rows = 0;
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE user SET ");
        int idx = 1;
        Connection conn = null;
        PreparedStatement ps = null;
        try{
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

    // POI上傳
    public int insert1(UserInd0127DAO dao) throws Exception {

        // 1) SQL：新增資料到 USER
        String sql = "INSERT INTO USER (NAME, PASSWORD, ACCOUNT, PHONE, EMAIL, ADDRESS, ZIPCODES, CRE_USER, CRE_DATE, UPD_USER, UPD_DATE) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        // 2) try-with-resources：會自動關閉 Connection、PreparedStatement（避免資源外洩）
        try (Connection conn = dataSource.getConnection();        // 3) 跟資料庫要一個連線
             PreparedStatement ps = conn.prepareStatement(sql)) { // 4) 把 SQL 指令準備好（PreparedStatement）
            // 5) 把 dao 的欄位值依序塞進 SQL 的
            ps.setString(1, dao.getName());
            ps.setString(2, dao.getPassword());
            ps.setString(3, dao.getAccount());
            ps.setString(4, dao.getPhone());
            ps.setString(5, dao.getEmail());
            ps.setString(6, dao.getAddress());
            ps.setString(7, dao.getZipcodes());
            ps.setString(8, dao.getCreUser());
            if (dao.getCreDate() == null) {
                ps.setNull(9, java.sql.Types.TIMESTAMP);
            } else {
                ps.setTimestamp(9, Timestamp.valueOf(dao.getCreDate()));
            }

            ps.setString(10, dao.getUpdUser());
            if (dao.getUpdDate() == null) {
                ps.setNull(11, java.sql.Types.TIMESTAMP);
            } else {
                ps.setTimestamp(11, Timestamp.valueOf(dao.getUpdDate()));
            }
            // 6) 執行新增
            return ps.executeUpdate();
        }
    }

}




