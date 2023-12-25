package com.demo.domain.stamping;

import com.demo.model.PLCSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface StampingRepository extends JpaRepository<Stamping, Long>, JpaSpecificationExecutor<Stamping> {

    @Query(value = "from Stamping where barcode = :#{#barcode} and productTypeId = :#{#productTypeId}")
    List<Stamping> getDataByBarcode(String barcode, short productTypeId);

    @Query(value = "delete from Stamping as d where d.productTypeId = :#{#productTypeId}")
    @Modifying
    int deleteAllByProductTypeId(short productTypeId);

    @Query(value = "update Stamping set manualReinspectResult = :#{#status}, reinspectBy = :#{#reinspectBy} where id = :#{#id}")
    @Modifying
    int updateStampingById(long id, String status, String reinspectBy);


    default Specification<Stamping> buildSpecification(PLCSearchCriteria criteria) {
        return (root, query, builder) -> query.where(buildPredicates(root, query, builder, criteria).toArray(new Predicate[0]))
                .orderBy(builder.desc(root.get("logTime"))).getRestriction();
    }

    default CriteriaQuery<Object[]> buildCountQualifiedProducts(PLCSearchCriteria criteria, CriteriaBuilder builder) {
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<Stamping> root = query.from(Stamping.class);
        return query.multiselect(root.get("autoInspectResult"), builder.count(root)).where(buildPredicates(root, query, builder, criteria).toArray(new Predicate[0]))
                .groupBy(root.get("autoInspectResult"));
    }

    default List<Predicate> buildPredicates(Root<Stamping> root, CriteriaQuery<?> query, CriteriaBuilder builder, PLCSearchCriteria criteria) {
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.hasText(criteria.getBarcode())) {
            predicates.add(builder.equal(root.get("barcode"), criteria.getBarcode()));
        }
        if (StringUtils.hasText(criteria.getBarcodeData())) {
            predicates.add(builder.like(root.get("barcodeData"), criteria.getBarcodeData() + "%"));
        }
        LocalDateTime from = criteria.getFrom();
        LocalDateTime end = criteria.getEnd();
        if (null == from && predicates.isEmpty()) {
            from = LocalDate.now().atStartOfDay();
        }
        if (null == end && predicates.isEmpty()) {
            end = LocalDateTime.now();
        }
        if (null != from) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("logTime"), from));
        }
        if (null != end) {
            predicates.add(builder.lessThanOrEqualTo(root.get("logTime"), end));
        }
        if (null != criteria.getAutoInspectResult()) {
            predicates.add(builder.equal(root.get("autoInspectResult"), criteria.getAutoInspectResult()));
            if (Objects.equals("设备OK", criteria.getAutoInspectResult())) {
                predicates.add(builder.and(builder.isNotNull(root.get("barcodeData")), builder.notEqual(root.get("barcodeData"), "")));
            }
        }
        predicates.add(builder.equal(root.get("productTypeId"), criteria.getProductTypeId()));
        return predicates;
    }
}
