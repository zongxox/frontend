package com.example.demo.servicec;

import com.example.demo.dao.FruitInit0120DAO;
import com.example.demo.dao.FruitQuery0120DAO;
import com.example.demo.dao.FruitUpdQuery0120DAO;
import com.example.demo.mapper.FruitRepository;
import com.example.demo.req.*;
import com.example.demo.res.FruitInit0120Res;
import com.example.demo.res.FruitQuery0120Res;
import com.example.demo.res.FruitUpdQuery0120Res;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class Fruit0120Service {
    @Autowired
    private FruitRepository fruitRepository;



    //頁面初始化查詢
    public List<FruitInit0120Res> initSelect() {
        List<FruitInit0120DAO> fruitInit0120DAO = fruitRepository.initSelect();
        List<FruitInit0120Res> fruitInit0120Res = new ArrayList<>();
        for (FruitInit0120DAO dao : fruitInit0120DAO) {
            FruitInit0120Res res = new FruitInit0120Res();
            BeanUtils.copyProperties(dao, res);
            fruitInit0120Res.add(res);
        }

        return fruitInit0120Res;
    }


    //查詢按鈕
    public List<FruitQuery0120Res> query(FruitQuery0120Req req) {
        List<FruitQuery0120DAO> dao = fruitRepository.query(req);
        List<FruitQuery0120Res> res = new ArrayList<>();
        for (FruitQuery0120DAO dao1 : dao) {
            FruitQuery0120Res fruitQuery0120Res = new FruitQuery0120Res();
            BeanUtils.copyProperties(dao1, fruitQuery0120Res);
            res.add(fruitQuery0120Res);
        }
        return res;
    }

    //刪除
    public int delete(FruitDel0120Req req) {
        return fruitRepository.deleteById(req);
    }

    //註冊按鈕
    public int insert(FruitIns0120Req req){
        int rows = fruitRepository.insert(req);
        return rows;
    }

    //跳轉修改先查詢
    public FruitUpdQuery0120Res updQuery(FruitUpdQuery0120Req req){
        FruitUpdQuery0120DAO dao = fruitRepository.updQuery(req);
        FruitUpdQuery0120Res res = new FruitUpdQuery0120Res();
        BeanUtils.copyProperties(dao, res);
        return res;
    }

    //修改
    public int update(FruitUpd0120Req req){
        int rows = fruitRepository.update(req);
        return rows;
    }



    public ResponseEntity<Resource> download(Long id) {
        try {
            // 1) 從資料庫查「資料夾路徑(dirPath) + 檔名(fileName)」
            //    你 repository 的 queryFileInfo(id) 會回傳 Map: {dirPath:xxx, fileName:yyy}
            Map<String, String> info = fruitRepository.queryFileInfo(id);

            // 2) 如果查不到這筆 id 對應的檔案資訊，回 404（Not Found）
            if (info == null) return ResponseEntity.notFound().build();

            // 3) 取出資料夾路徑（例如 C:\Users\...\Desktop）
            String dirPath = info.get("dirPath");

            // 4) 取出檔案名稱（例如 qwe.zip）
            String fileName = info.get("fileName");

            // 5) 用 dirPath + fileName 組成完整檔案路徑
            //    例如：Paths.get("C:\\Users\\..\\Desktop", "qwe.zip")
            //    會得到：C:\Users\..\Desktop\qwe.zip
            Path path = Paths.get(dirPath, fileName);

            // 6) 印出實際組出來的完整路徑，方便除錯用
            System.out.println("download path=" + path.toAbsolutePath());

            // 7) 檢查檔案是否存在 + 不能是資料夾
            //    - Files.exists(path) 代表此路徑存在
            //    - Files.isDirectory(path) 代表這是資料夾（不是檔案）
            //    若不存在或是資料夾，就回 404
            if (!Files.exists(path) || Files.isDirectory(path)) {
                return ResponseEntity.notFound().build();
            }

            // 8) 建立 Resource（Spring 用 Resource 來「串流讀取檔案」回傳給瀏覽器）
            //    FileSystemResource 代表從本機檔案系統拿檔案
            Resource resource = new org.springframework.core.io.FileSystemResource(path.toFile());

            // 9) 設定下載用的 HTTP Header：Content-Disposition
            //    attachment 表示「要下載」，filename 指定下載時預設顯示的檔名
            String contentDisposition = "attachment; filename=\"" + fileName + "\"";

            // 10) 預設 content-type（MIME type）用 application/octet-stream
            //     這代表「二進位檔案」，瀏覽器通常會用下載方式處理
            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

            // 11) 嘗試用作業系統去猜這個檔案的 content-type（例如 application/zip）
            //     probeContentType 可能會失敗或回 null，所以包 try/catch
            try {
                String ct = Files.probeContentType(path); // 例如回 "application/zip" 或 null
                if (ct != null) mediaType = MediaType.parseMediaType(ct); // 把字串轉成 MediaType
            } catch (Exception ignore) {
                // 12) 如果猜不到或失敗，就忽略，繼續使用預設的 application/octet-stream
            }

            // 13) 建立 ResponseEntity 的回應「框架」
            //     ResponseEntity.ok() 代表 HTTP 200
            //     header 加上 Content-Disposition 讓瀏覽器用下載方式處理
            //     contentType 設定回應的 MIME type
            ResponseEntity.BodyBuilder builder = ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .contentType(mediaType);

            // 14) 嘗試設定 Content-Length（檔案大小）
            //     有些瀏覽器/下載工具會用它顯示進度或做更穩定的下載
            //     Files.size(path) 可能丟 IOException，所以也包 try/catch
            try {
                builder.contentLength(Files.size(path));
            } catch (Exception ignore) {
                // 15) 如果取不到 size，就不設定 Content-Length，也可以正常下載
            }

            // 16) 把檔案 resource 放到 response body
            //     Spring 會幫你把檔案內容「串流」寫到 HTTP 回應中（不是讀成 byte[]）
            return builder.body(resource);

        } catch (Exception e) {
            // 17) 任何未預期例外都印出堆疊，方便除錯
            e.printStackTrace();

            // 18) 回傳 HTTP 500（Internal Server Error）
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





}
