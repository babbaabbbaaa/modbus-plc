package com.demo.domain.casting;

import com.demo.model.PLCSearchCriteria;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface DieCastingRepository extends JpaRepository<DieCasting, Long>, JpaSpecificationExecutor<DieCasting> {

    @Query("delete from DieCasting where productTypeId = :#{#productTypeId}")
    @Modifying
    int deleteAllByProductTypeId(int productTypeId);

    default Specification<DieCasting> buildSpecification(PLCSearchCriteria criteria) {
        return (root, query, builder) -> query.where(buildPredicates(root, builder, criteria).toArray(new Predicate[0]))
                .orderBy(builder.desc(root.get("logTime"))).getRestriction();
    }

    default CriteriaQuery<Object[]> buildCountQualifiedProducts(PLCSearchCriteria criteria, CriteriaBuilder builder) {
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<DieCasting> root = query.from(DieCasting.class);
        Path<Collection<SubDieCasting>> subDieCasting = root.join("subDieCastings");
        return query.multiselect(subDieCasting.get("autoInspectResult"), builder.countDistinct(subDieCasting.get("subId"))).where(buildPredicates(root, builder, criteria).toArray(new Predicate[0]))
                .groupBy(subDieCasting.get("autoInspectResult"));
    }

    default List<Predicate> buildPredicates(Root<DieCasting> root, CriteriaBuilder builder, PLCSearchCriteria criteria) {
        List<Predicate> predicates = new ArrayList<>();
        Path<Collection<SubDieCasting>> subDieCasting = root.join("subDieCastings");
        if (StringUtils.hasText(criteria.barcode())) {
            predicates.add(builder.equal(subDieCasting.get("barcode"), criteria.barcode()));
        }
        if (StringUtils.hasText(criteria.barcodeData())) {
            predicates.add(builder.like(subDieCasting.get("barcodeData"), criteria.barcodeData() + "%"));
        }
        if (StringUtils.hasText(criteria.autoInspectResult())) {
            predicates.add(builder.equal(subDieCasting.get("autoInspectResult"), criteria.autoInspectResult()));
        }
        if (StringUtils.hasText(criteria.manualReinspectResult())) {
            predicates.add(builder.equal(subDieCasting.get("manualReinspectResult"), criteria.manualReinspectResult()));
        }
        if (StringUtils.hasText(criteria.reinspectBy())) {
            predicates.add(builder.equal(subDieCasting.get("reinspectBy"), criteria.reinspectBy()));
        }
        LocalDateTime from = criteria.from();
        LocalDateTime end = criteria.end();
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
        predicates.add(builder.equal(root.get("productTypeId"), criteria.productTypeId()));
        return predicates;
    }
}
