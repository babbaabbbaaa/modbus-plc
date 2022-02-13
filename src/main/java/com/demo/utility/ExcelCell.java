package com.demo.utility;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @author Rocker Chen
 * Use annotation to replace this common config later
 */
@Getter
@Setter
@Slf4j
public class ExcelCell {

    private static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String header;
    private final int columnWidth;
    private Method invokeMethod;
    private final String methodName;
    private final DataType format;
    private int xOffset = 0;
    private int yOffset = 1;
    private boolean merged = false;

    public ExcelCell(String header, int columnWidth, String methodName, Class<?> type, DataType format) {
        this.header = header;
        this.columnWidth = columnWidth;
        this.methodName = methodName;
        this.format = format;
        try {
            this.invokeMethod = getInvokeMethod(type);
        } catch (NoSuchMethodException e) {
            log.error("Failed: ", e);
        }
    }

    public ExcelCell(String header, int columnWidth, String methodName, Class<?> type, DataType format, int xOffset, int yOffset) {
        this(header, columnWidth, methodName, type, format);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.merged = true;
    }

    public void createHeader(Row row, CellStyle cellStyle, int index) {
        Cell cell = row.createCell(index);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(header);
        cell.getSheet().setColumnWidth(index, columnWidth);
    }


    public void createCell(Row row, CellStyle cellStyle, Object value, int index) {
        Cell cell = row.createCell(index);
        cell.setCellStyle(cellStyle);
        if (merged) {
            int rowNum = row.getRowNum();
            int colNum = cell.getColumnIndex();
            CellRangeAddress range = new CellRangeAddress(rowNum, rowNum + yOffset, colNum, colNum + xOffset);
            row.getSheet().addMergedRegion(range);
            range.forEach(cellAddress -> row.getSheet().getRow(cellAddress.getRow()).getCell(cellAddress.getColumn(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
                    .setCellStyle(cellStyle));
        }
        if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else if (value instanceof Float) {
            cell.setCellValue(((Float) value).doubleValue());
        } else if (value instanceof LocalDateTime) {
            cell.setCellValue(((LocalDateTime) value).format(YYYY_MM_DD_HH_MM_SS));
        } else if (value instanceof LocalDate) {
            cell.setCellValue(((LocalDate) value).format(DateTimeFormatter.ISO_LOCAL_DATE));
        } else if (value instanceof Boolean) {
            cell.setCellValue(((boolean) value) ? "Yes" : "No");
        } else {
            cell.setCellValue(value == null ? "" : value.toString());
        }

    }

    private Method getInvokeMethod(Class<?> clazz) throws NoSuchMethodException {
        if (null == invokeMethod) {
            this.invokeMethod = clazz.getDeclaredMethod(methodName);
        }
        return invokeMethod;
    }


}
