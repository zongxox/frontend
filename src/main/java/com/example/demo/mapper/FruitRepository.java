package com.example.demo.mapper;

import com.example.demo.dao.*;
import com.example.demo.req.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FruitRepository {

    @Autowired
    private DataSource dataSource;

    /*頁面初始化查詢*/
    public List<FruitInit0120DAO> initSelect() {
        String sql = "SELECT DISTINCT ID, ORIGIN FROM fruit";
        List<FruitInit0120DAO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);//把sql準備好之後,讓她變成一個物件
            rs = ps.executeQuery();//查詢出的結果

            while (rs.next()) {//將每一筆結果放到 dao
                FruitInit0120DAO dao = new FruitInit0120DAO();
                dao.setId(rs.getLong("ID"));
                dao.setOrigin(rs.getString("ORIGIN"));
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

    /*查詢按鈕*/
    public List<FruitQuery0120DAO> query(FruitQuery0120Req req) {
        StringBuilder sql = new StringBuilder(
                "SELECT ID, FRUIT_NAME, FRUIT_CODE, FRUIT_TYPE, PRICE, QUANTITY, ORIGIN, REMARK, " +
                        "CRE_USER, CRE_DATE, UPD_USER, UPD_DATE " +
                        "FROM fruit WHERE 1=1 "
        );//1=1 條件永遠成立

        List<Object> params = new ArrayList<>();

        // id 如果前端傳遞過來 不是null 就將條件加入到sql
        if (req.getId() != null) {
            sql.append(" AND ID = ? ");
            params.add(req.getId());
        }


        if (req.getPrice()!= null ) {
            sql.append(" AND PRICE = ? ");
            params.add(req.getPrice());
        }


        if (req.getFruitName() != null && !req.getFruitName().trim().isEmpty()) {
            sql.append(" AND FRUIT_NAME LIKE ? ");
            params.add("%" + req.getFruitName().trim() + "%");
        }


        // 多選 quantity，如果有傳進來，就組成 IN (?, ?, ?)
        if (req.getQuantity() != null && !req.getQuantity().isEmpty()) {
            sql.append(" AND QUANTITY IN (");

            for (int i = 0; i < req.getQuantity().size(); i++) {
                if (i > 0) {
                    sql.append(", ");
                }
                sql.append("?");
                params.add(req.getQuantity().get(i));
            }

            sql.append(") ");
        }



        List<FruitQuery0120DAO> list = new ArrayList<>();
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
                FruitQuery0120DAO dao = new FruitQuery0120DAO();
                dao.setId(rs.getLong("ID"));
                dao.setFruitName(rs.getString("FRUIT_NAME"));
                dao.setFruitCode(rs.getString("FRUIT_CODE"));
                dao.setFruitType(rs.getString("FRUIT_TYPE"));
                dao.setPrice(rs.getInt("PRICE"));
                dao.setQuantity(rs.getInt("QUANTITY"));
                dao.setOrigin(rs.getString("ORIGIN"));
                dao.setRemark(rs.getString("REMARK"));
                dao.setCreUser(rs.getString("CRE_USER"));
                dao.setCreDate(rs.getTimestamp("CRE_DATE").toLocalDateTime());
                dao.setUpdUser(rs.getString("UPD_USER"));
                dao.setUpdDate(rs.getTimestamp("UPD_DATE").toLocalDateTime());

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
    public int deleteById(FruitDel0120Req req) {
        String sql = "DELETE FROM fruit WHERE ID = ?";

        FruitDel0120DAO dao = new FruitDel0120DAO();
        BeanUtils.copyProperties(req, dao);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1,dao.getId());
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("刪除失敗: " + e.getMessage(), e);
        }
    }



    //新增
    public int insert(FruitIns0120Req req) {
        String sql = "INSERT INTO fruit (FRUIT_NAME, FRUIT_CODE, FRUIT_TYPE, PRICE, QUANTITY, ORIGIN, REMARK, CRE_USER, CRE_DATE, UPD_USER, UPD_DATE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";



        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0;
        FruitIns0120DAO dao = new FruitIns0120DAO();
        dao.setFruitName(req.getFruitName());
        dao.setFruitCode(req.getFruitCode());
        dao.setFruitType(req.getFruitType());
        dao.setPrice(req.getPrice());
        dao.setQuantity(req.getQuantity());
        dao.setOrigin(req.getOrigin());
        dao.setRemark(req.getRemark());
        dao.setCreUser(req.getCreUser());
        dao.setCreDate(req.getCreDate());
        dao.setUpdUser(req.getUpdUser());
        dao.setUpdDate(req.getUpdDate());
        try{
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);
            ps.setString(1, dao.getFruitName());
            ps.setString(2, dao.getFruitCode());
            ps.setString(3, dao.getFruitType());
            ps.setInt(4, dao.getPrice());
            List<Integer> qtyList = dao.getQuantity();
            if (qtyList != null && !qtyList.isEmpty() && qtyList.get(0) != null) {
                ps.setInt(5, qtyList.get(0));
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.setString(6, dao.getOrigin());
            ps.setString(7, dao.getRemark());
            ps.setString(8, dao.getCreUser());
            ps.setTimestamp(9, Timestamp.valueOf(dao.getCreDate()));
            ps.setString(10, dao.getUpdUser());
            ps.setTimestamp(11, Timestamp.valueOf(dao.getUpdDate())); // 如果 updDate 是 Timestamp
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
    public FruitUpdQuery0120DAO updQuery(FruitUpdQuery0120Req req) {
        String sql = "SELECT FRUIT_NAME, FRUIT_CODE, FRUIT_TYPE, PRICE, QUANTITY, ORIGIN, REMARK, " +
                "CRE_USER, CRE_DATE, UPD_USER, UPD_DATE " +
                "FROM fruit WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        FruitUpdQuery0120DAO dao = new FruitUpdQuery0120DAO();
        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);//把sql準備好之後,讓她變成一個物件
            ps.setLong(1,req.getId());

            rs = ps.executeQuery();//查詢出的結果


            if (rs.next()) {
                dao.setFruitName(rs.getString("FRUIT_NAME"));
                dao.setFruitCode(rs.getString("FRUIT_CODE"));
                dao.setFruitType(rs.getString("FRUIT_TYPE"));
                dao.setPrice(rs.getInt("PRICE"));
                dao.setQuantity(rs.getInt("QUANTITY"));
                dao.setOrigin(rs.getString("ORIGIN"));
                dao.setRemark(rs.getString("REMARK"));
                dao.setCreUser(rs.getString("CRE_USER"));
                dao.setCreDate(rs.getTimestamp("CRE_DATE").toLocalDateTime());
                dao.setUpdUser(rs.getString("UPD_USER"));
                dao.setUpdDate(rs.getTimestamp("UPD_DATE").toLocalDateTime());
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
        return dao;
    }


    //修改
    public int update(FruitUpd0120Req req) {
        int rows = 0;
        FruitUpd0120DAO dao = new FruitUpd0120DAO();
        BeanUtils.copyProperties(req,dao);
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE `fruit` SET ");

        int idx = 1;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            boolean hasSet = false;

            if (dao.getFruitName() != null) {
                sql.append("FRUIT_NAME = ?");
                hasSet = true;
            }
            if (dao.getFruitCode() != null) {
                if (hasSet) sql.append(", ");
                sql.append("FRUIT_CODE = ?");
                hasSet = true;
            }
            if (dao.getFruitType() != null) {
                if (hasSet) sql.append(", ");
                sql.append("FRUIT_TYPE = ?");
                hasSet = true;
            }
            if (dao.getPrice() != null) {
                if (hasSet) sql.append(", ");
                sql.append("PRICE = ?");
                hasSet = true;
            }
            if (dao.getQuantity() != null) {
                if (hasSet) sql.append(", ");
                sql.append("QUANTITY = ?");
                hasSet = true;
            }
            if (dao.getOrigin() != null) {
                if (hasSet) sql.append(", ");
                sql.append("ORIGIN = ?");
                hasSet = true;
            }
            if (dao.getRemark() != null) {
                if (hasSet) sql.append(", ");
                sql.append("REMARK = ?");
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
            if (dao.getFruitName() != null) {
                ps.setString(idx++, dao.getFruitName());
            }
            if (dao.getFruitCode() != null) {
                ps.setString(idx++, dao.getFruitCode());
            }
            if (dao.getFruitType() != null) {
                ps.setString(idx++, dao.getFruitType());
            }
            if (dao.getPrice() != null) {
                ps.setInt(idx++, dao.getPrice());
            }
            if (dao.getQuantity() != null) {
                String qStr = dao.getQuantity().stream()
                        .map(String::valueOf)
                        .collect(java.util.stream.Collectors.joining(","));
                ps.setString(idx++, qStr);
            }
            if (dao.getOrigin()!= null) {
                ps.setString(idx++, dao.getOrigin());
            }
            if (dao.getRemark() != null) {
                ps.setString(idx++, dao.getRemark());
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
            ps.setLong(idx, dao.getId());

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

    // 查詢檔案路徑 + 檔案名稱
    //傳入水果資料的 id，回傳 路徑+檔名 的 M
    public Map<String, String> queryFileInfo(Long id) {
        //SQL：用 ID 查 FRUIT_CODE(檔名) 和 FRUIT_TYPE(路徑)
        String sql = "SELECT FRUIT_CODE,FRUIT_TYPE FROM fruit WHERE ID = ?";

        //從 dataSource 取得資料庫連線（try-with-resources 自動關閉）
        try (Connection conn = dataSource.getConnection();
             //預備 SQL 語法（PreparedStatement 可避免 SQL Injection）
             PreparedStatement ps = conn.prepareStatement(sql)) {
            //把 SQL 裡的第 1 個 ? 參數帶入 id
            ps.setLong(1, id);
                //執行查詢，回傳 ResultSet（查詢結果）
            try (ResultSet rs = ps.executeQuery()) {

                //rs.next() 會移到第一筆資料；如果沒有資料表示查不到
                //查不到該 id 就回傳 null（表示沒檔案資訊）
                if (!rs.next()) {
                    return null;
                }

                //從查詢結果取出 FRUIT_TYPE 欄位值（代表檔案路徑）
                String dirPath = rs.getString("FRUIT_TYPE");
                //從查詢結果取出 FRUIT_CODE 欄位值（代表檔案名稱）
                String fileName = rs.getString("FRUIT_CODE");
                //建立一個 Map 來裝路徑與檔名
                Map<String, String> map = new HashMap<>();
                map.put("dirPath", dirPath);//把路徑放進 map，key 叫 "dirPath"
                map.put("fileName", fileName);// 把檔名放進 map，key 叫 "fileName"
                return map; //回傳 map 給 service 使用
            }

        } catch (Exception e) {//發生任何例外（SQL錯誤、連線失敗、欄位錯等等）
            //包成 RuntimeException 丟出去，方便看錯誤原因
            throw new RuntimeException("FruitRepository queryFileInfo 失敗：" + e.getMessage(), e);
        }
    }



}




