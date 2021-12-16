package com.demo.config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatternConfigRepository extends JpaRepository<PatternConfig, Integer> {


    @Query("from PatternConfig where productTypeId = :#{#productTypeId}")
    PatternConfig findByProductTypeId(short productTypeId);


}
