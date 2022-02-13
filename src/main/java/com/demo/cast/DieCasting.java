package com.demo.cast;


import com.demo.config.PatternConfig;
import com.demo.config.PatternConfigRepository;
import com.demo.enums.BarcodeDuplicateEnum;
import com.demo.plc.IPLCData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

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
    private LocalDateTime logTime = LocalDateTime.now();
    private int moldNo;
    private int injectionNo;
    private short productTypeId;
    @OneToMany(mappedBy = "castingId")
    private List<SubDieCasting> subDieCastings;

    @Transient
    private int index;

    public DieCasting(short[] dataArrays, short[] barcodeArrays,
                      PatternConfigRepository patternConfigRepository,
                      SubDieCastingRepository subDieCastingRepository) {

        this.productTypeId = dataArrays[3];
        this.moldNo = dataArrays[4];
        this.injectionNo = dataArrays[5];
        this.subDieCastings = new ArrayList<>();
        PatternConfig patternConfig = patternConfigRepository.findByProductTypeId(this.productTypeId);
        initSubDieCasting(dataArrays, barcodeArrays, "A", patternConfig, subDieCastingRepository);
        initSubDieCasting(dataArrays, barcodeArrays, "B", patternConfig, subDieCastingRepository);
    }

    private void initSubDieCasting(short[] dataArrays, short[] barcodeArrays, String type,
                                   PatternConfig patternConfig, SubDieCastingRepository subDieCastingRepository) {
        SubDieCasting subDieCasting = new SubDieCasting(dataArrays, barcodeArrays, type);
        if (null != patternConfig) {
            subDieCasting.setBarcode(getBarcode(subDieCasting.getBarcodeData(), patternConfig.getStart(), patternConfig.getEnd()));
            subDieCasting.setBarcodeGrade(getBarcode(subDieCasting.getBarcodeData(), patternConfig.getQualifiedStart(), patternConfig.getQualifiedEnd()));
            checkDup(subDieCasting.getBarcode(), subDieCastingRepository);
        }
        subDieCasting.setAutoInspectionResult();
        this.subDieCastings.add(subDieCasting);
    }

    private void checkDup(String barcode,
                          SubDieCastingRepository subDieCastingRepository) {
        List<SubDieCasting> subDieCastings = subDieCastingRepository.findSubDieCastingByBarcodeAndProduct(barcode, productTypeId);
        if (!CollectionUtils.isEmpty(subDieCastings)) {
            for (SubDieCasting subDieCasting : subDieCastings) {
                subDieCasting.setDuplicated(BarcodeDuplicateEnum.DUP);
            }
            subDieCastingRepository.saveAll(subDieCastings);
        }
    }

    @Override
    public BarcodeDuplicateEnum getDuplicated() {
        return BarcodeDuplicateEnum.NONE;
    }
}
