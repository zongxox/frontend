package com.example.demo.CorsConfig;

import com.example.demo.res.UserQuery0127Res;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.List;

public class ExcelExportUtil {

    //設置Excel 欄位總數有幾個欄位要處理,後面用於設置調欄位寬度
    private static final int COL_COUNT = 15;

    //Excel日期顯示格式（這是 Excel 的格式，不是 Java格式）
    private static final String EXCEL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 依照前端畫面欄位成匯出 Excel
     * @param rows 查詢後、已整理好的資料
     * @return Excel 檔案的 byte[]
     */
    public static byte[] buildApplyDetailExcel(List<UserQuery0127Res> rows, boolean isXls) throws Exception {

        // 建立 Excel Workbook 與輸出用的 ByteArrayOutputStream
        // try-with-resources：結束後會自動關閉資源
        try (
                Workbook workbook = isXls ? new HSSFWorkbook() : new XSSFWorkbook();
                ByteArrayOutputStream bos = new ByteArrayOutputStream()
        ) {

            //1.建立一個excel下面工作表名稱，名稱為 分頁1
            Sheet sheet = workbook.createSheet("分頁1");

            //2.CreationHelper = 幫 Excel 處理「日期格式」用的工具
            CreationHelper helper = workbook.getCreationHelper();

            //建立一個「儲存格樣式」物件（要拿來套用到日期欄位）
            CellStyle dateStyle = workbook.createCellStyle();

            //設定這個樣式的「資料格式」＝ 日期時間格式
            //helper.createDataFormat().getFormat(...) 會把你給的格式字串
            //轉成 Excel 真正認得的格式代碼，讓 Excel 顯示成 yyyy-mm-dd hh:mm:ss
            //EXCEL_DATE_TIME_FORMAT上面定義的時間格式
            dateStyle.setDataFormat(helper.createDataFormat().getFormat(EXCEL_DATE_TIME_FORMAT));

            //設定文字（或日期顯示）在儲存格中「靠左對齊」
            dateStyle.setAlignment(HorizontalAlignment.LEFT);

            //設定內容在儲存格中「垂直置中」(上/中/下的那個中)
            dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // 一般文字靠左樣式
            CellStyle leftStyle = workbook.createCellStyle();
            leftStyle.setAlignment(HorizontalAlignment.LEFT);
            leftStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            //3.在分頁1裡，建立『標題那一列標題列，順序要跟資料一致
            Row header = sheet.createRow(0);
            int c = 0;//初始化由0開始

            //建立每一個標題欄位,leftStyle是上面設置的樣式
            createTextCell(header, c++, "用戶編號", leftStyle);//0開始 往下就自增1
            createTextCell(header, c++, "姓名", leftStyle);//12
            createTextCell(header, c++, "密碼", leftStyle);//1
            createTextCell(header, c++, "帳號", leftStyle);//2
            createTextCell(header, c++, "電話", leftStyle);//3
            createTextCell(header, c++, "EMAIL", leftStyle);//4
            createTextCell(header, c++, "住址", leftStyle);//5
            createTextCell(header, c++, "郵遞區號", leftStyle);//6
            createTextCell(header, c++, "創建用戶", leftStyle);//7
            createTextCell(header, c++, "創建時間", leftStyle);//8
            createTextCell(header, c++, "更新用戶", leftStyle);//11
            createTextCell(header, c++, "更新時間", leftStyle);//12


            //4.下面欄位,位子要跟上面標題欄位要跟一致
            int r = 1;//0是標題 1才是數據的第一筆

            // service 查詢完資料並轉成 AdRes，controller 取得結果後交由 ExcelExportUtil
            // 將每筆 AdRes 依欄位順序逐筆寫入 Excel
            for (UserQuery0127Res dto : rows) {
                Row row = sheet.createRow(r++);//在分頁1裡，建立 那一行(橫)欄位，順序要跟資料一致
                int col = 0; //第一行的第一個欄位開始

                // 1 系統流水編號
                createTextCell(row, col++, n(dto.getId()), leftStyle);//0

                // 2 評估日期（目前當字串處理）
                createTextCell(row, col++, n(dto.getName()), leftStyle);

                // 3 公司代號
                createTextCell(row, col++, n(dto.getPassword()), leftStyle);

                // 4 檔案類型
                createTextCell(row, col++, n(dto.getAccount()), leftStyle);

                // 5 上傳檔案存放路徑
                createTextCell(row, col++, n(dto.getPassword()), leftStyle);

                // 6 上傳檔案檔名
                createTextCell(row, col++, n(dto.getEmail()), leftStyle);

                // 7 上傳檔案檔名（原檔名）
                createTextCell(row, col++, n(dto.getAddress()), leftStyle);

                // 8 上傳檔案大小（KB）
                createTextCell(row, col++, n(dto.getZipcodes()), leftStyle);

                // 9 上傳檔案帳號
                createTextCell(row, col++, n(dto.getCreUser()), leftStyle);

                // 10 上傳檔案時間（Excel 真日期）
                createDateCell(row, col++, dto.getCreDate(), dateStyle);

                // 11 是否為申報檔或聲明書以外的檔案
                createTextCell(row, col++, n(dto.getUpdUser()), leftStyle);

                // 12 建立人員
                createDateCell(row, col++, dto.getUpdDate(), dateStyle);


            }

            //5.欄寬調整
            // 先 autoSize，再額外加寬，避免 Excel 出現 ####
            for (int i = 0; i < COL_COUNT; i++) {
                sheet.autoSizeColumn(i);
                int w = sheet.getColumnWidth(i);
                sheet.setColumnWidth(i, Math.min(w + 2000, 255 * 256));
            }

            // 將 Excel 寫入輸出串流
            workbook.write(bos);

            // 回傳 Excel 的 byte 陣列（給 Controller 下載用）
            return bos.toByteArray();
        }
    }

    // ===== helpers =====

    // 建立文字儲存格
    private static void createTextCell(Row row, int col, String value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    // 建立日期儲存格（LocalDateTime → Excel Date）
    private static void createDateCell(Row row, int col,
                                       java.time.LocalDateTime value,
                                       CellStyle style) {
        Cell cell = row.createCell(col);

        // 若日期不為 null，轉成 Timestamp
        if (value != null) {
            cell.setCellValue(Timestamp.valueOf(value));
        } else {
            // null 則填空字串
            cell.setCellValue("");
        }

        cell.setCellStyle(style);
    }

    /**
     * null 轉成空字串
     * 避免 Apache POI 因 null 值拋出 NPE
     */
    private static String n(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}