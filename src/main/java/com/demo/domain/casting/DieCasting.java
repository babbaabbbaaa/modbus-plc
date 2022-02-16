package com.demo.domain.casting;


import com.demo.config.PatternConfig;
import com.demo.config.PatternConfigRepository;
import com.demo.enums.BarcodeDuplicateEnum;
import com.demo.plc.IPLCData;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class DieCasting implements IPLCData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime logTime;
    private int moldNo;
    private int injectionNo;
    private short productTypeId;
    @OneToMany(mappedBy = "castingId", cascade = CascadeType.REMOVE)
    private List<SubDieCasting> subDieCastings;

    @Transient
    private int index;

    @Transient
    public static final DieCasting EMPTY_DIE_CASTING = new DieCasting(true);

    public DieCasting(short[] dataArrays, short[] barcodeArrays,
                      PatternConfigRepository patternConfigRepository) {

        this.logTime = LocalDateTime.now();
        this.productTypeId = dataArrays[3];
        this.moldNo = dataArrays[4];
        this.injectionNo = dataArrays[5];
        this.subDieCastings = new ArrayList<>();
        PatternConfig patternConfig = patternConfigRepository.findByProductTypeId(this.productTypeId);
        initSubDieCasting(dataArrays, barcodeArrays, "A", patternConfig);
        initSubDieCasting(dataArrays, barcodeArrays, "B", patternConfig);
    }

    public DieCasting(boolean empty) {
        if (empty) {
            this.logTime = LocalDateTime.now();
            this.productTypeId = 0;
            this.moldNo = 1;
            this.injectionNo = 1;
            this.subDieCastings = new ArrayList<>();
            subDieCastings.add(new SubDieCasting(1));
            subDieCastings.add(new SubDieCasting(2));
        }
    }

    private void initSubDieCasting(short[] dataArrays, short[] barcodeArrays, String type,
                                   PatternConfig patternConfig) {
        SubDieCasting subDieCasting = new SubDieCasting(dataArrays, barcodeArrays, type);
        if (null != patternConfig) {
            subDieCasting.setBarcode(getBarcode(subDieCasting.getBarcodeData(), patternConfig.getStart(), patternConfig.getEnd()));
            subDieCasting.setBarcodeGrade(getBarcode(subDieCasting.getBarcodeData(), patternConfig.getQualifiedStart(), patternConfig.getQualifiedEnd()));
        }
        subDieCasting.setAutoInspectionResult();
        subDieCasting.setProductTypeId(this.productTypeId);
        this.subDieCastings.add(subDieCasting);
    }


    @Override
    public BarcodeDuplicateEnum getDuplicated() {
        return BarcodeDuplicateEnum.NONE;
    }
}
