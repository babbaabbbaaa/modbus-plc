package com.demo.utility;

import org.apache.poi.ss.usermodel.*;

import java.util.Map;

/**
 * @author Rocker Chen
 */
public enum CellStyleEnum {

    FLOAT_NORMAL {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> {
                CellStyle cellStyle = createCellStyle(workbook, (short) -1);
                cellStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.0000"));
                return cellStyle;
            });
        }
    },
    FLOAT_RED {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> {
                CellStyle cellStyle = createCellStyle(workbook, IndexedColors.RED.getIndex());
                cellStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.0000"));
                return cellStyle;
            });
        }
    },
    FLOAT_YELLOW {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> {
                CellStyle cellStyle = createCellStyle(workbook, IndexedColors.YELLOW.getIndex());
                cellStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.0000"));
                return cellStyle;
            });
        }
    },
    GENERAL_NORMAL {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> createCellStyle(workbook, (short) -1));
        }
    },
    GENERAL_RED {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> createCellStyle(workbook, IndexedColors.RED.getIndex()));
        }
    },
    GENERAL_YELLOW {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> createCellStyle(workbook, IndexedColors.YELLOW.getIndex()));
        }
    },

    ;


    public abstract CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) ;

    protected static CellStyle createCellStyle(Workbook workbook, short indexedColor) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        if (indexedColor > -1) {
            cellStyle.setFillForegroundColor(indexedColor);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        return cellStyle;
    }
}
