package com.demo.domain;

import com.demo.model.PLCSearchCriteria;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public interface PLCRepository extends JpaRepository<PLCData, Long>, JpaSpecificationExecutor<PLCData> {

    @Query("from PLCData where barcode = :#{#barcode}")
    List<PLCData> getDataByBarcode(String barcode);

    @Query("delete from PLCData as d where d.productTypeId = :#{#productTypeId}")
    @Modifying
    int deleteAllByProductTypeId(short productTypeId);

    default Specification<PLCData> buildSpecification(PLCSearchCriteria criteria) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(criteria.getBarcode())) {
                predicates.add(builder.equal(root.get("barcode"), criteria.getBarcode()));
            }
            if (StringUtils.hasText(criteria.getBarcodeData())) {
                predicates.add(builder.equal(root.get("barcode"), criteria.getBarcodeData()));
            }
            LocalDate from = criteria.getFrom();
            LocalDate end = criteria.getEnd();
            if (null == from && predicates.isEmpty()) {
                from = LocalDate.now();
            }
            if (null == end && predicates.isEmpty()) {
                end = LocalDate.now();
            }
            if (null != from) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("logTime"), from.atStartOfDay()));
            }
            if (null != end) {
                predicates.add(builder.lessThanOrEqualTo(root.get("logTime"), end.atTime(LocalTime.MAX)));
            }
            predicates.add(builder.equal(root.get("productTypeId"), criteria.getProductTypeId()));
            return query.where(predicates.toArray(new Predicate[0])).orderBy(new OrderImpl(root.get("logTime"), false)).getRestriction();
        };
    }
}
