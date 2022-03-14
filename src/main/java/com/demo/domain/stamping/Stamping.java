package com.demo.domain.stamping;


import com.demo.enums.BarcodeDuplicateEnum;
import com.demo.plc.IPLCData;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "plc_data")
public class Stamping implements IPLCData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private String barcodeGrade = "";
    //通规等功能是否合格
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

    @Transient
    public static final Stamping EMPTY_PLC_DATA = new Stamping(true);

    public Stamping(boolean empty) {
        if (empty) {
            this.barcodeData = "000017212109004100@0000373029@20052200006:92:A";
            this.barcode = "20052200006";
            this.barcodeGrade = "A";
            this.generalFunc = "使用且结果合格";
            this.heightFunc = "使用且结果合格";
            this.spinCheckFunc = "使用且结果合格";
            this.scanFunc = "使用且结果合格";
            this.typhoonFunc = "使用且结果合格";
            this.slotDepthFunc = "使用且结果合格";
            this.flagFunc = "使用且结果合格";
            this.weldFunc = "使用且结果合格";
            this.heightMeasure1 = 11.123F;
            this.heightMeasure2 = 11.123F;
            this.heightMeasure3 = 11.123F;
            this.heightMeasure4 = 11.123F;
            this.heightMeasure5 = 11.123F;
            this.heightMeasure6 = 11.123F;
            this.heightMeasure7 = 11.123F;
            this.heightMeasure8 = 11.123F;
            this.heightMeasure9 = 11.123F;
            this.heightMeasure10 = 11.123F;
            this.heightMeasure11 = 11.123F;
            this.heightMeasure12 = 11.123F;
            this.heightMeasure13 = 11.123F;
            this.heightMeasure14 = 11.123F;
            this.heightMeasure15 = 11.123F;
            this.heightMeasure16 = 11.123F;
            this.heightMeasure17 = 11.123F;
            this.heightMeasure18 = 11.123F;
            this.heightMeasure19 = 11.123F;
            this.heightMeasure20 = 11.123F;
            this.duplicate = 0;
            this.productTypeId = 0;
            this.autoInspectResult = "设备OK";
        }
    }


    public void autoInspect() {
        if (!StringUtils.hasText(this.barcodeData)) {
            this.autoInspectResult = "";
            return;
        }
        if (this.duplicated == BarcodeDuplicateEnum.DUP) {
            this.autoInspectResult = "设备NG";
            return;
        }
        if (qualified) {
            this.autoInspectResult = "设备OK";
        } else {
            this.autoInspectResult = "设备NG";
        }

    }
}
