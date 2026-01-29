package com.example.demo.mapper;

import com.example.demo.dao.*;
import com.example.demo.req.OrderItem0126QueryReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderItem0126Repository {

    @Autowired
    private DataSource dataSource;

    public List<OrderItemInit0126DAO> init(){
        String sql = "SELECT CODE,CONTENT FROM ORDER_ITEM_CODE WHERE TYPE = 'STATUS'";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<OrderItemInit0126DAO> list = new ArrayList<>();
        try{
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);//把sql準備好之後,讓她變成一個物件
            rs = ps.executeQuery();//查詢出的結果

            while (rs.next()){
                OrderItemInit0126DAO dao = new OrderItemInit0126DAO();
                dao.setStatusCode(rs.getString(1));
                dao.setStatusContent(rs.getString(2));
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


    public List<OrderItemInit0126DAO> init1(){
        String sql = "SELECT CODE,CONTENT FROM ORDER_ITEM_CODE WHERE TYPE = 'QUANTITY'";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<OrderItemInit0126DAO> list = new ArrayList<>();
        try{
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);//把sql準備好之後,讓她變成一個物件
            rs = ps.executeQuery();//查詢出的結果

            while (rs.next()){
                OrderItemInit0126DAO dao = new OrderItemInit0126DAO();
                dao.setQuantityCode(rs.getString(1));
                dao.setQuantityContent(rs.getString(2));
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



    public List<OrderItemInit0126DAO> init2(){
        String sql = "SELECT CODE,CONTENT FROM ORDER_ITEM_CODE WHERE TYPE = 'PRODUCT_NAME'";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<OrderItemInit0126DAO> list = new ArrayList<>();
        try{
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);//把sql準備好之後,讓她變成一個物件
            rs = ps.executeQuery();//查詢出的結果

            while (rs.next()){
                OrderItemInit0126DAO dao = new OrderItemInit0126DAO();
                dao.setProductNameCode(rs.getString(1));
                dao.setProductNameContent(rs.getString(2));
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
    public List<OrderItem0126QueryDAO> query(OrderItem0126QueryReq req){
        StringBuilder sql = new StringBuilder( "SELECT o.ID,o.ORDER_ID,o.ITEM_ID,c.CONTENT," +
                     "b.CONTENT,o.UNIT_PRICE,o.DISCOUNT,a.CONTENT," +
                     "o.CREATED_AT,o.UPDATED_AT FROM order_item o " +
                     "LEFT JOIN order_item_code a ON  o.STATUS = a.CODE AND a.TYPE ='STATUS'" +
                     "LEFT JOIN order_item_code b ON  o.QUANTITY = b.CODE AND b.TYPE ='QUANTITY'" +
                     "LEFT JOIN order_item_code c ON  o.PRODUCT_NAME = c.CODE AND c.TYPE ='PRODUCT_NAME' WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (req.getItemId() != null ) {
            sql.append(" AND o.ITEM_ID = ? ");
            params.add(req.getItemId());
        }

        if (req.getProductName()!= null && !req.getProductName().trim().isEmpty()) {
            sql.append(" AND o.PRODUCT_NAME = ? ");
            params.add(req.getProductName().trim());
        }

        if (req.getStatus() != null && !req.getStatus().trim().isEmpty()) {
            sql.append(" AND o.STATUS = ? ");
            params.add(req.getStatus().trim());
        }


        if (req.getQuantity() != null && !req.getQuantity().isEmpty()) {
            sql.append(" AND o.QUANTITY IN (");
            for (int i = 0; i < req.getQuantity().size(); i++) {
                sql.append("?");
                if (i < req.getQuantity().size() - 1) {
                    sql.append(",");
                }
                params.add(req.getQuantity().get(i).trim());
            }
            sql.append(") ");
        }

        sql.append(" ORDER BY o.ID");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<OrderItem0126QueryDAO> list = new ArrayList<>();
        try{
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql.toString());//把sql準備好之後,讓她變成一個物件

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            rs = ps.executeQuery();//查詢出的結果

            while (rs.next()){
                OrderItem0126QueryDAO dao = new OrderItem0126QueryDAO();
                dao.setId(rs.getLong(1));
                dao.setOrderId(rs.getLong(2));
                dao.setItemId(rs.getLong(3));
                dao.setProductName(rs.getString(4));
                dao.setQuantity(rs.getString(5));
                dao.setUnitPrice(rs.getInt(6));
                dao.setDiscount(rs.getInt(7));
                dao.setStatus(rs.getString(8));
                dao.setCreatedAt(rs.getTimestamp(9).toLocalDateTime());
                dao.setUpdatedAt(rs.getTimestamp(10).toLocalDateTime());
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
    public int del(OrderItem0126DelDAO  dao){
        String sql = "DELETE FROM order_item WHERE ID = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0;
        try {
             conn = dataSource.getConnection();
             ps = conn.prepareStatement(sql);
             ps.setLong(1,dao.getId());
             rows = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("刪除失敗: " + e.getMessage(), e);
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

    //新增
    public int insert(OrderItem0126InsDAO dao){
        String sql = "INSERT INTO order_item(ORDER_ID, ITEM_ID, PRODUCT_NAME,QUANTITY, UNIT_PRICE, DISCOUNT, STATUS, CREATED_AT, UPDATED_AT)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";


        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0;

        try{
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);
            ps.setLong(1,dao.getOrderId());
            ps.setLong(2,dao.getItemId());
            ps.setString(3,dao.getProductName());
            ps.setString(4,String.join(",",dao.getQuantity()));
            ps.setInt(5,dao.getUnitPrice());
            ps.setInt(6,dao.getDiscount());
            ps.setString(7,dao.getStatus());
            ps.setTimestamp(8, Timestamp.valueOf(dao.getCreatedAt()));
            ps.setTimestamp(9,Timestamp.valueOf(dao.getUpdatedAt()));

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

    public int update(OrderItemUpd0126DAO dao) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE order_item SET ");

        int idx = 1;
        boolean hasSet = false;

        if (dao.getOrderId() != null) {
            sql.append("ORDER_ID = ?");
            hasSet = true;
        }
        if (dao.getItemId() != null) {
            if (hasSet) sql.append(", ");
            sql.append("ITEM_ID = ?");
            hasSet = true;
        }
        if (dao.getProductName() != null) {
            if (hasSet) sql.append(", ");
            sql.append("PRODUCT_NAME = ?");
            hasSet = true;
        }
        if (dao.getQuantity() != null) {
            if (hasSet) sql.append(", ");
            sql.append("QUANTITY = ?");
            hasSet = true;
        }
        if (dao.getUnitPrice() != null) {
            if (hasSet) sql.append(", ");
            sql.append("UNIT_PRICE = ?");
            hasSet = true;
        }
        if (dao.getDiscount() != null) {
            if (hasSet) sql.append(", ");
            sql.append("DISCOUNT = ?");
            hasSet = true;
        }
        if (dao.getStatus() != null) {
            if (hasSet) sql.append(", ");
            sql.append("STATUS = ?");
            hasSet = true;
        }
        if (dao.getCreatedAt() != null) {
            if (hasSet) sql.append(", ");
            sql.append("CREATED_AT = ?");
            hasSet = true;
        }
        if (dao.getUpdatedAt() != null) {
            if (hasSet) sql.append(", ");
            sql.append("UPDATED_AT = ?");
            hasSet = true;
        }

        if (!hasSet) throw new RuntimeException("沒有任何可更新欄位");
        if (dao.getId() == null) throw new RuntimeException("缺少 ID，無法更新");

        sql.append(" WHERE ID = ?");

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection(); // 你如果不是 dataSource，就改成 DriverManager.getConnection(...)
            ps = conn.prepareStatement(sql.toString());

            if (dao.getOrderId() != null) ps.setLong(idx++, dao.getOrderId());
            if (dao.getItemId() != null) ps.setLong(idx++, dao.getItemId());
            if (dao.getProductName() != null) ps.setString(idx++, dao.getProductName());

            if (dao.getQuantity() != null) {
                //String qStr = String.join(",", dao.getQuantity());
                ps.setString(idx++,dao.getQuantity());
               // ps.setString(idx++, qStr);
            }

            if (dao.getUnitPrice() != null) ps.setInt(idx++, dao.getUnitPrice());
            if (dao.getDiscount() != null) ps.setInt(idx++, dao.getDiscount());

            if (dao.getStatus() != null) ps.setString(idx++, dao.getStatus());
            if (dao.getCreatedAt() != null) {
                ps.setTimestamp(idx++, Timestamp.valueOf(dao.getCreatedAt()));
            }
            if (dao.getUpdatedAt() != null) {
                ps.setTimestamp(idx++, Timestamp.valueOf(dao.getUpdatedAt()));
            }



            ps.setLong(idx++, dao.getId());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("修改失敗: " + e.getMessage(), e);

        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }


    // poi上傳
    public int insert1(OrderItemUpd0126DAO dao) throws Exception {

        String sql = "INSERT INTO ORDER_ITEM " +
                "(ORDER_ID, ITEM_ID, PRODUCT_NAME, QUANTITY, UNIT_PRICE, DISCOUNT, STATUS, CREATED_AT, UPDATED_AT) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, dao.getOrderId());
            ps.setLong(2, dao.getItemId());
            ps.setString(3, dao.getProductName());
            ps.setString(4, dao.getQuantity());
            ps.setInt(5, dao.getUnitPrice());
            ps.setInt(6, dao.getDiscount());
            ps.setString(7, dao.getStatus());
            ps.setTimestamp(8, Timestamp.valueOf(dao.getCreatedAt()));
            ps.setTimestamp(9, Timestamp.valueOf(dao.getUpdatedAt()));

            return ps.executeUpdate();
        }
    }


    //下載
    public List<OrderItemUpd0126DAO> findAll() throws Exception {

        String sql = "SELECT ID, ORDER_ID, ITEM_ID, PRODUCT_NAME, QUANTITY, UNIT_PRICE, DISCOUNT, STATUS, CREATED_AT, UPDATED_AT " +
                "FROM ORDER_ITEM ORDER BY ID";

        List<OrderItemUpd0126DAO> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                OrderItemUpd0126DAO dao = OrderItemUpd0126DAO.builder()
                        .id(rs.getLong("ID"))
                        .orderId(rs.getLong("ORDER_ID"))
                        .itemId(rs.getLong("ITEM_ID"))
                        .productName(rs.getString("PRODUCT_NAME"))
                        .quantity(rs.getString("QUANTITY"))
                        .unitPrice(rs.getInt("UNIT_PRICE"))
                        .discount(rs.getInt("DISCOUNT"))
                        .status(rs.getString("STATUS"))
                        .createdAt(rs.getTimestamp("CREATED_AT").toLocalDateTime())
                        .updatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime())
                        .build();

                list.add(dao);
            }
        }

        return list;
    }
}




