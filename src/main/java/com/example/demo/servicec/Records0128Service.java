package com.example.demo.servicec;

import com.example.demo.dao.*;
import com.example.demo.entity.Records;
import com.example.demo.mapper.EventRegistration0123Repository;
import com.example.demo.mapper.Records0128Repository;
import com.example.demo.req.RecordsQuery0128Req;
import com.example.demo.req.RecordsUp0128Req;
import com.example.demo.req.RecordsUpd0128Req;
import com.example.demo.res.ErTest123Res;
import com.example.demo.res.RecordsInt0128Res;
import com.example.demo.res.RecordsQuery0128Res;
import com.example.demo.res.RecordsUp0128Res;
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
import java.util.*;


@Service
public class Records0128Service {
    @Autowired
    private Records0128Repository records0128Repository;


    //頁面初始化查詢
    public List<RecordsInt0128Res> init() {
        List<RecordsInt0128DAO> init = records0128Repository.init();
        List<RecordsInt0128Res> res = new ArrayList<>();
        for (RecordsInt0128DAO s : init) {
            RecordsInt0128Res recordsInt0128Res = new RecordsInt0128Res();
            BeanUtils.copyProperties(s, recordsInt0128Res);
            if ("1".equals(recordsInt0128Res.getStatus())) {
                recordsInt0128Res.setStatusName("啟用");
            } else if ("0".equals(recordsInt0128Res.getStatus())) {
                recordsInt0128Res.setStatusName("停用");
            }
            res.add(recordsInt0128Res);
        }
        return res;
    }

    //查詢按鈕
    public List<RecordsQuery0128Res> query(RecordsQuery0128Req req) {
        List<RecordsQuery0128DAO> dao = records0128Repository.query(req);
        List<RecordsQuery0128Res> res = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        for (RecordsQuery0128DAO s : dao) {
            RecordsQuery0128Res recordsQuery0128Res = new RecordsQuery0128Res();
            BeanUtils.copyProperties(s, recordsQuery0128Res);
            if (recordsQuery0128Res.getStartTime() != null) {
                recordsQuery0128Res.setStartTimeStr(recordsQuery0128Res.getStartTime().format(formatter));
            }
            if (recordsQuery0128Res.getEndTime() != null) {
                recordsQuery0128Res.setEndTimeStr(recordsQuery0128Res.getEndTime().format(formatter2));
            }
            if (recordsQuery0128Res.getCreatedAt() != null) {
                recordsQuery0128Res.setCreatedAtStr(recordsQuery0128Res.getCreatedAt().format(formatter3));
            }
            if (recordsQuery0128Res.getUpdatedAt() != null) {
                recordsQuery0128Res.setUpdatedAtStr(recordsQuery0128Res.getUpdatedAt().format(formatter4));
            }
            if ("1".equals(recordsQuery0128Res.getStatus())) {
                recordsQuery0128Res.setStatusName("啟用");
            } else if ("0".equals(recordsQuery0128Res.getStatus())) {
                recordsQuery0128Res.setStatusName("停用");
            }
            res.add(recordsQuery0128Res);
        }
        return res;
    }


    //修改
    public int update(RecordsUpd0128Req req) {
        RecordsUpd0128DAO dao = RecordsUpd0128DAO.builder()
                .id(req.getId())
                .userId(Integer.valueOf(req.getUserId()))
                .title(req.getTitle())
                .description(req.getDescription())
                .startTime(LocalDateTime.parse(req.getStartTime()))
                .endTime(LocalDateTime.parse(req.getEndTime()))
                .status(req.getStatus())
                .location(req.getLocation())
                .createdAt(LocalDateTime.parse(req.getCreatedAt()))
                .updatedAt(LocalDateTime.parse(req.getUpdatedAt()))
                .build();
        int rows = records0128Repository.update(dao);
        return rows;
    }


    //讀取上傳檔案
    public List<RecordsUp0128Res> importExcel(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("請選擇檔案");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
            throw new RuntimeException("只支援 .xlsx 或 .xls");
        }

        List<RecordsUp0128Res> res = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();

