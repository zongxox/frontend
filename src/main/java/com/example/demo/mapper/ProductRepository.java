package com.example.demo.mapper;

import com.example.demo.dao.*;
import com.example.demo.req.ProducQuery0122Req;
import com.example.demo.req.ProductUpdQuery0122Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    private DataSource dataSource;

    /*init查詢*/
    public List<ProductInit0122DAO> init() {
        String sql =
                "SELECT " +
                        "  e.CODE AS BRAND_CODE, e.CONTENT AS BRAND_CONTENT " +
                        "FROM PRODUCT p " +
                        "LEFT JOIN PRODUCT_CODE e ON e.TYPE='brand' AND e.CODE = p.BRAND ";

        List<ProductInit0122DAO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql.toString());//把sql準備好之後,讓她變成一個物件

            rs = ps.executeQuery();//查詢出的結果
            while (rs.next()) {//將每一筆結果放到 dao
                ProductInit0122DAO dao = new ProductInit0122DAO();
                dao.setBrandCode(rs.getString("BRAND_CODE"));
                dao.setBrandContent(rs.getString("BRAND_CONTENT"));

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
    /*init查詢*/
    public List<ProductInit0122DAO> init1() {
        String sql =
                "SELECT " +
                        "  c.CODE AS STATUS_CODE, c.CONTENT AS STATUS_CONTENT " +
                        "FROM PRODUCT p " +
                        "LEFT JOIN PRODUCT_CODE c ON c.TYPE='status' AND c.CODE = p.STATUS ";

        List<ProductInit0122DAO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql.toString());//把sql準備好之後,讓她變成一個物件

            rs = ps.executeQuery();//查詢出的結果
            while (rs.next()) {//將每一筆結果放到 dao
                ProductInit0122DAO dao = new ProductInit0122DAO();
                dao.setStatusCode(rs.getString("STATUS_CODE"));
                dao.setStatusContent(rs.getString("STATUS_CONTENT"));

//                dao.setCategoryCode(rs.getString("CATEGORY_CODE"));
//                dao.setCategoryContent(rs.getString("CATEGORY_CONTENT"));
//
//                dao.setBrandCode(rs.getString("BRAND_CODE"));
//                dao.setBrandContent(rs.getString("BRAND_CONTENT"));

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


    public List<ProductInit0122DAO> init2() {
        String sql =
                "SELECT " +
                        "  d.CODE AS CATEGORY_CODE, d.CONTENT AS CATEGORY_CONTENT " +
                        "FROM PRODUCT p " +
                        "LEFT JOIN PRODUCT_CODE d ON d.TYPE='category' AND d.CODE = p.CATEGORY ";

        List<ProductInit0122DAO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql.toString());//把sql準備好之後,讓她變成一個物件

            rs = ps.executeQuery();//查詢出的結果
            while (rs.next()) {//將每一筆結果放到 dao
                ProductInit0122DAO dao = new ProductInit0122DAO();
                dao.setCategoryCode(rs.getString("CATEGORY_CODE"));
                dao.setCategoryContent(rs.getString("CATEGORY_CONTENT"));

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




    /*查詢按鈕*/
    public List<ProductQuery0122DAO> query(ProducQuery0122Req req) {
        StringBuilder sql = new StringBuilder(
                "SELECT p.ID, p.NAME, p.DESCRIPTION, p.PRICE, p.STOCK, " +
                        "d.CONTENT, e.CONTENT, p.SKU, c.CONTENT, " +
                        "p.CREATED_TIME, p.UPDATED_TIME " +
                        "FROM product p " +
                        "LEFT JOIN product_code c ON p.STATUS = c.CODE " +
                        "LEFT JOIN product_code d ON p.CATEGORY = d.CODE " +
                        "LEFT JOIN product_code e ON p.BRAND = e.CODE " +
                        "WHERE 1=1 "
        );



        List<Object> params = new ArrayList<>();

        // id 如果前端傳遞過來 不是null 就將條件加入到sql
        if (req.getName() != null && !req.getName().trim().isEmpty()) {
            sql.append(" AND p.NAME = ? ");
            params.add(req.getName().trim());
        }

        // id 如果前端傳遞過來 不是null 就將條件加入到sql
        if (req.getBrand() != null && !req.getBrand().trim().isEmpty()) {
            sql.append(" AND p.BRAND = ? ");
            params.add(req.getBrand().trim());
        }

        // id 如果前端傳遞過來 不是null 就將條件加入到sql
        if (req.getStatus() != null && !req.getStatus().trim().isEmpty()) {
            sql.append(" AND p.STATUS = ? ");
            params.add(req.getStatus().trim());
        }

        if (req.getCategory() != null && !req.getCategory().isEmpty()) {
            sql.append(" AND p.CATEGORY IN (");
            for (int i = 0; i < req.getCategory().size(); i++) {
                sql.append("?");
                if (i < req.getCategory().size() - 1) {
                    sql.append(",");
                }
                params.add(req.getCategory().get(i).trim());
            }
            sql.append(") ");
        }

        sql.append(" ORDER BY p.UPDATED_TIME , p.ID  ");

        List<ProductQuery0122DAO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql.toString());//把sql準備好之後,讓她變成一個物件

            // ps 塞參數（重點在這）
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            rs = ps.executeQuery();//查詢出的結果
            while (rs.next()) {//將每一筆結果放到 dao
                ProductQuery0122DAO dao = new ProductQuery0122DAO();
                dao.setId(rs.getLong(1));
                dao.setName(rs.getString(2));
                dao.setDescription(rs.getString(3));
                dao.setPrice(rs.getInt(4));
                dao.setStock(rs.getInt(5));
                dao.setCategory(rs.getString(6));
                dao.setBrand(rs.getString(7));
                dao.setSku(rs.getString(8));
                dao.setStatus(rs.getString(9));
                dao.setCreatedTime(rs.getTimestamp(10).toLocalDateTime());
                dao.setUpdatedTime(rs.getTimestamp(11).toLocalDateTime());
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
    public int deleteById(ProductDel0122DAO dao) {
        String sql = "DELETE FROM event_registration WHERE ID = ?";


        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1,dao.getId());
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("刪除失敗: " + e.getMessage(), e);
        }
    }



    //新增
    public int insert(ProductIns0122DAO dao) {
        String sql = "INSERT INTO product " +
                "(NAME, DESCRIPTION, PRICE, STOCK, CATEGORY, BRAND, SKU, STATUS, CREATED_TIME, UPDATED_TIME) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0;

        try{
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);
            ps.setString(1,dao.getName());
            ps.setString(2,dao.getDescription());
            ps.setInt(3,dao.getPrice());
            ps.setInt(4,dao.getStock());
            ps.setString(5, String.join(",", dao.getCategory()));
            ps.setString(6,dao.getBrand());
            ps.setString(7,dao.getSku());
            ps.setString(8, dao.getStatus());
            ps.setTimestamp(9, Timestamp.valueOf(dao.getCreatedTime()));
            ps.setTimestamp(10, Timestamp.valueOf(dao.getUpdatedTime()));
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
    public ProductUpdQuery0122DAO updQuery(ProductUpdQuery0122Req req) {
        String sql =
                "SELECT p.NAME, p.DESCRIPTION, p.PRICE, p.STOCK, d.CONTENT,e.CONTENT,p.SKU, " +
                 " c.CONTENT,p.CREATED_TIME, p.UPDATED_TIME " +
                 "FROM product p " +
                 "LEFT JOIN product_code c ON p.STATUS = c.CODE " +
                 "LEFT JOIN product_code d ON p.CATEGORY = d.CODE " +
                 "LEFT JOIN product_code e ON p.BRAND = e.CODE " +
                 "WHERE p.ID = ? ";



        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ProductUpdQuery0122DAO dao = new ProductUpdQuery0122DAO();

        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);//把sql準備好之後,讓她變成一個物件
            ps.setLong(1,req.getId());

            rs = ps.executeQuery();//查詢出的結果


            if (rs.next()) {
                dao.setName(rs.getString(1));
                dao.setDescription(rs.getString(2));
                dao.setPrice(rs.getInt(3));
                dao.setStock(rs.getInt(4));
                dao.setCategory(rs.getString(5));
                dao.setBrand(rs.getString(6));
                dao.setSku(rs.getString(7));
                dao.setStatus(rs.getString(8));
                dao.setCreatedTime(rs.getTimestamp(9).toLocalDateTime());
                dao.setUpdatedTime(rs.getTimestamp(10).toLocalDateTime());
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
    public int update(ProductUpd0122DAO dao) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE product SET ");

        int idx = 1;
        boolean hasSet = false;

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // 動態 SET
            if (dao.getName() != null) {
                sql.append("NAME = ?");
                hasSet = true;
            }
            if (dao.getDescription() != null) {
                if (hasSet) sql.append(", ");
                sql.append("DESCRIPTION = ?");
                hasSet = true;
            }


            if (dao.getPrice() != null) {
                if (hasSet) sql.append(", ");
                sql.append("PRICE = ?");
                hasSet = true;
            }


            if (dao.getStock() != null) {
                if (hasSet) sql.append(", ");
                sql.append("STOCK = ?");
                hasSet = true;
            }

            if (dao.getCategory() != null) {
                if (hasSet) sql.append(", ");
                sql.append("CATEGORY = ?");
                hasSet = true;
            }

            if (dao.getBrand() != null) {
                if (hasSet) sql.append(", ");
                sql.append("BRAND = ?");
                hasSet = true;
            }
            if (dao.getSku() != null) {
                if (hasSet) sql.append(", ");
                sql.append("SKU = ?");
                hasSet = true;
            }

            if (dao.getStatus() != null) {
                if (hasSet) sql.append(", ");
                sql.append("STATUS = ?");
                hasSet = true;
            }

            if (dao.getCreatedTime() != null) {
                if (hasSet) sql.append(", ");
                sql.append("CREATED_TIME = ?");
                hasSet = true;
            }

            if (dao.getUpdatedTime() != null) {
                if (hasSet) sql.append(", ");
                sql.append("UPDATED_TIME = ?");
                hasSet = true;
            }

            if (!hasSet) throw new RuntimeException("沒有任何可更新欄位");


            // WHERE
            sql.append(" WHERE ID = ?");

            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql.toString());

            // 依序塞值（跟 SET 的順序一致）
            if (dao.getName() != null) ps.setString(idx++, dao.getName());
            if (dao.getDescription() != null) ps.setString(idx++, dao.getDescription());
            if (dao.getPrice()!= null) ps.setInt(idx++, dao.getPrice());
            if (dao.getStock() != null) ps.setInt(idx++, dao.getStock());

            if (dao.getCategory() != null) {
                String categoryStr = dao.getCategory().stream()
                        .filter(x -> x != null && !x.trim().isEmpty())
                        .map(String::trim)
                        .collect(java.util.stream.Collectors.joining(","));
                // post.category NOT NULL：至少給空字串
                ps.setString(idx++, categoryStr);
            }
            if (dao.getBrand() != null) ps.setString(idx++, dao.getBrand());
            if (dao.getSku() != null) ps.setString(idx++, dao.getSku());
            if (dao.getStatus() != null) ps.setString(idx++, dao.getStatus());

            if (dao.getCreatedTime() != null)ps.setTimestamp(idx++, Timestamp.valueOf(dao.getCreatedTime()));

            if (dao.getUpdatedTime() != null)ps.setTimestamp(idx++, Timestamp.valueOf(dao.getUpdatedTime()));

            //WHERE ID 不可為 null
            if (dao.getId() == null) throw new RuntimeException("ID 不可為空");
            ps.setLong(idx, dao.getId());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("修改失敗: " + e.getMessage(), e);
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }



}




