package com.demo.utility;

import com.demo.domain.PLCData;

import java.util.Arrays;
import java.util.List;

public class ExcelHeaderConstants {


    public static final List<ExcelCell> EXCEL_HEADERS = Arrays.asList(
            new ExcelCell("序号", 2500, "getIndex", PLCData.class, DataType.INTEGER),
            new ExcelCell("数据源", 3000, "getDataSource", PLCData.class, DataType.STRING),
            new ExcelCell("时间", 5000, "getLogTime", PLCData.class, DataType.STRING),
            new ExcelCell("SR1000二维码编号", 14000, "getBarcodeData", PLCData.class, DataType.STRING),
            new ExcelCell("二维码字符提取", 5000, "getBarcode", PLCData.class, DataType.STRING),
            new ExcelCell("通规功能", 4000, "getGeneralFunc", PLCData.class, DataType.STRING),
            new ExcelCell("测高功能", 4000, "getHeightFunc", PLCData.class, DataType.STRING),
            new ExcelCell("打标功能", 4000, "getFlagFunc", PLCData.class, DataType.STRING),
            new ExcelCell("读码功能", 4000, "getScanFunc", PLCData.class, DataType.STRING),
            new ExcelCell("台风功能", 4000, "getTyphoonFunc", PLCData.class, DataType.STRING),
            new ExcelCell("槽深检测功能", 4000, "getSlotDepthFunc", PLCData.class, DataType.STRING),
            new ExcelCell("旋转错误检测", 4000, "getSpinCheckFunc", PLCData.class, DataType.STRING),
            new ExcelCell("焊缝功能", 4000, "getWeldFunc", PLCData.class, DataType.STRING),
            new ExcelCell("测高数值1", 4000, "getHeightMeasure1", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值2", 4000, "getHeightMeasure2", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值3", 4000, "getHeightMeasure3", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值4", 4000, "getHeightMeasure4", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值5", 4000, "getHeightMeasure5", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值6", 4000, "getHeightMeasure6", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值7", 4000, "getHeightMeasure7", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值8", 4000, "getHeightMeasure8", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值9", 4000, "getHeightMeasure9", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值10", 4000, "getHeightMeasure10", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值11", 4000, "getHeightMeasure11", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值12", 4000, "getHeightMeasure12", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值13", 4000, "getHeightMeasure13", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值14", 4000, "getHeightMeasure14", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值15", 4000, "getHeightMeasure15", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值16", 4000, "getHeightMeasure16", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值17", 4000, "getHeightMeasure17", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值18", 4000, "getHeightMeasure18", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值19", 4000, "getHeightMeasure19", PLCData.class, DataType.FLOAT),
            new ExcelCell("测高数值20", 4000, "getHeightMeasure20", PLCData.class, DataType.FLOAT)
    );
}
