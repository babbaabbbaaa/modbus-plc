package com.demo.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "plc_data")
public class PLCData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Transient
    private int index;
    private String dataSource;
    private short productTypeId;
    private LocalDateTime logTime;
    private float heightMeasure1;
    private float heightMeasure2;
    private float heightMeasure3;
    private float heightMeasure4;
    private float heightMeasure5;
    private float heightMeasure6;
    private float heightMeasure7;
    private float heightMeasure8;
    private float heightMeasure9;
    private float heightMeasure10;
    private float heightMeasure11;
    private float heightMeasure12;
    private float heightMeasure13;
    private float heightMeasure14;
    private float heightMeasure15;
    private float heightMeasure16;
    private float heightMeasure17;
    private float heightMeasure18;
    private float heightMeasure19;
    private float heightMeasure20;
    private String generalFunc;
    private String heightFunc;
    private String flagFunc;
    private String scanFunc;
    private String typhoonFunc;
    private String slotDepthFunc;
    private String spinCheckFunc;
    private String weldFunc;
    private String barcodeData;
    private String barcode;
    @Enumerated(EnumType.STRING)
    private BarcodeDuplicateEnum duplicated;
    private LocalDateTime confirmedTime;
    private String confirmedIp;
    private String comments;
    @Transient
    private short ratio;


}
