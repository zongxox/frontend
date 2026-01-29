package com.example.demo.servicec;

import com.example.demo.dao.*;
import com.example.demo.mapper.OrderItem0126Repository;
import com.example.demo.req.OrderItem0126DelReq;
import com.example.demo.req.OrderItem0126InsReq;
import com.example.demo.req.OrderItem0126QueryReq;
import com.example.demo.req.OrderItemUpd0126Req;
import com.example.demo.res.OrderItem0126QueryRes;
import com.example.demo.res.OrderItemInit0126Res;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OrderItem0126Service {
    @Autowired
    private OrderItem0126Repository orderItem0126Repository;

    public List<OrderItemInit0126Res> init() {
        List<OrderItemInit0126DAO> init = orderItem0126Repository.init();
        List<OrderItemInit0126Res> res = new ArrayList<>();
        for (OrderItemInit0126DAO i : init) {
            OrderItemInit0126Res r = new OrderItemInit0126Res();
            BeanUtils.copyProperties(i, r);
            res.add(r);
        }
        return res;
    }

    public List<OrderItemInit0126Res> init1() {
        List<OrderItemInit0126DAO> init = orderItem0126Repository.init1();
        List<OrderItemInit0126Res> res = new ArrayList<>();
        for (OrderItemInit0126DAO i : init) {
            OrderItemInit0126Res r = new OrderItemInit0126Res();
            BeanUtils.copyProperties(i, r);
            res.add(r);
        }
        return res;
    }

    public List<OrderItemInit0126Res> init2() {
        List<OrderItemInit0126DAO> init = orderItem0126Repository.init2();
        List<OrderItemInit0126Res> res = new ArrayList<>();
        for (OrderItemInit0126DAO i : init) {
            OrderItemInit0126Res r = new OrderItemInit0126Res();
            BeanUtils.copyProperties(i, r);
            res.add(r);
        }
        return res;
    }

    //查詢按鈕
    public List<OrderItem0126QueryRes> query(OrderItem0126QueryReq req) {
        List<OrderItem0126QueryDAO> query = orderItem0126Repository.query(req);
        List<OrderItem0126QueryRes> res = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");

        for (OrderItem0126QueryDAO s : query) {
            OrderItem0126QueryRes orderItem0126QueryRes = new OrderItem0126QueryRes();
            BeanUtils.copyProperties(s, orderItem0126QueryRes);

            if (orderItem0126QueryRes.getCreatedAt() != null) {
                orderItem0126QueryRes.setCreatedAtStr(orderItem0126QueryRes.getCreatedAt().format(formatter));
            }

            if (orderItem0126QueryRes.getUpdatedAt() != null) {
                orderItem0126QueryRes.setUpdatedAtStr(orderItem0126QueryRes.getUpdatedAt().format(formatter2));
            }

            res.add(orderItem0126QueryRes);
        }

        return res;
    }

    //刪除
    public int del(OrderItem0126DelReq req) {
        OrderItem0126DelDAO dao = new OrderItem0126DelDAO();
        BeanUtils.copyProperties(req, dao);
        int rows = orderItem0126Repository.del(dao);
        return rows;
    }


    //新增
    public int insert(OrderItem0126InsReq req) {
        OrderItem0126InsDAO dao = new OrderItem0126InsDAO();
        BeanUtils.copyProperties(req, dao);
        int rows = orderItem0126Repository.insert(dao);
        return rows;
    }

    //修改
    public int update(OrderItemUpd0126Req req) {
        OrderItemUpd0126DAO dao = new OrderItemUpd0126DAO();
        BeanUtils.copyProperties(req, dao);
        int rows = orderItem0126Repository.update(dao);
        return rows;
    }


    // POI 上傳：把 Excel 裡的 ORDER_ITEM 匯入資料庫
    public void importOrderItemExcel(MultipartFile file) throws Exception {

        // 1)  用 try-with-resources 開啟 Excel 檔案
        //    XSSFWorkbook 專門讀 .xlsx
        //    會自動關閉 workbook（避免記憶體/檔案資源外洩）
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

            // 2)  取得第 0 張工作表（第一張 sheet）
            Sheet sheet = workbook.getSheetAt(0);

            // 3) 從第 1 列開始讀，因為第 0 列通常是表頭
            //    sheet.getLastRowNum() 代表最後一列的 index
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                // 4)  取得當前列
                Row row = sheet.getRow(i);

                // 5)  如果該列是空的，就跳過
                if (row == null) continue;

                // =========================
                //  讀 Excel 各欄位（0~8）
                // =========================

                // 6)  讀取 ORDER_ID（第 0 欄）
                String orderIdStr = getCell(row, 0);

                // 7)  讀取 ITEM_ID（第 1 欄）
                String itemIdStr = getCell(row, 1);

                // 8)  讀取 PRODUCT_NAME（第 2 欄）
                String productName = getCell(row, 2);

                // 9)  讀取 QUANTITY（第 3 欄）
                String quantity = getCell(row, 3);

                // 10)  讀取 UNIT_PRICE（第 4 欄）
                String unitPriceStr = getCell(row, 4);

                // 11)  讀取 DISCOUNT（第 5 欄）
                String discountStr = getCell(row, 5);

                // 12)  讀取 STATUS（第 6 欄）
                String status = getCell(row, 6);

                // 13)  讀取 CREATED_AT（第 7 欄）→ 轉 LocalDateTime
                LocalDateTime createdAt = getDateTime(row, 7);

                // 14)  讀取 UPDATED_AT（第 8 欄）→ 轉 LocalDateTime
                LocalDateTime updatedAt = getDateTime(row, 8);

                // =========================
                //  把字串轉成數字型別
                // =========================

                // 15)  Excel 讀出來是 String，所以要轉成 Long（ORDER_ID）
                Long orderId = parseLong(orderIdStr);

                // 16)  Excel 讀出來是 String，所以要轉成 Long（ITEM_ID）
                Long itemId = parseLong(itemIdStr);

                // 17)  Excel 讀出來是 String，所以要轉成 Integer（UNIT_PRICE）
                Integer unitPrice = parseInt(unitPriceStr);

                // 18)  Excel 讀出來是 String，所以要轉成 Integer（DISCOUNT）
                Integer discount = parseInt(discountStr);

                // =========================
                //  組 DAO（準備存進資料庫）
                // =========================

                // 19)  用 builder 組出一筆 OrderItemUpd0126DAO
                //     這就是你要 insert 進資料庫的一筆資料
                OrderItemUpd0126DAO dao = OrderItemUpd0126DAO.builder()
                        .orderId(orderId)           // 20) 設定 orderId
                        .itemId(itemId)             // 21) 設定 itemId
                        .productName(productName)   // 22) 設定 productName
                        .quantity(quantity)         // 23) 設定 quantity
                        .unitPrice(unitPrice)       // 24) 設定 unitPrice
                        .discount(discount)         // 25) 設定 discount
                        .status(status)             // 26) 設定 status
                        .createdAt(createdAt)       // 27) 設定 createdAt
                        .updatedAt(updatedAt)       // 28) 設定 updatedAt
                        .build();                   // 29) 建立 DAO 物件

                // 30) 呼叫 Repository 寫入資料庫（INSERT）
                orderItem0126Repository.insert1(dao);
            }
        }
    }


    //  讀取 Excel 指定欄位，回傳字串
    private String getCell(Row row, int index) {

        // 1) 取得這一列的第 index 格
        Cell cell = row.getCell(index);

        // 2) 如果 cell 是空，回傳空字串
        // 3) 不空的話，用 cell.toString() 轉字串，再 trim() 去掉前後空白
        return cell == null ? "" : cell.toString().trim();
    }


    // 判斷字串是否為空（null 或空白）
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }


    //  把字串轉成 Long
    private Long parseLong(String s) {

        // 1) 去掉空白
        s = s.trim();

        // 2) Excel 很常把整數變成 "123.0"
        //    所以如果尾巴是 .0，就把它切掉
        if (s.endsWith(".0")) s = s.substring(0, s.length() - 2);

        // 3) 轉成 Long
        return Long.parseLong(s);
    }


    //  把字串轉成 Integer
    private Integer parseInt(String s) {

        // 1) 去掉空白
        s = s.trim();

        // 2) Excel 很常把整數變成 "500.0"
        //    所以如果尾巴是 .0，就把它切掉
        if (s.endsWith(".0")) s = s.substring(0, s.length() - 2);

        // 3) 轉成 Integer
        return Integer.parseInt(s);
    }


    //  取得 Excel 的日期時間欄位，回傳 LocalDateTime
    private LocalDateTime getDateTime(Row row, int index) {

        // 1) 取得 cell
        Cell cell = row.getCell(index);

        // 2) 如果 cell 是空，回傳 null
        if (cell == null) return null;

        // =========================
        //  情況 1：Excel 日期格（真正日期型別）
        // =========================
        // Excel 的日期通常是 NUMERIC，而且有日期格式
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {

            // 3) 取得 Date
            Date d = cell.getDateCellValue();

            // 4) Date → LocalDateTime
            return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }

        // =========================
        //  情況 2：當字串處理
        // =========================
        // 例如你遇到的 "26-一月-2026" 這種格式就會出事，所以最好不要用 toString parse
        String s = cell.toString().trim();

        // 5) 空字串就回傳 null
        if (s.isEmpty()) return null;

        // 6) 把 "2026-01-26 19:30:00" 轉成 "2026-01-26T19:30:00"
        //    因為 LocalDateTime.parse() 喜歡 ISO 格式（中間要 T）
        if (s.contains(" ")) s = s.replace(" ", "T");

        // 7) 若只有日期（沒有時間）例如 "2026-01-26"
        //    幫它補上 "T00:00:00"
        if (s.length() == 10) s = s + "T00:00:00";

        // 8) 最後轉成 LocalDateTime 回傳
        return LocalDateTime.parse(s);
    }



    // 下載 Excel：從資料庫撈 ORDER_ITEM → 產生 Excel → 直接回傳下載
    public void downloadExcel(HttpServletResponse response) throws Exception {

        // 1 從資料庫查出全部 ORDER_ITEM 資料（回傳 List<DAO>）
        List<OrderItemUpd0126DAO> list = orderItem0126Repository.findAll();

        // 2 設定下載檔名
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String fileName = URLEncoder.encode(dateStr + ".xlsx", StandardCharsets.UTF_8.name());

        // 3 告訴瀏覽器：我回傳的是 Excel 檔（xlsx 的 MIME type）
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // 4 告訴瀏覽器：這是附件下載（不是直接顯示在畫面）
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        // 5 建立一個新的 Excel 檔案（XSSFWorkbook = .xlsx 格式）
        try (Workbook workbook = new XSSFWorkbook()) {

            // 6 建立一個工作表 Sheet，名字叫 ORDER_ITEM
            Sheet sheet = workbook.createSheet("ORDER_ITEM");

            // 日期格式設定（讓 Excel 顯示 "yyyy-MM-dd HH:mm:ss"）
            // 7 建立儲存格的樣式物件（CellStyle）
            CellStyle dateStyle = workbook.createCellStyle();

            // 8 CreationHelper 幫你做 DataFormat（Excel 格式用）
            CreationHelper helper = workbook.getCreationHelper();

            // 9 設定日期格式（寫入 Excel 的 Date 會用這個格式顯示）
            dateStyle.setDataFormat(helper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));


            // 表頭（第一列：欄位名稱）
            // 10 建立第 0 列當作表頭
            Row header = sheet.createRow(0);

            // 11 Excel 第一列要顯示的標題
            String[] cols = {
                    "ID", "ORDER_ID", "ITEM_ID", "PRODUCT_NAME", "QUANTITY",
                    "UNIT_PRICE", "DISCOUNT", "STATUS", "CREATED_AT", "UPDATED_AT"
            };



            // 12  把欄位名稱一格一格寫進第 0 列（表頭列）
            for (int i = 0; i < cols.length; i++) {
                header.createCell(i).setCellValue(cols[i]);
            }


            // 資料列（從第 1 列開始寫入）
            // 13 rowIndex = 1，因為第 0 列已經是表頭了
            int rowIndex = 1;

            // 14 迴圈把資料庫每一筆 dao 寫到 Excel 裡
            for (OrderItemUpd0126DAO dao : list) {

                // 15 建立一列 Row，例如第 1 列、第 2 列...
                Row row = sheet.createRow(rowIndex++);

                // 16 依序把欄位寫進每一格 cell（數字/字串）
                row.createCell(0).setCellValue(dao.getId() == null ? 0 : dao.getId());
                row.createCell(1).setCellValue(dao.getOrderId() == null ? 0 : dao.getOrderId());
                row.createCell(2).setCellValue(dao.getItemId() == null ? 0 : dao.getItemId());

                // 17 字串欄位用 nvl() 避免 null → 變成空字串 ""
                row.createCell(3).setCellValue(nvl(dao.getProductName()));
                row.createCell(4).setCellValue(nvl(dao.getQuantity()));

                // 18 數字欄位如果 null 就放 0
                row.createCell(5).setCellValue(dao.getUnitPrice() == null ? 0 : dao.getUnitPrice());
                row.createCell(6).setCellValue(dao.getDiscount() == null ? 0 : dao.getDiscount());

                // 19 STATUS 也用 nvl 避免 null
                row.createCell(7).setCellValue(nvl(dao.getStatus()));

                // 20 寫入 CREATED_AT（第 8 欄）& UPDATED_AT（第 9 欄）
                //     使用 writeDateCell() 讓時間變成 Excel 真正日期格（不是純文字）
                writeDateCell(row, 8, dao.getCreatedAt(), dateStyle);
                writeDateCell(row, 9, dao.getUpdatedAt(), dateStyle);
            }


            // 21 自動調整每一欄的寬度（依照內容長度）
            for (int i = 0; i < cols.length; i++) {
                sheet.autoSizeColumn(i);
            }

            //  寫出 Excel 給瀏覽器下載

            // 22 把 workbook 寫入 response 的輸出串流
            workbook.write(response.getOutputStream());

            // 23  強制把資料送出去（確保下載完整）
            response.getOutputStream().flush();
        }
    }


    //寫入 Excel 的「日期時間欄位」
// row：要寫進去的那一列
// colIndex：第幾欄（8=CREATED_AT、9=UPDATED_AT）
// ldt：LocalDateTime 時間
// dateStyle：日期格式樣式（yyyy-MM-dd HH:mm:ss）
    private void writeDateCell(Row row, int colIndex, LocalDateTime ldt, CellStyle dateStyle) {

        // 1)建立該欄位的 cell
        Cell cell = row.createCell(colIndex);

        // 2 如果時間是 null，就不寫（cell 會空白）
        if (ldt == null) return;

        // 3 LocalDateTime → Date（因為 Excel setCellValue 需要 Date）
        Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        // 4 寫入 Excel（變成真正日期格）
        cell.setCellValue(date);

        // 5 套用日期格式（顯示 yyyy-MM-dd HH:mm:ss）
        cell.setCellStyle(dateStyle);
    }


    //防止字串欄位是 null 時出錯（轉成空字串）
    private String nvl(String s) {
        return s == null ? "" : s;
    }

}


