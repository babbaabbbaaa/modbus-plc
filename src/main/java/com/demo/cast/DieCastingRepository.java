package com.demo.cast;

import com.demo.model.PLCSearchCriteria;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface DieCastingRepository extends JpaRepository<DieCasting, Long>, JpaSpecificationExecutor<DieCasting> {


    default Specification<DieCasting> buildSpecification(PLCSearchCriteria criteria) {
        return (root, query, builder) -> query.where(buildPredicates(root, builder, criteria).toArray(new Predicate[0]))
                .orderBy(new OrderImpl(root.get("logTime"), false)).getRestriction();
    }


    default List<Predicate> buildPredicates(Root<DieCasting> root, CriteriaBuilder builder, PLCSearchCriteria criteria) {
        List<Predicate> predicates = new ArrayList<>();
        Path<Collection<SubDieCasting>> subDieCasting = root.join("subDieCastings");
        if (StringUtils.hasText(criteria.getBarcode())) {
            predicates.add(builder.equal(subDieCasting.get("barcode"), criteria.getBarcode()));
        }
        if (StringUtils.hasText(criteria.getBarcodeData())) {
            predicates.add(builder.equal(subDieCasting.get("barcodeData"), criteria.getBarcodeData()));
        }
        if (StringUtils.hasText(criteria.getAutoInspectResult())) {
            predicates.add(builder.equal(subDieCasting.get("autoInspectResult"), criteria.getAutoInspectResult()));
        }
        if (StringUtils.hasText(criteria.getManualReinspectResult())) {
            predicates.add(builder.equal(subDieCasting.get("manualReinspectResult"), criteria.getManualReinspectResult()));
        }
        if (StringUtils.hasText(criteria.getReinspectBy())) {
            predicates.add(builder.equal(subDieCasting.get("reinspectBy"), criteria.getReinspectBy()));
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
        predicates.add(builder.equal(root.get("productTypeId"), criteria.getProductTypeId()));
        return predicates;
    }
}
