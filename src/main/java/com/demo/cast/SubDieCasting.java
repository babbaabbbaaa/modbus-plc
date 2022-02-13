package com.demo.cast;


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

    @Enumerated(EnumType.STRING)
    private BarcodeDuplicateEnum duplicated = BarcodeDuplicateEnum.NONE;


    public SubDieCasting(short[] dataArray, short[] barcodeArray, String type) {
        if ("A".equals(type)) {
            this.holeSelection = dataArray[6] == 1 ? "A穴使用" : "A穴未使用";
            this.serialNumber = (int) dataArray[7];
            this.markingFunc = GeneralFunctionEnum.mapDefinition(dataArray[8]);
            this.codeReadingFunc = GeneralFunctionEnum.mapDefinition(dataArray[9]);
            this.weightBeforeDieCasting = getFloatValue(dataArray[14], dataArray[15]);
            this.weightAfterDieCasting = getFloatValue(dataArray[16], dataArray[17]);
            this.weightDifference = getFloatValue(dataArray[18], dataArray[19]);
            this.maxWeightTolerance = getFloatValue(dataArray[20], dataArray[21]);
            this.minWeightTolerance = getFloatValue(dataArray[22], dataArray[23]);
            this.barcodeData = getBarcodeData(barcodeArray, 0, 50);
        } else {
            this.holeSelection = dataArray[10] == 1 ? "B穴使用" : "B穴未使用";
            this.serialNumber = (int) dataArray[11];
            this.markingFunc = GeneralFunctionEnum.mapDefinition(dataArray[12]);
            this.codeReadingFunc = GeneralFunctionEnum.mapDefinition(dataArray[13]);
            this.weightBeforeDieCasting = getFloatValue(dataArray[23], dataArray[25]);
            this.weightAfterDieCasting = getFloatValue(dataArray[26], dataArray[27]);
            this.weightDifference = getFloatValue(dataArray[28], dataArray[29]);
            this.maxWeightTolerance = getFloatValue(dataArray[30], dataArray[31]);
            this.minWeightTolerance = getFloatValue(dataArray[32], dataArray[33]);
            this.barcodeData = getBarcodeData(barcodeArray, 50, 50);
        }
        if (weightAfterDieCasting - weightBeforeDieCasting > minWeightTolerance &&
                weightAfterDieCasting - weightBeforeDieCasting < maxWeightTolerance) {
            this.weightResult = "合格";
        } else {
            this.weightResult = "不合格";
        }
    }

    public void setAutoInspectionResult() {
        if (StringUtils.hasText(this.barcodeData)) {
            if ("".equalsIgnoreCase(this.codeReadingFunc) //读码合格
                    && "".equalsIgnoreCase(this.weightResult) //铝重合格
                    && this.duplicated != BarcodeDuplicateEnum.DUP) {
                this.autoInspectResult = "合格";
            } else {
                this.autoInspectResult = "不合格";
            }
        }

    }

    @Override
    public void setIndex(int index) {

    }
}
