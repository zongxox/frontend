package com.example.demo.mapper;

import com.example.demo.dao.*;
import com.example.demo.req.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {

    @Autowired
    private DataSource dataSource;



    /*查詢按鈕*/
    public List<PostQuery0121DAO> query(PostQuery0121Req req) {
        StringBuilder sql = new StringBuilder(
                "SELECT ID, TITLE, CONTENT, CODE, CATEGORY, TAGS, VIEWS, LIKES, STATUS, " +
                        "CREATED_TIME, UPDATED_TIME " +
                        "FROM post WHERE 1=1 "
        );


        List<Object> params = new ArrayList<>();

        // id 如果前端傳遞過來 不是null 就將條件加入到sql
        if (req.getCode() != null && !req.getCode().trim().isEmpty()) {
            sql.append(" AND CODE = ? ");
            params.add(req.getCode().trim());
        }



        if (req.getTitle() != null && !req.getTitle().trim().isEmpty()) {
            sql.append(" AND TITLE LIKE ? ");
            params.add("%" + req.getTitle().trim() + "%");
        }


        List<PostQuery0121DAO> list = new ArrayList<>();
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
                PostQuery0121DAO dao = new PostQuery0121DAO();
                dao.setId(rs.getLong("ID"));
                dao.setTitle(rs.getString("TITLE"));
                dao.setContent(rs.getString("CONTENT"));
                dao.setCode(rs.getString("CODE"));
                dao.setTags(rs.getString("TAGS"));
                dao.setViews(rs.getInt("VIEWS"));
                dao.setLikes(rs.getInt("LIKES"));
                dao.setCategory(rs.getString("CATEGORY"));
                dao.setStatus(rs.getInt("STATUS"));
                dao.setCreatedTime(rs.getTimestamp("CREATED_TIME").toLocalDateTime());
                dao.setUpdatedTime(rs.getTimestamp("UPDATED_TIME").toLocalDateTime());
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
    public int deleteById(PostDel0121Req req) {
        String sql = "DELETE FROM post WHERE ID = ?";

        PostDel0121DAO dao = new PostDel0121DAO();
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
    public int insert(PostIns0121Req req) {
        String sql = "INSERT INTO post " +
                "(title, content, category, code, tags, views, likes, status, created_time, updated_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";



        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0;
        PostIns0121DAO dao = new PostIns0121DAO();
        BeanUtils.copyProperties(req,dao);
        dao.setCode(req.getAuthor());
        try{
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);
            ps.setString(1, dao.getTitle());
            ps.setString(2, dao.getContent());
            ps.setString(3, String.join(",", dao.getCategory()));
            ps.setString(4, dao.getCode());
            ps.setString(5, dao.getTags());
            ps.setInt(6, dao.getViews());
            ps.setInt(7, dao.getLikes());
            ps.setInt(8, dao.getStatus());
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
    public PostUpdQuery0121DAO updQuery(PostUpdQuery0121Req req) {
        String sql =
                "SELECT ID, TITLE, CONTENT, CODE, CATEGORY, TAGS, VIEWS, LIKES, STATUS, " +
                        "CREATED_TIME, UPDATED_TIME " +
                        "FROM post " +
                        "WHERE ID = ? ";


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PostUpdQuery0121DAO dao = new PostUpdQuery0121DAO();
        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);//把sql準備好之後,讓她變成一個物件
            ps.setLong(1,req.getId());

            rs = ps.executeQuery();//查詢出的結果


            if (rs.next()) {
                dao.setTitle(rs.getString("TITLE"));
                dao.setContent(rs.getString("CONTENT"));
                dao.setCode(rs.getString("CODE"));
                dao.setTags(rs.getString("TAGS"));
                dao.setViews(rs.getInt("VIEWS"));
                dao.setLikes(rs.getInt("LIKES"));
                dao.setCategory(rs.getString("CATEGORY"));
                dao.setStatus(rs.getInt("STATUS"));
                dao.setCreatedTime(rs.getTimestamp("CREATED_TIME").toLocalDateTime());
                dao.setUpdatedTime(rs.getTimestamp("UPDATED_TIME").toLocalDateTime());
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


    public int update(PostUpd0121Req req) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE post SET ");

        int idx = 1;
        boolean hasSet = false;

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // 動態 SET
            if (req.getTitle() != null) {
                sql.append("TITLE = ?");
                hasSet = true;
            }
            if (req.getContent() != null) {
                if (hasSet) sql.append(", ");
                sql.append("CONTENT = ?");
                hasSet = true;
            }

            //category(List) -> String
            if (req.getCategory() != null) {
                if (hasSet) sql.append(", ");
                sql.append("CATEGORY = ?");
                hasSet = true;
            }

            //author 下拉用 author 送來，存到 CODE
            if (req.getAuthor() != null) {
                if (hasSet) sql.append(", ");
                sql.append("CODE = ?");
                hasSet = true;
            }

            if (req.getTags() != null) {
                if (hasSet) sql.append(", ");
                sql.append("TAGS = ?");
                hasSet = true;
            }
            if (req.getViews() != null) {
                if (hasSet) sql.append(", ");
                sql.append("VIEWS = ?");
                hasSet = true;
            }
            if (req.getLikes() != null) {
                if (hasSet) sql.append(", ");
                sql.append("LIKES = ?");
                hasSet = true;
            }
            if (req.getStatus() != null) {
                if (hasSet) sql.append(", ");
                sql.append("STATUS = ?");
                hasSet = true;
            }

            //updated_time：前端沒給也沒關係，後端自己補
            if (hasSet) sql.append(", ");
            sql.append("UPDATED_TIME = ?");
            hasSet = true;

            if (!hasSet) return 0;

            // WHERE
            sql.append(" WHERE ID = ?");

            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql.toString());

            // 依序塞值（跟 SET 的順序一致）
            if (req.getTitle() != null) ps.setString(idx++, req.getTitle());
            if (req.getContent() != null) ps.setString(idx++, req.getContent());

            if (req.getCategory() != null) {
                String categoryStr = req.getCategory().stream()
                        .filter(x -> x != null && !x.trim().isEmpty())
                        .map(String::trim)
                        .collect(java.util.stream.Collectors.joining(","));
                // post.category NOT NULL：至少給空字串
                ps.setString(idx++, categoryStr);
            }

            if (req.getAuthor() != null) ps.setString(idx++, req.getAuthor().trim());
            if (req.getTags() != null) ps.setString(idx++, req.getTags());
            if (req.getViews() != null) ps.setInt(idx++, req.getViews());
            if (req.getLikes() != null) ps.setInt(idx++, req.getLikes());
            if (req.getStatus() != null) ps.setInt(idx++, req.getStatus());

            //一定會有 updated_time
            ps.setTimestamp(idx++, Timestamp.valueOf(
                    req.getUpdatedTime() != null ? req.getUpdatedTime() : java.time.LocalDateTime.now()
            ));

            //WHERE ID 不可為 null
            if (req.getId() == null) throw new RuntimeException("ID 不可為空");
            ps.setLong(idx, req.getId());

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




