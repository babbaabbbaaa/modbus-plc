package com.demo.utility;

import com.demo.domain.BarcodeDuplicateEnum;
import com.demo.domain.PLCData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.demo.domain.BarcodeDuplicateEnum.CONFIRMED;
import static com.demo.domain.BarcodeDuplicateEnum.DUP;

/**
 * @author Rocker Chen
 */
public class ExcelHelper {


    /**
     * Filling data by using java reflect.
     *
     * @param rows       data need to be filled in Excel
     * @param headerList a title list with Excel title and bean field
     * @return an Excel workbook filled with PLC data
     */
    public static byte[] writeBean(List<PLCData> rows, List<ExcelCell> headerList) throws Exception {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        XSSFFont headerFont = (XSSFFont) workbook.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        XSSFCellStyle headerStyle = (XSSFCellStyle) createCellStyle(workbook, headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        SXSSFSheet sheet = workbook.createSheet();
        SXSSFRow header = sheet.createRow(0);
        int i = 0;
        for (ExcelCell head : headerList) {
            head.createHeader(header, headerStyle, i++);
        }
        Map<Integer, CellStyle> cellStyleMap = new HashMap<>();
        i = 1;
        if (!CollectionUtils.isEmpty(rows)) {
            for (PLCData row : rows) {
                row.setIndex(i);
                SXSSFRow body = sheet.createRow(i++);
                int j = 0;
                for (ExcelCell item : headerList) {
                    item.createCell(body, getCellStyle(workbook, cellStyleMap, item.getFormat(), row.getDuplicated()), item.getInvokeMethod().invoke(row), j++);
                }
            }
        }
        return parseWorkBook(workbook);
    }

    private static byte[] parseWorkBook(SXSSFWorkbook workbook) throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            workbook.write(out);
            return out.toByteArray();
        } finally {
            if (null != workbook) {
                workbook.dispose();
                workbook.close();
            }
        }
    }

    private static CellStyle createCellStyle(Workbook workbook, XSSFFont xssfFont) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        if (null != xssfFont) {
            cellStyle.setFont(xssfFont);
        }
        return cellStyle;
    }

    private static CellStyle getCellStyle(Workbook workbook, Map<Integer, CellStyle> cellStyleMap, DataType format,
                                          BarcodeDuplicateEnum duplicated) {
        switch (format) {
            case FLOAT:
                switch (duplicated) {
                    case DUP:
                        return ExcelFormat.FLOAT_DUP.createCellStyle(workbook, cellStyleMap);
                    case CONFIRMED:
                        return ExcelFormat.FLOAT_CONFIRMED.createCellStyle(workbook, cellStyleMap);
                    default:
                        return ExcelFormat.FLOAT_NORMAL.createCellStyle(workbook, cellStyleMap);
                }
            case INTEGER:
                switch (duplicated) {
                    case DUP:
                        return ExcelFormat.INTEGER_DUP.createCellStyle(workbook, cellStyleMap);
                    case CONFIRMED:
                        return ExcelFormat.INTEGER_CONFIRMED.createCellStyle(workbook, cellStyleMap);
                    default:
                        return ExcelFormat.INTEGER_NORMAL.createCellStyle(workbook, cellStyleMap);
                }
            default:
                switch (duplicated) {
                    case DUP:
                        return ExcelFormat.STRING_DUP.createCellStyle(workbook, cellStyleMap);
                    case CONFIRMED:
                        return ExcelFormat.STRING_CONFIRMED.createCellStyle(workbook, cellStyleMap);
                    default:
                        return ExcelFormat.STRING_NORMAL.createCellStyle(workbook, cellStyleMap);
                }
        }
    }
}
