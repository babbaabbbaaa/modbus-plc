package com.demo.cast;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubDieCastingRepository extends JpaRepository<SubDieCasting, Long> {

    @Query("from SubDieCasting where barcode = :#{#barcode} and productTypeId = :#{#productTypeId}")
    List<SubDieCasting> findSubDieCastingByBarcodeAndProduct(String barcode, int productTypeId);

    @Modifying
    @Query("update SubDieCasting s set s.manualReinspectResult = :#{#status}, s.reinspectBy = :#{#reinspectBy} where id = :#{#id}")
    int saveManualReinspectResult(String status, String reinspectBy, long id);

}
