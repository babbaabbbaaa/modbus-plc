package com.demo.utility;

import com.demo.domain.casting.DieCasting;
import com.demo.domain.casting.SubDieCasting;
import com.demo.domain.stamping.Stamping;
import com.demo.plc.IPLCData;
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
    public static byte[] writeBean(List<? extends IPLCData> rows, List<ExcelCell> headerList) throws Exception {
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
        i = rows.size();
        int rowNo = 1;
        if (!CollectionUtils.isEmpty(rows)) {
            for (IPLCData row : rows) {
                row.setIndex(i--);
                if (row instanceof Stamping) {
                    int j = 0;
                    SXSSFRow body = sheet.createRow(rowNo);
                    for (ExcelCell item : headerList) {
                        item.createCell(body, j++, cellStyleMap, row);
                    }
                    rowNo++;
                } else if (row instanceof DieCasting) {
                    List<SubDieCasting> subDieCastings = ((DieCasting) row).getSubDieCastings();
                    int rowIndex = rowNo;
                    for (SubDieCasting subDieCasting : subDieCastings) {
                        SXSSFRow body = sheet.createRow(rowIndex++);
                        for (int headIndex = 4; headIndex < headerList.size(); headIndex++) {
                            ExcelCell item = headerList.get(headIndex);
                            item.createCell(body, headIndex, cellStyleMap, subDieCasting);
                        }
                    }
                    for (int headIndex = 0; headIndex < 4; headIndex++) {
                        ExcelCell item = headerList.get(headIndex);
                        SXSSFRow body = sheet.getRow(rowNo);
                        item.createCell(body, headIndex, cellStyleMap, row);
                    }
                    rowNo = rowIndex;
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
}
