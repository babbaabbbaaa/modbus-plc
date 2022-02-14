package com.demo.domain.stamping;


import com.demo.enums.BarcodeDuplicateEnum;
import com.demo.plc.IPLCData;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "plc_data")
public class PLCData implements IPLCData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Transient
    private int index;
    private String dataSource;
    private short productTypeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    private BarcodeDuplicateEnum duplicated = BarcodeDuplicateEnum.NONE;
    private LocalDateTime confirmedTime;
    private String confirmedIp;
    private String comments;
    private String barcodeQualify = "";
    private boolean qualified;
    private String autoInspectResult;
    private String manualReinspectResult;
    private String reinspectBy;
    @Transient
    private int ratio;
    @Transient
    private short duplicate; //重码标记
    @Transient
    private short ready;
    @Transient
    private boolean valid;


}
