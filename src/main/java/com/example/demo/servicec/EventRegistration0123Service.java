package com.example.demo.servicec;

import com.example.demo.dao.*;
import com.example.demo.mapper.EventRegistration0123Repository;
import com.example.demo.req.*;
import com.example.demo.res.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class EventRegistration0123Service {
    @Autowired
    private EventRegistration0123Repository eventRegistration0123Repository;

    public List<EventRegistrationInit0123Res> init(){
        List<EventRegistrationInit0123DAO> init = eventRegistration0123Repository.init();
        List<EventRegistrationInit0123Res> res = new ArrayList<>();
        for (EventRegistrationInit0123DAO s : init){
            EventRegistrationInit0123Res eventRegistrationInit0123Res = new EventRegistrationInit0123Res();
            BeanUtils.copyProperties(s,eventRegistrationInit0123Res);
            res.add(eventRegistrationInit0123Res);
        }
        return res;
    }

    public List<EventRegistrationInit10123Res> init1(){
        List<EventRegistrationInit0123DAO> init = eventRegistration0123Repository.init1();
        List<EventRegistrationInit10123Res> res = new ArrayList<>();
        for (EventRegistrationInit0123DAO s : init){
            EventRegistrationInit10123Res eventRegistrationInit10123Res = new EventRegistrationInit10123Res();
            BeanUtils.copyProperties(s,eventRegistrationInit10123Res);
            res.add(eventRegistrationInit10123Res);
        }
        return res;
    }


    public List<EventRegistrationInit20123Res> init2(){
        List<EventRegistrationInit0123DAO> init = eventRegistration0123Repository.init2();
        List<EventRegistrationInit20123Res> res = new ArrayList<>();
        for (EventRegistrationInit0123DAO s : init){
            EventRegistrationInit20123Res eventRegistrationInit20123Res = new EventRegistrationInit20123Res();
            BeanUtils.copyProperties(s,eventRegistrationInit20123Res);
            res.add(eventRegistrationInit20123Res);
        }
        return res;
    }


    //查詢按鈕
    public List<EventRegistrationQueryRes> query(EventRegistrationQuery0123Req req){
        List<EventRegistrationQueryDAO> dao = eventRegistration0123Repository.query(req);
        List<EventRegistrationQueryRes> eventRegistrationQueryRes = new ArrayList<>();

        for (EventRegistrationQueryDAO s : dao){
            EventRegistrationQueryRes res = new EventRegistrationQueryRes();
            BeanUtils.copyProperties(s, res);
            eventRegistrationQueryRes.add(res);
        }
        return eventRegistrationQueryRes;
    }


    //刪除
    public int delete(EventRegistrationDel0123Req req) {
        EventRegistrationDel0123DAO dao = new EventRegistrationDel0123DAO();
        BeanUtils.copyProperties(req, dao);
        return eventRegistration0123Repository.deleteById(dao);
    }


    //註冊按鈕
    public int insert(EventRegistrationIns0123Req req){
        EventRegistrationIns0123DAO dao = new EventRegistrationIns0123DAO();
        BeanUtils.copyProperties(req, dao);
        int rows = eventRegistration0123Repository.insert(dao);
        if (req.getMemberDetail() != null) {
            MemberDetail0123DAO detailDao = new MemberDetail0123DAO();
            BeanUtils.copyProperties(req.getMemberDetail(), detailDao);

            eventRegistration0123Repository.insert1(detailDao);
        }
        return rows;
    }



    //跳轉修改先查詢
    public EventRegistrationUpdQuery0123Res updQuery(EventRegistrationUpdQuery0123Req req){
        EventRegistrationUpdQuery0123DAO dao = eventRegistration0123Repository.updQuery(req);
        EventRegistrationUpdQuery0123Res res = new EventRegistrationUpdQuery0123Res();
        BeanUtils.copyProperties(dao, res);
        return res;
    }

    //修改
    public int update(EventRegistrationUpd0123Req req){
        EventRegistrationUpd0123DAO dao = new EventRegistrationUpd0123DAO();
        BeanUtils.copyProperties(req, dao);
        int rows = eventRegistration0123Repository.update(dao);
        if (req.getMemberDetail() != null) {
            MemberDetail0123DAO md = new MemberDetail0123DAO();
            BeanUtils.copyProperties(req.getMemberDetail(), md);
            dao.setMemberDetail(md);
        }
        return rows;
    }

    //下載
    public ResponseEntity<Resource> download(Long memberId) {
        try {
            // 1) 從資料庫查「資料夾路徑(dirPath) + 檔名(fileName)」
            //    你 repository 的 queryFileInfo(id) 會回傳 Map: {dirPath:xxx, fileName:yyy}
            EventRegistrationDon0123DAO info = eventRegistration0123Repository.queryFileInfo(memberId);

            // 2) 如果查不到這筆 id 對應的檔案資訊，回 404（Not Found）
            if (info == null) return ResponseEntity.notFound().build();

            // 3) 取出資料夾路徑（例如 C:\Users\...\Desktop）
            String dirPath = info.getEmail();

            // 4) 取出檔案名稱（例如 qwe.zip）
            String fileName = info.getPhone();

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
            //String contentDisposition = "attachment; filename=\"" + fileName + "\"";

            //下載顯示檔名：日期時間 + 副檔名
            String originalName = path.getFileName().toString(); // 例如 qwe.pdf
            String ext = "";
            int dot = originalName.lastIndexOf(".");
            if (dot >= 0) ext = originalName.substring(dot); // 例如 ".pdf"

            String dateTimeName = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            String downloadName = dateTimeName + ext;


            //建立 Content-Disposition（支援中文檔名）
            ContentDisposition cd = ContentDisposition.attachment()
                    .filename(downloadName, StandardCharsets.UTF_8)
                    .build();

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
                    .header(HttpHeaders.CONTENT_DISPOSITION, cd.toString())
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

    // 上傳
    public void upload(Long memberId, MultipartFile file) {
        try {
            //取得 DB 裡的路徑 (email = dirPath)
            EventRegistrationDon0123DAO dao = eventRegistration0123Repository.queryFileInfo(memberId);
            if (dao == null) {
                throw new RuntimeException("找不到會員資料 memberId=" + memberId);
            }

            String dirPath = dao.getEmail(); //email 當路徑
            if (dirPath == null || dirPath.trim().isEmpty()) {
                throw new RuntimeException("路徑(email)不可為空");
            }

            // 避免反斜線跳脫問題，統一用 /
            dirPath = dirPath.replace("\\", "/");

            // 檢查檔案本身
            if (file == null || file.isEmpty()) {
                throw new RuntimeException("上傳檔案不可為空");
            }

            // 取得原始檔名 (用來拿副檔名)
            String originalName = file.getOriginalFilename(); // ex: 555.png
            if (originalName == null || originalName.trim().isEmpty()) {
                throw new RuntimeException("檔名不可為空");
            }

            // 4) 取得副檔名
            String ext = "";
            int dot = originalName.lastIndexOf(".");
            if (dot >= 0) {
                ext = originalName.substring(dot); // ex: .png
            }

            // 存檔名稱 (phone = saveName)
            String saveName = memberId + ext; // ex: 555.png

            // 確保資料夾存在（沒有就建立）
            Path dir = Paths.get(dirPath);
            Files.createDirectories(dir);

            // 寫入檔案
            Path savePath = dir.resolve(saveName);
            Files.copy(file.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);

            // 更新 DB：email=路徑，phone=檔案名稱
            eventRegistration0123Repository.updateFileInfo(memberId, dirPath, saveName);

        } catch (Exception e) {
            throw new RuntimeException("upload 失敗：" + e.getMessage(), e);
        }
    }



}
