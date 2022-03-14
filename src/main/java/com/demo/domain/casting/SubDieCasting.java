package com.demo.domain.casting;


import com.demo.enums.BarcodeDuplicateEnum;
import com.demo.enums.GeneralFunctionEnum;
import com.demo.plc.IPLCData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "sub_die_casting")
public class SubDieCasting implements IPLCData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long castingId;
    private Integer serialNumber;
    private String barcodeData;
    private String markingFunc;  //打标
    @Column(columnDefinition = "double")
    private Float weightDifference;
    private String weightResult;
    private String autoInspectResult;
    private String manualReinspectResult;
    private String reinspectBy;
    private String barcode;
    private String barcodeGrade;
    @Column(columnDefinition = "double")
    private Float maxWeightTolerance;
    @Column(columnDefinition = "double")
    private Float minWeightTolerance;
    @Column(columnDefinition = "double")
    private Float weightBeforeDieCasting;
    @Column(columnDefinition = "double")
    private Float weightAfterDieCasting;
    private String holeSelection;
    private String codeReadingFunc; // 读码
    private String datasource = "自动线";
    private int productTypeId;

    @Enumerated(EnumType.STRING)
    private BarcodeDuplicateEnum duplicated = BarcodeDuplicateEnum.NONE;


    public SubDieCasting(short[] dataArray, short[] barcodeArray, String type) {
        if ("A".equals(type)) {
            this.holeSelection = dataArray[7] == 1 ? "1穴使用" : "1穴屏蔽";
            this.serialNumber = (int) dataArray[10];
            this.markingFunc = GeneralFunctionEnum.mapDefinition(dataArray[8]);
            this.codeReadingFunc = GeneralFunctionEnum.mapDefinition(dataArray[9]);
            this.weightBeforeDieCasting = getFloatValue(dataArray[17], dataArray[18]);
            this.weightAfterDieCasting = getFloatValue(dataArray[19], dataArray[20]);
            this.weightDifference = getFloatValue(dataArray[21], dataArray[22]);
            this.maxWeightTolerance = getFloatValue(dataArray[23], dataArray[24]);
            this.minWeightTolerance = getFloatValue(dataArray[25], dataArray[26]);
            this.barcodeData = getBarcodeData(barcodeArray, 0, 50);
        } else {
            this.holeSelection = dataArray[12] == 1 ? "2穴使用" : "2穴屏蔽";
            this.serialNumber = (int) dataArray[15];
            this.markingFunc = GeneralFunctionEnum.mapDefinition(dataArray[13]);
            this.codeReadingFunc = GeneralFunctionEnum.mapDefinition(dataArray[14]);
            this.weightBeforeDieCasting = getFloatValue(dataArray[27], dataArray[28]);
            this.weightAfterDieCasting = getFloatValue(dataArray[29], dataArray[30]);
            this.weightDifference = getFloatValue(dataArray[31], dataArray[32]);
            this.maxWeightTolerance = getFloatValue(dataArray[33], dataArray[34]);
            this.minWeightTolerance = getFloatValue(dataArray[35], dataArray[36]);
            this.barcodeData = getBarcodeData(barcodeArray, 50, 50);
        }
        if (weightAfterDieCasting - weightBeforeDieCasting > minWeightTolerance &&
                weightAfterDieCasting - weightBeforeDieCasting < maxWeightTolerance) {
            this.weightResult = "合格";
        } else {
            this.weightResult = "不合格";
        }
    }

    public SubDieCasting(int serialNumber) {
        this.serialNumber = serialNumber;
        if (1 == serialNumber) {
            this.holeSelection = "A穴使用";
            this.barcodeData = "00000AAA9055750000@0702012027@22011790465:96:A";
            this.barcode = "22011790465";
        } else {
            this.holeSelection = "B穴使用";
            this.barcodeData = "00000AAA9055750000@0702012027@22011790464:96:A";
            this.barcode = "22011790464";
        }
        this.barcodeGrade = "A";
        this.markingFunc = "使用且结果合格";
        this.codeReadingFunc = "使用且结果合格";
        this.weightBeforeDieCasting = 11.234F;
        this.weightAfterDieCasting = 11.123F;
        this.weightDifference = 0.111F;
        this.maxWeightTolerance = 0.2F;
        this.minWeightTolerance = -0.2F;
        this.weightResult = "合格";
    }

    public void autoInspectionResult() {
        if (!StringUtils.hasText(this.barcodeData)) {
            this.autoInspectResult = "";
            return;
        }
        if (this.duplicated == BarcodeDuplicateEnum.DUP) {
            this.autoInspectResult = "设备NG";
            return;
        }
        if ("使用但结果不合格".equalsIgnoreCase(this.codeReadingFunc) //读码合格
                && "使用但结果不合格".equalsIgnoreCase(this.weightResult) //铝重合格
                && "使用但结果不合格".equalsIgnoreCase(this.markingFunc)) { //打标合格
            this.autoInspectResult = "设备OK";
        } else {
            this.autoInspectResult = "设备NG";
        }
    }

}