            // 如果你 Excel 第0列是表頭，就從第1列開始讀
            int startRow = 1;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

            for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                if (isRowEmpty(row, fmt)) continue;

                String userId = getCell(row, 0, fmt);
                String title = getCell(row, 1, fmt);
                String description = getCell(row, 2, fmt);
                LocalDateTime startTime = getExcelDateTime(row, 3);
                LocalDateTime endTime = getExcelDateTime(row, 4);
                String status = getCell(row, 5, fmt);
                String location = getCell(row, 6, fmt);
                LocalDateTime createdAt = getExcelDateTime(row, 7);
                LocalDateTime updatedAt = getExcelDateTime(row, 8);

                RecordsUp0128DAO dao = new RecordsUp0128DAO();
                dao.setUserId(Integer.valueOf(userId));
                dao.setTitle(title);
                dao.setDescription(description);
                dao.setStartTime(startTime);
                dao.setEndTime(endTime);
                dao.setStatus(status);
                dao.setLocation(location);
                dao.setCreatedAt(createdAt);
                dao.setUpdatedAt(updatedAt);
                RecordsUp0128Res recordsUp0128Res = new RecordsUp0128Res();
                BeanUtils.copyProperties(dao, recordsUp0128Res);
                recordsUp0128Res.setStartTime(startTime.format(formatter));
                recordsUp0128Res.setEndTime(endTime.format(formatter));
                recordsUp0128Res.setCreatedAt(createdAt.format(formatter));
                recordsUp0128Res.setUpdatedAt(updatedAt.format(formatter));
                res.add(recordsUp0128Res);
            }


        } catch (Exception e) {
            throw new RuntimeException("匯入失敗：" + e.getMessage(), e);
        }
        return res;
    }

    private LocalDateTime getExcelDateTime(Row row, int colIdx) {
        Cell cell = row.getCell(colIdx);
        if (cell == null) return null;

        DataFormatter df = new DataFormatter();

        // 內嵌：清理 Excel 可能吐出的怪字串（含 NBSP、多空白、以及 15:00:00 AM 這種矛盾格式）
        java.util.function.Function<String, String> clean = (String s) -> {
            if (s == null) return null;
            s = s.replace('\u00A0', ' ');
            s = s.trim().replaceAll("\\s+", " ");
            // 24小時制卻帶 AM/PM -> 直接移除 AM/PM，讓它用 24 小時格式解析
            s = s.replaceAll("\\s*(AM|PM)\\b", "");
            return s;
        };

        // 先處理公式欄位：看「公式算出來的型別」
        if (cell.getCellType() == CellType.FORMULA) {
            CellType rt = cell.getCachedFormulaResultType();

            // 公式結果是數字：可能是 Excel 日期序號/日期
            if (rt == CellType.NUMERIC) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date d = cell.getDateCellValue();
                    return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                }
                double v = cell.getNumericCellValue();
                Date d = DateUtil.getJavaDate(v);
                return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            }

            // 公式結果是字串
            if (rt == CellType.STRING) {
                String s = clean.apply(cell.getStringCellValue());
                return parseExcelDateTime(s);
            }

            // 其他：用 formatter 保底
            String s = clean.apply(df.formatCellValue(cell));
            return parseExcelDateTime(s);
        }

        // 非公式：數字日期
        if (cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                Date d = cell.getDateCellValue();
                return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            }
            // 保底：像日期序號的數字就嘗試轉（可留可刪）
            double v = cell.getNumericCellValue();
            if (v > 20000 && v < 80000) {
                Date d = DateUtil.getJavaDate(v);
                return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            }
        }

        // 字串或其他：交給 parse（先清理）
        String s = clean.apply(df.formatCellValue(cell));
        return parseExcelDateTime(s);
    }

    private LocalDateTime parseExcelDateTime(String s) {
        if (s == null) return null;

        // 1) 清理空白（含多個空白/Tab/不換行空白）
        s = s.replace('\u00A0', ' ');      // NBSP
        s = s.trim().replaceAll("\\s+", " ");
        if (s.isEmpty()) return null;

        // 2) 容錯：把 " 115:00:00 AM" 視為 " 1:15:00 AM"
        s = s.replaceAll("(\\s)(\\d)(\\d{2}):(\\d{2}:\\d{2})\\s*(AM|PM)$", "$1$2:$3:$4 $5");

        // 3) 修正矛盾格式：24小時(>=13) 卻帶 AM/PM，例如 "2026/1/29 15:00:00 AM"
        if (s.matches(".*\\b(AM|PM)\\b.*")) {
            java.util.regex.Matcher m = java.util.regex.Pattern
                    .compile("\\b(\\d{1,2}):(\\d{2}):(\\d{2})\\b")
                    .matcher(s);
            if (m.find()) {
                int hour = Integer.parseInt(m.group(1));
                if (hour >= 13) {
                    s = s.replaceAll("\\s*(AM|PM)\\b", "");
                }
            }
        }

        List<DateTimeFormatter> formats = Arrays.asList(
                // 24 小時制
                DateTimeFormatter.ofPattern("yyyy/M/d HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy/M/d HH:mm"),
                DateTimeFormatter.ofPattern("yyyy/M/d"),

                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"),
                DateTimeFormatter.ofPattern("M/d/yy H:mm"),
                DateTimeFormatter.ofPattern("M/d/yy HH:mm"),
                DateTimeFormatter.ofPattern("M/d/yyyy H:mm"),
                DateTimeFormatter.ofPattern("M/d/yyyy HH:mm"),

                // 12 小時制 + AM/PM
                DateTimeFormatter.ofPattern("yyyy/M/d h:mm:ss a", Locale.US),
                DateTimeFormatter.ofPattern("yyyy/M/d h:mm a", Locale.US),
                DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a", Locale.US),
                DateTimeFormatter.ofPattern("M/d/yyyy h:mm a", Locale.US),
                DateTimeFormatter.ofPattern("M/d/yy h:mm:ss a", Locale.US),
                DateTimeFormatter.ofPattern("M/d/yy h:mm a", Locale.US),

                // 沒空白的 AM/PM
                DateTimeFormatter.ofPattern("yyyy/M/d h:mm:ssa", Locale.US),
                DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ssa", Locale.US),
                DateTimeFormatter.ofPattern("M/d/yy h:mm:ssa", Locale.US)
        );

        for (DateTimeFormatter f : formats) {
            try {
                // 有些 pattern 是只有日期，這裡會 parse 失敗是正常的（會被 catch 掉）
                return LocalDateTime.parse(s, f);
            } catch (DateTimeParseException ignore) {
            }
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


    //新增excel檔案
    public int insert(List<RecordsUp0128Req> req) {

        int rows = 0;
        List<Records> daoList = new ArrayList<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        for (RecordsUp0128Req s : req) {
            Records dao = Records.builder()
                    .userId(Integer.valueOf(s.getUserId()))
                    .title(s.getTitle())
                    .description(s.getDescription())
                    .startTime(LocalDateTime.parse(s.getStartTime(), fmt))
                    .endTime(LocalDateTime.parse(s.getEndTime(), fmt))
                    .status(s.getStatus())
                    .location(s.getLocation())
                    .createdAt(LocalDateTime.parse(s.getCreatedAt(), fmt))
                    .updatedAt(LocalDateTime.parse(s.getUpdatedAt(), fmt))
                    .build();
            daoList.add(dao);
            rows++;
        }
        if (!daoList.isEmpty()) {
            records0128Repository.insert(daoList);
        }
        return rows; // 成功幾筆
    }



    @Autowired
    private EventRegistration0123Repository e;

    //查詢
    public List<ErTest123Res> testQuery() {
        List<ErTest123DAO> erTest123DAOS = e.testQuery();
        List<ErTest123Res> res = new ArrayList<>();

        for (ErTest123DAO s : erTest123DAOS) {
            ErTest123Res erTest123Res = new ErTest123Res();
            BeanUtils.copyProperties(s, erTest123Res);
            res.add(erTest123Res);
        }
        return res;
    }

}
