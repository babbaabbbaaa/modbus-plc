package com.demo.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rocker Chen
 */
public enum ExcelFormat {

    DATE_TIME_NORMAL{
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> {
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                return cellStyle;
            });
        }
    },
    DATE_TIME_DUP {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> {
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                return cellStyle;
            });

        }
    },
    DATE_TIME_CONFIRMED {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> {
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                return cellStyle;
            });

        }
    },
    FLOAT_NORMAL {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> {
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
                return cellStyle;
            });

        }
    },
    FLOAT_DUP {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> {
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
                return cellStyle;
            });

        }
    },
    FLOAT_CONFIRMED {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> {
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
                return cellStyle;
            });

        }
    },
    STRING_NORMAL {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> {
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                return cellStyle;
            });

        }
    },
    STRING_DUP {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> {
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                return cellStyle;
            });

        }
    },
    STRING_CONFIRMED {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> {
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                return cellStyle;
            });

        }
    },
    INTEGER_NORMAL {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> {
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                return cellStyle;
            });

        }
    },
    INTEGER_DUP {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> {
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                return cellStyle;
            });

        }
    },
    INTEGER_CONFIRMED {
        @Override
        public CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) {
            return cellStyleMap.computeIfAbsent(this.ordinal(), (k) -> {
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                return cellStyle;
            });

        }
    },
    ;


    public abstract CellStyle createCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap) ;
}
