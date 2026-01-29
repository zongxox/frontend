package com.example.demo.mapper;

import com.example.demo.dao.*;
import com.example.demo.entity.FruitJpa;
import com.example.demo.req.FruitQuery0129Req;
import com.example.demo.req.FruitUpdQuery0129Req;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Fruit0129Repository {

    @PersistenceContext
    private EntityManager em;
    // 注入 EntityManager，用來建立 Criteria 查詢並執行


    //初始化查詢
    public List<FruitInit0129DAO> init() {
        // 查詢方法：依評估日期 + 公司代號查詢歷程資料，回傳 DTO List

        CriteriaBuilder cb = em.getCriteriaBuilder();
        // 取得 CriteriaBuilder，用來建立條件（WHERE、ORDER BY 等）

        CriteriaQuery<FruitInit0129DAO> cq = cb.createQuery(FruitInit0129DAO.class);
        // 建立查詢，指定回傳型別為 ApplyMainHisJpaDAO（不是 Entity）

        Root<FruitJpa> m = cq.from(FruitJpa.class);
        // FROM ApplyMainHisJpa m（主表）


        cq.select(cb.construct(
                FruitInit0129DAO.class,
                // 指定用建構式方式建立 DAO（等同 SELECT new xxx(...)）
                m.get("fruitType"),
                m.get("price"),
                m.get("origin")

        ));

        return em.createQuery(cq).getResultList();
    }

    //查詢按鈕
    public List<FruitQuery0129DAO> query(FruitQuery0129Req req) {
        // 查詢方法：依評估日期 + 公司代號查詢歷程資料，回傳 DTO List

        CriteriaBuilder cb = em.getCriteriaBuilder();
        // 取得 CriteriaBuilder，用來建立條件（WHERE、ORDER BY 等）

        CriteriaQuery<FruitQuery0129DAO> cq = cb.createQuery(FruitQuery0129DAO.class);
        // 建立查詢，指定回傳型別為 ApplyMainHisJpaDAO（不是 Entity）

        Root<FruitJpa> m = cq.from(FruitJpa.class);
        // FROM ApplyMainHisJpa m（主表）


        cq.select(cb.construct(
                FruitQuery0129DAO.class,
                // 指定用建構式方式建立 DAO（等同 SELECT new xxx(...)）
                m.get("id"),
                m.get("fruitName"),
                m.get("fruitCode"),
                m.get("fruitType"),
                m.get("price"),
                m.get("quantity"),
                m.get("origin"),
                m.get("remark"),
                m.get("creUser"),
                m.get("creDate"),
                m.get("updUser"),
                m.get("updDate")

        ));
        List<Predicate> predicates = new ArrayList<>();


        if (req.getFruitName() != null && !req.getFruitName().isEmpty()) {
            predicates.add(cb.like(
                    cb.lower(m.get("fruitName")),
                    "%" + req.getFruitName().trim().toLowerCase() + "%"
            ));
        }


        if (req.getPrice() != null) {
            predicates.add(cb.equal(m.get("price"), req.getPrice()));
        }


        if (req.getOrigin() != null && !req.getOrigin().isEmpty()) {
            predicates.add(cb.like(
                    cb.lower(m.get("origin")),
                    "%" + req.getOrigin().trim().toLowerCase() + "%"
            ));
        }

        // location IN (...)
        if (req.getFruitType() != null && !req.getFruitType().isEmpty()) {
            predicates.add(m.get("fruitType").in(req.getFruitType()));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }
        return em.createQuery(cq).getResultList();
    }

    //刪除
    @Transactional
    public int deleteById(FruitDel0129DAO dao) {
        int rows = 0; // 影響筆數
        FruitJpa entity = em.find(FruitJpa.class, dao.getId());
        if (entity == null) {
            return rows; // 0：找不到資料
        }
        em.remove(entity);
        rows = 1; // 成功刪除一筆
        return rows;
    }



    //新增
    @Transactional
    public int insert(FruitJpa fruitJpa) {
        em.persist(fruitJpa);
        return 1;
    }

    //跳轉修改畫面id查詢
    public FruitUpdQuery0129DAO updQuery(FruitUpdQuery0129Req req) {
        // 查詢方法：依評估日期 + 公司代號查詢歷程資料，回傳 DTO List

        CriteriaBuilder cb = em.getCriteriaBuilder();
        // 取得 CriteriaBuilder，用來建立條件（WHERE、ORDER BY 等）

        CriteriaQuery<FruitUpdQuery0129DAO> cq = cb.createQuery(FruitUpdQuery0129DAO.class);
        // 建立查詢，指定回傳型別為 ApplyMainHisJpaDAO（不是 Entity）

        Root<FruitJpa> m = cq.from(FruitJpa.class);
        // FROM ApplyMainHisJpa m（主表）


        cq.select(cb.construct(
                FruitUpdQuery0129DAO.class,
                // 指定用建構式方式建立 DAO（等同 SELECT new xxx(...)）
                m.get("fruitName"),
                m.get("fruitCode"),
                m.get("fruitType"),
                m.get("price"),
                m.get("quantity"),
                m.get("origin"),
                m.get("remark"),
                m.get("creUser"),
                m.get("creDate"),
                m.get("updUser"),
                m.get("updDate")

        ));
        cq.where(cb.equal(m.get("id"), Long.valueOf(req.getId())));

        return em.createQuery(cq).getSingleResult();
    }



    //修改
    @Transactional
    public int update(FruitUpd0129DAO  dao) {

        // 取得 CriteriaBuilder，用來建立 CriteriaUpdate 與更新條件
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // 建立一個針對 ZauthCompanyJpa Entity 的 CriteriaUpdate（UPDATE 語法）
        CriteriaUpdate<FruitJpa> cu =
                cb.createCriteriaUpdate(FruitJpa.class);

        // 指定要更新的 Entity（UPDATE ZauthCompanyJpa）
        Root<FruitJpa> root = cu.from(FruitJpa.class);


        cu.set(root.get("fruitName"), dao.getFruitName());
        cu.set(root.get("fruitCode"), dao.getFruitCode());
        cu.set(root.get("fruitType"), dao.getFruitType());
        cu.set(root.get("price"), dao.getPrice());
        cu.set(root.get("quantity"), dao.getQuantity());
        cu.set(root.get("origin"), dao.getOrigin());
        cu.set(root.get("remark"), dao.getRemark());
        cu.set(root.get("creUser"), dao.getCreUser());
        cu.set(root.get("creDate"), dao.getCreDate());
        cu.set(root.get("updUser"), dao.getUpdUser());
        cu.set(root.get("updDate"), dao.getUpdDate());


        cu.where(cb.equal(root.get("id"), dao.getId()));


        return em.createQuery(cu).executeUpdate();
    }


}




