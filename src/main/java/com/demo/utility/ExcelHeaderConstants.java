package com.demo.utility;

import com.demo.domain.casting.DieCasting;
import com.demo.domain.casting.SubDieCasting;
import com.demo.domain.stamping.Stamping;

import java.util.Arrays;
import java.util.List;

public class ExcelHeaderConstants {

    public static final List<ExcelCell> EXCEL_HEADERS = Arrays.asList(


            new ExcelCell("序号", 2500, "getIndex", Stamping.class, DataType.INTEGER),
            new ExcelCell("数据源", 3000, "getDataSource", Stamping.class, DataType.STRING),
            new ExcelCell("时间", 5000, "getLogTime", Stamping.class, DataType.STRING),
            new ExcelCell("自动线检测结果", 5000, "getAutoInspectResult", Stamping.class, DataType.STRING),
            new ExcelCell("人工复检结果", 5000, "getManualReinspectResult", Stamping.class, DataType.STRING),
            new ExcelCell("复检人员", 5000, "getReinspectBy", Stamping.class, DataType.STRING),
            new ExcelCell("SR1000二维码编号", 14000, "getBarcodeData", Stamping.class, DataType.STRING),
            new ExcelCell("二维码字符提取", 5000, "getBarcode", Stamping.class, DataType.STRING),
            new ExcelCell("托盘识别号", 5000, "getTrayIdentityCode", Stamping.class, DataType.STRING),
            new ExcelCell("通规功能", 4000, "getGeneralFunc", Stamping.class, DataType.STRING),
            new ExcelCell("测高功能", 4000, "getHeightFunc", Stamping.class, DataType.STRING),
            new ExcelCell("打标功能", 4000, "getFlagFunc", Stamping.class, DataType.STRING),
            new ExcelCell("读码功能", 4000, "getScanFunc", Stamping.class, DataType.STRING),
            new ExcelCell("台风功能", 4000, "getTyphoonFunc", Stamping.class, DataType.STRING),
            new ExcelCell("槽深检测功能", 4000, "getSlotDepthFunc", Stamping.class, DataType.STRING),
            new ExcelCell("旋转错误检测", 4000, "getSpinCheckFunc", Stamping.class, DataType.STRING),
            new ExcelCell("焊缝功能", 4000, "getWeldFunc", Stamping.class, DataType.STRING),
            new ExcelCell("测高数值1", 4000, "getHeightMeasure1", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值2", 4000, "getHeightMeasure2", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值3", 4000, "getHeightMeasure3", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值4", 4000, "getHeightMeasure4", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值5", 4000, "getHeightMeasure5", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值6", 4000, "getHeightMeasure6", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值7", 4000, "getHeightMeasure7", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值8", 4000, "getHeightMeasure8", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值9", 4000, "getHeightMeasure9", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值10", 4000, "getHeightMeasure10", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值11", 4000, "getHeightMeasure11", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值12", 4000, "getHeightMeasure12", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值13", 4000, "getHeightMeasure13", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值14", 4000, "getHeightMeasure14", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值15", 4000, "getHeightMeasure15", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值16", 4000, "getHeightMeasure16", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值17", 4000, "getHeightMeasure17", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值18", 4000, "getHeightMeasure18", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值19", 4000, "getHeightMeasure19", Stamping.class, DataType.FLOAT),
            new ExcelCell("测高数值20", 4000, "getHeightMeasure20", Stamping.class, DataType.FLOAT)
    );

    public static final List<ExcelCell> DIE_CASTING_EXCEL_HEADERS = Arrays.asList(
            new ExcelCell("序号", 2500, "getIndex", DieCasting.class, DataType.INTEGER, 0, 1),
            new ExcelCell("时间", 5000, "getLogTime", DieCasting.class, DataType.STRING, 0, 1),
            new ExcelCell("模具号", 5000, "getMoldNo", DieCasting.class, DataType.STRING, 0, 1),
            new ExcelCell("压射号", 5000, "getInjectionNo", DieCasting.class, DataType.INTEGER, 0, 1),
            new ExcelCell("流水号", 2500, "getSerialNumber", SubDieCasting.class, DataType.STRING),
            new ExcelCell("SR1000二维码编号", 14000, "getBarcodeData", SubDieCasting.class, DataType.STRING),
            new ExcelCell("读码判定结果", 4000, "getCodeReadingFunc", SubDieCasting.class, DataType.STRING),
            new ExcelCell("铝重差值", 5000, "getWeightDifference", SubDieCasting.class, DataType.FLOAT),
            new ExcelCell("铝重判定结果", 5000, "getWeightResult", SubDieCasting.class, DataType.STRING),
            new ExcelCell("自动线检测结果", 5000, "getAutoInspectResult", SubDieCasting.class, DataType.STRING),
            new ExcelCell("人工复检结果", 5000, "getManualReinspectResult", SubDieCasting.class, DataType.STRING),
            new ExcelCell("复检人员", 5000, "getReinspectBy", SubDieCasting.class, DataType.STRING),
            new ExcelCell("二维码字符提取", 5000, "getBarcode", SubDieCasting.class, DataType.STRING),
            new ExcelCell("二维码等级提取", 5000, "getBarcodeGrade", SubDieCasting.class, DataType.STRING),
            new ExcelCell("铝重公差上限设定值", 4000, "getMaxWeightTolerance", SubDieCasting.class, DataType.FLOAT),
            new ExcelCell("铝重公差下限设定值", 4000, "getMinWeightTolerance", SubDieCasting.class, DataType.FLOAT),
            new ExcelCell("铝重压铸前", 4000, "getWeightBeforeDieCasting", SubDieCasting.class, DataType.FLOAT),
            new ExcelCell("铝重压铸后", 4000, "getWeightAfterDieCasting", SubDieCasting.class, DataType.FLOAT),
            new ExcelCell("使用产品穴数选择", 4000, "getHoleSelection", SubDieCasting.class, DataType.STRING),
            new ExcelCell("打标使用状态", 4000, "getMarkingFunc", SubDieCasting.class, DataType.STRING),
            new ExcelCell("数据源", 3000, "getDatasource", SubDieCasting.class, DataType.STRING)
    );
}
