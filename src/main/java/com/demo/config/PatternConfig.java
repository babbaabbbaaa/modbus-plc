package com.demo.config;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "plc_pattern_config")
public class PatternConfig {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String productType;
    private short productTypeId;
    private int start;
    private int end;
}
