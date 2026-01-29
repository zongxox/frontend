package com.example.demo.servicec;

import com.example.demo.dao.*;
import com.example.demo.mapper.User0127Repository;
import com.example.demo.req.*;
import com.example.demo.res.UserInit0127Res;
import com.example.demo.res.UserQuery0127Res;
import com.example.demo.res.UserUpdQuery0127Res;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
public class User0127Service {
    @Autowired
    private User0127Repository user0127Repository;

    public List<UserInit0127Res> init() {
        List<UserInit0127DAO> dao = user0127Repository.init();
        List<UserInit0127Res> res = new ArrayList<>();
        for (UserInit0127DAO s : dao) {
            UserInit0127Res userInit0127Res = new UserInit0127Res();
            BeanUtils.copyProperties(s, userInit0127Res);
            res.add(userInit0127Res);
        }
        return res;
    }

    //查詢按鈕
    public List<UserQuery0127Res> query(UserQuery0127Req req) {
        List<UserQuery0127DAO> dao = user0127Repository.query(req);
        List<UserQuery0127Res> res = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        for (UserQuery0127DAO s : dao) {
            UserQuery0127Res userQuery0127Res = new UserQuery0127Res();
            BeanUtils.copyProperties(s, userQuery0127Res);
            if (userQuery0127Res.getCreDate() != null) {
                userQuery0127Res.setCreDateStr(userQuery0127Res.getCreDate().format(formatter));
            }
            if (userQuery0127Res.getUpdDate() != null) {
                userQuery0127Res.setUpdDateStr(userQuery0127Res.getUpdDate().format(formatter2));
            }

            res.add(userQuery0127Res);
        }
        return res;
    }

    //刪除
    public int del(UserDel0127Req req) {
        UserDel0127DAO dao = new UserDel0127DAO();
        dao.setId(Integer.valueOf(req.getId()));
        int rows = user0127Repository.del(dao);
        return rows;
    }

    //新增
    public int insert(UserInd0127Req req) {
        int rows = user0127Repository.insert(req);
        return rows;
    }

    //跳轉畫面查詢
    public UserUpdQuery0127Res updQuery(String id) {
        UserUpdQuery0127Req req = new UserUpdQuery0127Req();
        req.setId(Integer.valueOf(id));
        UserUpdQuery0127DAO dao = user0127Repository.updQuery(req);
        UserUpdQuery0127Res res = new UserUpdQuery0127Res();
        BeanUtils.copyProperties(dao, res);
        return res;
    }

    //修改
    public int update(UserUpd0127Req req) {
        UserUpd0127DAO dao = new UserUpd0127DAO();
        dao.setId(req.getId());
        dao.setName(req.getName());
        dao.setAccount(req.getAccount());
        dao.setPassword(req.getPassword());
        dao.setPhone(req.getPhone());
        dao.setEmail(req.getEmail());
        dao.setAddress(req.getAddress());
        String zipcodesStr = String.join(",", req.getZipcodes());
        dao.setZipcodes(zipcodesStr);
        dao.setCreUser(req.getCreUser());
        dao.setCreDate(req.getCreDate());
        dao.setUpdUser(req.getUpdUser());
        dao.setUpdDate(req.getUpdDate());
        int rows = user0127Repository.update(dao);
        return rows;
    }

    //上傳
    public void importExcel(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("請選擇檔案");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
            throw new RuntimeException("只支援 .xlsx 或 .xls");
        }

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();

            // 如果你 Excel 第0列是表頭，就從第1列開始讀
            int startRow = 1;


            for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                if (isRowEmpty(row, fmt)) continue;

                String name     = getCell(row, 0, fmt);
                String password = getCell(row, 1, fmt);
                String account  = getCell(row, 2, fmt);
                String phone    = getCell(row, 3, fmt);
                String email    = getCell(row, 4, fmt);
                String address  = getCell(row, 5, fmt);
                String zipcodes = getCell(row, 6, fmt);
                String creUser  = getCell(row, 7, fmt);
                LocalDateTime creDate = getExcelDateTime(row, 8);
                String updUser  = getCell(row, 9, fmt);
                LocalDateTime updDate = getExcelDateTime(row, 10);


                UserInd0127DAO dao = new UserInd0127DAO();
                dao.setName(name);
                dao.setPassword(password);
                dao.setAccount(account);
                dao.setPhone(phone);
                dao.setEmail(email);
                dao.setAddress(address);
                dao.setZipcodes(zipcodes);
                dao.setCreUser(creUser);
                dao.setCreDate(creDate);
                dao.setUpdUser(updUser);
                dao.setUpdDate(updDate);


                user0127Repository.insert1(dao);
            }


        } catch (Exception e) {
            throw new RuntimeException("匯入失敗：" + e.getMessage(), e);
        }
    }

    private LocalDateTime getExcelDateTime(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell == null) return null;

        // 1) Excel 日期型（最穩）
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            Date d = cell.getDateCellValue();
            return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }

        // 2) 字串型（走你原本 parseExcelDateTime）
        if (cell.getCellType() == CellType.STRING) {
            return parseExcelDateTime(cell.getStringCellValue());
        }

        // 3) 公式
        if (cell.getCellType() == CellType.FORMULA) {
            if (cell.getCachedFormulaResultType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                Date d = cell.getDateCellValue();
                return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            }
            if (cell.getCachedFormulaResultType() == CellType.STRING) {
                return parseExcelDateTime(cell.getStringCellValue());
            }
        }

        // 其他型別就當空
        return null;
    }


    private LocalDateTime parseExcelDateTime(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;

        List<DateTimeFormatter> formats = Arrays.asList(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"),


                DateTimeFormatter.ofPattern("M/d/yy H:mm"),
                DateTimeFormatter.ofPattern("M/d/yy HH:mm"),


                DateTimeFormatter.ofPattern("M/d/yyyy H:mm"),
                DateTimeFormatter.ofPattern("M/d/yyyy HH:mm")
        );

        for (DateTimeFormatter f : formats) {
            try {
                return LocalDateTime.parse(s, f);
            } catch (DateTimeParseException ignore) {}
        }

        throw new RuntimeException("日期格式錯誤：" + s);
    }


    private String getCell(Row row, int colIndex, DataFormatter fmt) {
        Cell cell = row.getCell(colIndex);
        if (cell == null) return null;

        String val = fmt.formatCellValue(cell).trim();
        return val.isEmpty() ? null : val;
    }

    private boolean isRowEmpty(Row row, DataFormatter fmt) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && !fmt.formatCellValue(cell).trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    //下載
    public List<UserQuery0127Res> downloadList(UserQuery0127Req req) {

        //2.呼叫 MyBatis / Mapper，依照 param 條件查詢資料庫
        List<UserQuery0127DAO> list = user0127Repository.query(req);

        //3.將查詢結果 DTO 轉成回傳用的 Response 物件 AdRes
        List<UserQuery0127Res> rows = new ArrayList<>();

        //逐筆處理查詢出來的資料
        for (UserQuery0127DAO s : list) {

            //建立一筆回傳用的 AdRes 物件
            UserQuery0127Res r = new UserQuery0127Res();

            //將 DTO 中同名欄位的資料複製到 AdRes
            BeanUtils.copyProperties(s, r);

            //將轉換完成的物件加入回傳清單
            rows.add(r);
        }

        //4.回傳整理完成的資料清單（供 Excel 匯出使用）
        return rows;
    }

}


