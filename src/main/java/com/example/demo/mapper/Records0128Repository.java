package com.example.demo.mapper;

import com.example.demo.dao.RecordsInt0128DAO;
import com.example.demo.dao.RecordsQuery0128DAO;
import com.example.demo.dao.RecordsUpd0128DAO;
import com.example.demo.entity.Records;
import com.example.demo.req.RecordsQuery0128Req;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Records0128Repository {

    @PersistenceContext
    private EntityManager em;
    // 注入 EntityManager，用來建立 Criteria 查詢並執行

    public List<RecordsInt0128DAO> init() {
        // 查詢方法：依評估日期 + 公司代號查詢歷程資料，回傳 DTO List

        CriteriaBuilder cb = em.getCriteriaBuilder();
        // 取得 CriteriaBuilder，用來建立條件（WHERE、ORDER BY 等）

        CriteriaQuery<RecordsInt0128DAO> cq = cb.createQuery(RecordsInt0128DAO.class);
        // 建立查詢，指定回傳型別為 ApplyMainHisJpaDAO（不是 Entity）

        Root<Records> m = cq.from(Records.class);
        // FROM ApplyMainHisJpa m（主表）


        cq.select(cb.construct(
                RecordsInt0128DAO.class,
                // 指定用建構式方式建立 DAO（等同 SELECT new xxx(...)）
                m.get("description"),
                m.get("status"),
                m.get("location")

        ));

        return em.createQuery(cq).getResultList();
    }

    //查詢按鈕
    public List<RecordsQuery0128DAO> query(RecordsQuery0128Req req) {
        // 查詢方法：依評估日期 + 公司代號查詢歷程資料，回傳 DTO List

        CriteriaBuilder cb = em.getCriteriaBuilder();
        // 取得 CriteriaBuilder，用來建立條件（WHERE、ORDER BY 等）

        CriteriaQuery<RecordsQuery0128DAO> cq = cb.createQuery(RecordsQuery0128DAO.class);
        // 建立查詢，指定回傳型別為 ApplyMainHisJpaDAO（不是 Entity）

        Root<Records> m = cq.from(Records.class);
        // FROM ApplyMainHisJpa m（主表）


        cq.select(cb.construct(
                RecordsQuery0128DAO.class,
                // 指定用建構式方式建立 DAO（等同 SELECT new xxx(...)）
                m.get("id"),
                m.get("userId"),
                m.get("title"),
                m.get("description"),
                m.get("startTime"),
                m.get("endTime"),
                m.get("status"),
                m.get("location"),
                m.get("createdAt"),
                m.get("updatedAt")

        ));
        List<Predicate> predicates = new ArrayList<>();

// title LIKE
        if (req.getTitle() != null && !req.getTitle().isEmpty()) {
            predicates.add(cb.like(
                    cb.lower(m.get("title")),
                    "%" + req.getTitle().trim().toLowerCase() + "%"
            ));
        }


        // description LIKE
        if (req.getDescription() != null && !req.getDescription().isEmpty()) {
            predicates.add(cb.like(
                    cb.lower(m.get("description")),
                    "%" + req.getDescription().trim().toLowerCase() + "%"
            ));
        }

        // status =
        if (req.getStatus() != null && !req.getStatus().isEmpty()) {
            predicates.add(cb.equal(
                    m.get("status"),
                    Integer.parseInt(req.getStatus().trim())
            ));
        }

        // location IN (...)
        if (req.getLocation() != null && !req.getLocation().isEmpty()) {
            predicates.add(m.get("location").in(req.getLocation()));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }
        return em.createQuery(cq).getResultList();
    }


    //修改
    @Transactional
    public int update(RecordsUpd0128DAO dao) {


        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaUpdate<Records> cu =
                cb.createCriteriaUpdate(Records.class);
        Root<Records> root = cu.from(Records.class);

        if (dao.getUserId() != null) {
            cu.set(root.get("userId"), dao.getUserId());
        }

        if (dao.getTitle() != null) {
            cu.set(root.get("title"), dao.getTitle());
        }

        if (dao.getDescription() != null) {
            cu.set(root.get("description"), dao.getDescription());
        }

        if (dao.getStartTime() != null) {
            cu.set(root.get("startTime"), dao.getStartTime());
        }

        if (dao.getEndTime() != null) {
            cu.set(root.get("endTime"), dao.getEndTime());
        }

        if (dao.getStatus() != null) {
            cu.set(root.get("status"), dao.getStatus());
        }

        if (dao.getLocation() != null && !dao.getLocation().isEmpty()) {
            cu.set(root.get("location"), dao.getLocation());
        }

        if (dao.getCreatedAt() != null) {
            cu.set(root.get("createdAt"), dao.getCreatedAt());
        }

        if (dao.getUpdatedAt() != null) {
            cu.set(root.get("updatedAt"), dao.getUpdatedAt());
        }


        cu.where(cb.equal(root.get("id"), dao.getId()));

        return em.createQuery(cu).executeUpdate();
    }


    //新增
    @Transactional
    public int insert(List<Records> list) {
        int rows = 0;
        for (Records dao : list) {
            try {
                em.persist(dao);
                em.flush();   // 強制送 SQL，有錯這裡一定會丟
                rows++;     // 成功
            } catch (Exception e) {
                return 0;     // 失敗
            }
        }
        return rows;
    }

}




