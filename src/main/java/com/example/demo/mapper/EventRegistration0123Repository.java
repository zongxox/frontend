package com.example.demo.mapper;

import com.example.demo.dao.*;
import com.example.demo.req.EventRegistrationQuery0123Req;
import com.example.demo.req.EventRegistrationUpdQuery0123Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventRegistration0123Repository {

    @Autowired
    private DataSource dataSource;

    /*init查詢*/
    public List<EventRegistrationInit0123DAO> init() {
        String sql =
                "SELECT  e.CODE, e.CONTENT FROM event_registration p " +
                "LEFT JOIN member_detail e ON e.TYPE='event_name' AND e.CODE = p.event_name ";

        List<EventRegistrationInit0123DAO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql.toString());//把sql準備好之後,讓她變成一個物件

            rs = ps.executeQuery();//查詢出的結果
            while (rs.next()) {//將每一筆結果放到 dao
                EventRegistrationInit0123DAO dao = new EventRegistrationInit0123DAO();
                dao.setEventNameCode(rs.getString(1));
                dao.setEventNameContent(rs.getString(2));
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
    public List<EventRegistrationInit0123DAO> init1() {
        String sql =
                "SELECT  e.CODE, e.CONTENT FROM event_registration p " +
                        "LEFT JOIN member_detail e ON e.TYPE='status_code' AND e.CODE = p.status_code ";

        List<EventRegistrationInit0123DAO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql.toString());//把sql準備好之後,讓她變成一個物件

            rs = ps.executeQuery();//查詢出的結果
            while (rs.next()) {//將每一筆結果放到 dao
                EventRegistrationInit0123DAO dao = new EventRegistrationInit0123DAO();
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


    public List<EventRegistrationInit0123DAO> init2() {
        String sql =
                "SELECT  e.CODE, e.CONTENT FROM event_registration p " +
                        "LEFT JOIN member_detail e ON e.TYPE='option_codes' AND e.CODE = p.option_codes ";

        List<EventRegistrationInit0123DAO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql.toString());//把sql準備好之後,讓她變成一個物件

            rs = ps.executeQuery();//查詢出的結果
            while (rs.next()) {//將每一筆結果放到 dao
                EventRegistrationInit0123DAO dao = new EventRegistrationInit0123DAO();
                dao.setOptionCodesCode((rs.getString(1)));
                dao.setOptionCodesContent((rs.getString(2)));
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
    public List<EventRegistrationQueryDAO> query(EventRegistrationQuery0123Req req) {
                StringBuilder sql = new StringBuilder(
                "SELECT e.member_id, e.event_code, " +
                        "DATE_FORMAT(e.register_time,'%Y-%m-%d %H:%i:%s'), " +
                        "DATE_FORMAT(e.cancel_time,'%Y/%m/%d '), " +
                        "DATE_FORMAT(e.update_time,'%H:%i:%s'), " +
                        "c.CONTENT , d.CONTENT , e.phone, e.email, e.note, f.CONTENT " +
                        "FROM event_registration e " +
                        "LEFT JOIN member_detail c ON e.event_name = c.CODE AND c.TYPE='event_name' " +
                        "LEFT JOIN member_detail d ON e.status_code = d.CODE AND d.TYPE='status_code' " +
                        "LEFT JOIN member_detail f ON e.option_codes = f.CODE AND f.TYPE='option_codes' " +
                        "WHERE 1=1 "
        );



        List<Object> params = new ArrayList<>();


        if (req.getRegisterTime() != null && !req.getRegisterTime().trim().isEmpty()
                && req.getCancelTime() != null && !req.getCancelTime().trim().isEmpty()) {
            sql.append(" AND DATE_FORMAT(e.register_time,'%Y%m%d') BETWEEN ? AND ? ");
            params.add(req.getRegisterTime().trim()); // 起日 20260101
            params.add(req.getCancelTime().trim());   // 迄日 20260105
        }




        if (req.getStatusCode() != null && !req.getStatusCode().trim().isEmpty()) {
            sql.append(" AND e.status_code = ? ");
            params.add(req.getStatusCode().trim());
        }

        // id 如果前端傳遞過來 不是null 就將條件加入到sql
        if (req.getEventName()!= null && !req.getEventName().trim().isEmpty()) {
            sql.append(" AND e.event_name = ? ");
            params.add(req.getEventName().trim());
        }

        if (req.getOptionCodes() != null && !req.getOptionCodes().isEmpty()) {
            sql.append(" AND e.option_codes IN (");
            for (int i = 0; i < req.getOptionCodes().size(); i++) {
                sql.append("?");
                if (i < req.getOptionCodes().size() - 1) {
                    sql.append(",");
                }
                params.add(req.getOptionCodes().get(i).trim());
            }
            sql.append(") ");
        }

        sql.append(" ORDER BY e.member_id");

        List<EventRegistrationQueryDAO> list = new ArrayList<>();
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
                EventRegistrationQueryDAO dao = new EventRegistrationQueryDAO();
                dao.setMemberId(rs.getLong(1));
                dao.setEventCode(rs.getString(2));
                dao.setRegisterTime(rs.getString(3));
                dao.setCancelTime(rs.getString(4));
                dao.setUpdateTime(rs.getString(5));
                dao.setEventName(rs.getString(6));     // c.CONTENT
                dao.setStatusCode(rs.getString(7));    // d.CONTENT
                dao.setPhone(rs.getString(8));
                dao.setEmail(rs.getString(9));
                dao.setNote(rs.getString(10));
                dao.setOptionCodes(rs.getString(11));  // f.CONTENT
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
    public int deleteById(EventRegistrationDel0123DAO dao) {
        String sql = "DELETE FROM event_registration WHERE member_id = ?";
        String sql1 = "DELETE FROM member_detail WHERE member_id = ?";

            int rows = 0;
            int row=0;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement ps1 = conn.prepareStatement(sql);
            ps1.setLong(1,dao.getMemberId());
            rows = ps1.executeUpdate();
            PreparedStatement ps2 = conn.prepareStatement(sql1);
            ps2.setLong(1,dao.getMemberId());
            row = ps2.executeUpdate();
            return rows + row;


        } catch (SQLException e) {
            throw new RuntimeException("刪除失敗: " + e.getMessage(), e);
        }
    }


    //主表新增
    public int insert(EventRegistrationIns0123DAO dao) {
        String sql = "INSERT INTO event_registration(member_id, event_code, register_time,event_name, status_code, phone, email, note, option_codes, cancel_time,update_time)" +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";


        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0;

        try{
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);
            ps.setLong(1, dao.getMemberId());
            ps.setString(2, dao.getEventCode());
            ps.setTimestamp(3, dao.getRegisterTime());
            ps.setString(4, dao.getEventName());
            ps.setString(5, dao.getStatusCode());
            ps.setString(6, dao.getPhone());
            ps.setString(7, dao.getEmail());
            ps.setString(8, dao.getNote());
            ps.setString(9, String.join(",", dao.getOptionCodes()));
            ps.setTimestamp(10, dao.getCancelTime());
            ps.setTimestamp(11, dao.getUpdateTime());
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


    //副表新增
    public int insert1(MemberDetail0123DAO dao) {

        String sql = "INSERT INTO member_detail(member_id,name,gender,phone,id_number,birth_date,address,hobby ) " +
                "VALUES (?, ?, ?, ?,?,?,?,?);";

        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0;

        try{
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);
            ps.setLong(1,dao.getMemberId());
            ps.setString(2,dao.getName());
            ps.setString(3,dao.getGender());
            ps.setString(4,dao.getPhone());
            ps.setString(5,dao.getIdNumber());
            if (dao.getBirthDate() != null) {
                ps.setDate(6, new java.sql.Date(dao.getBirthDate().getTime()));
            } else {
                ps.setDate(6, null);
            }

            ps.setString(7,dao.getAddress());
            ps.setString(8,dao.getHobby());
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
    public EventRegistrationUpdQuery0123DAO updQuery(EventRegistrationUpdQuery0123Req req) {
        String sql = "SELECT " +
                "e.member_id,e.event_code,e.register_time,e.event_name,e.status_code,e.phone," +
                "e.email,e.note,e.option_codes,e.cancel_time,e.update_time," +
                "m.member_id,m.name,m.gender,m.phone,m.id_number,m.birth_date,m.address,m.hobby " +
                "FROM event_registration e " +
                "LEFT JOIN member_detail m ON e.member_id = m.member_id WHERE e.member_id = ?;";




        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        EventRegistrationUpdQuery0123DAO dao = new EventRegistrationUpdQuery0123DAO();

        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);//把sql準備好之後,讓她變成一個物件
            ps.setLong(1,req.getMemberId());

            rs = ps.executeQuery();//查詢出的結果


            if (rs.next()) {
                // event_registration (主表)
                dao.setEventCode(rs.getString(2));
                dao.setRegisterTime(rs.getString(3));
                dao.setEventName(rs.getString(4));
                dao.setStatusCode(rs.getString(5));
                dao.setPhone(rs.getString(6));
                dao.setEmail(rs.getString(7));
                dao.setNote(rs.getString(8));
                dao.setOptionCodes(rs.getString(9));
                dao.setCancelTime(rs.getString(10));
                dao.setUpdateTime(rs.getString(11));

                // member_detail (副表)
                MemberDetail0123DAO m = new MemberDetail0123DAO();
                m.setMemberId(rs.getLong(12));
                m.setName(rs.getString(13));
                m.setGender(rs.getString(14));
                m.setPhone(rs.getString(15));
                m.setIdNumber(rs.getString(16));
                m.setBirthDate(rs.getDate(17));
                m.setAddress(rs.getString(18));
                m.setHobby(rs.getString(19));

                dao.setMemberDetail(m);
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
    public int update(EventRegistrationUpd0123DAO dao) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE event_registration SET ");

        int idx = 1;
        boolean hasSet = false;

        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            // 動態 SET
            if (dao.getEventCode() != null) {
                sql.append("event_code = ?");
                hasSet = true;
            }
            if (dao.getRegisterTime() != null) {
                if (hasSet) sql.append(", ");
                sql.append("register_time = ?");
                hasSet = true;
            }


            if (dao.getEventName() != null) {
                if (hasSet) sql.append(", ");
                sql.append("event_name = ?");
                hasSet = true;
            }


            if (dao.getStatusCode() != null) {
                if (hasSet) sql.append(", ");
                sql.append("status_code = ?");
                hasSet = true;
            }

            if (dao.getPhone() != null) {
                if (hasSet) sql.append(", ");
                sql.append("phone = ?");
                hasSet = true;
            }

            if (dao.getEmail() != null) {
                if (hasSet) sql.append(", ");
                sql.append("email = ?");
                hasSet = true;
            }
            if (dao.getNote() != null) {
                if (hasSet) sql.append(", ");
                sql.append("note = ?");
                hasSet = true;
            }

            if (dao.getOptionCodes() != null) {
                if (hasSet) sql.append(", ");
                sql.append("option_codes = ?");
                hasSet = true;
            }

            if (dao.getCancelTime() != null) {
                if (hasSet) sql.append(", ");
                sql.append("cancel_time = ?");
                hasSet = true;
            }

            if (dao.getUpdateTime() != null) {
                if (hasSet) sql.append(", ");
                sql.append("update_time = ?");
                hasSet = true;
            }

            if (!hasSet) throw new RuntimeException("沒有任何可更新欄位");


            // WHERE
            sql.append(" WHERE member_id = ?");

            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql.toString());

            // 依序塞值（跟 SET 的順序一致）
            if (dao.getEventCode() != null) ps.setString(idx++, dao.getEventCode());
            if (dao.getRegisterTime() != null) ps.setString(idx++, dao.getRegisterTime());
            if (dao.getEventName()!= null) ps.setString(idx++, dao.getEventName());
            if (dao.getStatusCode() != null) ps.setString(idx++, dao.getStatusCode());


            if (dao.getPhone() != null) ps.setString(idx++, dao.getPhone());
            if (dao.getEmail() != null) ps.setString(idx++, dao.getEmail());
            if (dao.getNote() != null) ps.setString(idx++, dao.getNote());

            if (dao.getOptionCodes() != null) {
                ps.setString(idx++, String.join(",", dao.getOptionCodes()));
            }

            if (dao.getCancelTime() != null)ps.setTimestamp(idx++, Timestamp.valueOf(dao.getCancelTime()));
            if (dao.getUpdateTime() != null)ps.setTimestamp(idx++, Timestamp.valueOf(dao.getUpdateTime()));
            //WHERE ID 不可為 null
            if (dao.getMemberId()== null) throw new RuntimeException("getMemberID 不可為空");
            ps.setLong(idx, dao.getMemberId());
            int updated1 = ps.executeUpdate();


            MemberDetail0123DAO md = dao.getMemberDetail();
            if (md.getMemberId() == null) md.setMemberId(dao.getMemberId()); // 沿用外層 memberId

            StringBuilder sql2 = new StringBuilder();
            sql2.append("UPDATE member_detail SET ");

            int idx2 = 1;
            boolean hasSet2 = false;
            if (md.getName() != null) { sql2.append("name = ?"); hasSet2 = true; }
            if (md.getGender() != null) { if (hasSet2) sql2.append(", "); sql2.append("gender = ?"); hasSet2 = true; }
            if (md.getPhone() != null) { if (hasSet2) sql2.append(", "); sql2.append("phone = ?"); hasSet2 = true; }
            if (md.getIdNumber() != null) { if (hasSet2) sql2.append(", "); sql2.append("id_number = ?"); hasSet2 = true; }
            if (md.getBirthDate() != null) { if (hasSet2) sql2.append(", "); sql2.append("birth_date = ?"); hasSet2 = true; }
            if (md.getAddress() != null) { if (hasSet2) sql2.append(", "); sql2.append("address = ?"); hasSet2 = true; }
            if (md.getHobby() != null) { if (hasSet2) sql2.append(", "); sql2.append("hobby = ?"); hasSet2 = true; }
            sql2.append(" WHERE member_id = ?");
            ps2 = conn.prepareStatement(sql2.toString());
            if (md.getName() != null) ps2.setString(idx2++, md.getName());
            if (md.getGender() != null) ps2.setString(idx2++, md.getGender());
            if (md.getPhone() != null) ps2.setString(idx2++, md.getPhone());
            if (md.getIdNumber() != null) ps2.setString(idx2++, md.getIdNumber());
            // birth_date 假設是 yyyy-MM-dd
            if (md.getBirthDate() != null) {
                ps2.setDate(idx2++, new java.sql.Date(md.getBirthDate().getTime()));
            }

            if (md.getAddress() != null) ps2.setString(idx2++, md.getAddress());
            if (md.getHobby() != null) ps2.setString(idx2++, md.getHobby());

            ps2.setLong(idx2, md.getMemberId());
            int updated2 = ps2.executeUpdate();

            return updated1 + updated2;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("修改失敗: " + e.getMessage(), e);
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    // 查詢檔案路徑 + 檔案名稱
    //傳入水果資料的 id，回傳 路徑+檔名 的 M
    public EventRegistrationDon0123DAO queryFileInfo(Long memberId) {
        //SQL：用 ID 查 FRUIT_CODE(檔名) 和 FRUIT_TYPE(路徑)
        String sql = "SELECT phone,email FROM event_registration WHERE member_id = ?";

        //從 dataSource 取得資料庫連線（try-with-resources 自動關閉）
        try (Connection conn = dataSource.getConnection();
             //預備 SQL 語法（PreparedStatement 可避免 SQL Injection）
             PreparedStatement ps = conn.prepareStatement(sql)) {
            //把 SQL 裡的第 1 個 ? 參數帶入 id
            ps.setLong(1, memberId);
            //執行查詢，回傳 ResultSet（查詢結果）
            try (ResultSet rs = ps.executeQuery()) {

                //rs.next() 會移到第一筆資料；如果沒有資料表示查不到
                //查不到該 id 就回傳 null（表示沒檔案資訊）
                if (!rs.next()) {
                    return null;
                }

                EventRegistrationDon0123DAO dao = new EventRegistrationDon0123DAO();
                dao.setPhone(rs.getString("phone"));
                dao.setEmail(rs.getString("email"));
                return dao;
            }

        } catch (Exception e) {//發生任何例外（SQL錯誤、連線失敗、欄位錯等等）
            //包成 RuntimeException 丟出去，方便看錯誤原因
            throw new RuntimeException("FruitRepository queryFileInfo 失敗：" + e.getMessage(), e);
        }
    }

    //上傳
    public void updateFileInfo(Long memberId, String email, String phone) {
        String sql = "UPDATE event_registration SET email=?, phone=? WHERE MEMBER_ID=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, phone);
            ps.setLong(3, memberId);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("updateFileInfo 失敗：" + e.getMessage(), e);
        }
    }


    /*查詢按鈕*/
    public List<ErTest123DAO> testQuery() {
        List<MemberDetail0123DAO> allDetails = testQuery1();


        String sql = "SELECT member_id, event_code, register_time,event_name, status_code, phone, email, note, option_codes, cancel_time,update_time FROM event_registration";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<ErTest123DAO> list = new ArrayList<>();

        try {
            conn = dataSource.getConnection();//連線數據庫
            ps = conn.prepareStatement(sql);//把sql準備好之後,讓她變成一個物件

            rs = ps.executeQuery();//查詢出的結果
            while (rs.next()) {//將每一筆結果放到 dao
                ErTest123DAO dao = new ErTest123DAO();
                dao.setMemberId(rs.getLong(1));
                dao.setEventCode(rs.getString(2));
                dao.setRegisterTime(rs.getString(3));
                dao.setEventName(rs.getString(4));
                dao.setStatusCode(rs.getString(5));
                dao.setPhone(rs.getString(6));
                dao.setEmail(rs.getString(7));
                dao.setNote(rs.getString(8));
                dao.setOptionCodes(rs.getString(9));
                dao.setCancelTime(rs.getTimestamp(10));
                dao.setUpdateTime(rs.getTimestamp(11));
                list.add(dao);//把每一筆的res 循環結果放到list

                List<MemberDetail0123DAO> details = new ArrayList<>();
                for (MemberDetail0123DAO d : allDetails) {
                    if (d.getMemberId().equals(dao.getMemberId())) {
                        details.add(d);
                    }
                }

                dao.setMemberDetail(details);

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

    private List<MemberDetail0123DAO> testQuery1() {

        String sql = "SELECT member_id, name, gender, phone, id_number, " +
                "birth_date, address, hobby " +
                "FROM member_detail";

        List<MemberDetail0123DAO> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MemberDetail0123DAO d = new MemberDetail0123DAO();
                d.setMemberId(rs.getLong(1));
                d.setName(rs.getString(2));
                d.setGender(rs.getString(3));
                d.setPhone(rs.getString(4));
                d.setIdNumber(rs.getString(5));
                d.setBirthDate(rs.getDate(6));
                d.setAddress(rs.getString(7));
                d.setHobby(rs.getString(8));
                list.add(d);
            }

        } catch (SQLException e) {
            throw new RuntimeException("查詢 member_detail 失敗", e);
        }

        return list;
    }




}




