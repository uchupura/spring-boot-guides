package com.guide.excel;

import com.guide.excel.exception.ExcelInternalException;
import com.guide.excel.resource.*;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static com.guide.excel.utils.SuperClassReflectionUtils.getField;

public abstract class SXSSFExcelFile<T> implements ExcelFile<T> {
    protected static final SpreadsheetVersion supplyExcelVersion = SpreadsheetVersion.EXCEL2007;

    protected Sheet sheet;
    protected SXSSFWorkbook wb;
    protected ExcelRenderResource resource;

    public SXSSFExcelFile(Class<T> type) {
        this(Collections.emptyList(), type, new DefaultDataFormatDecider());
    }

    public SXSSFExcelFile(List<T> data, Class<T> type) {
        this(data, type, new DefaultDataFormatDecider());
    }

    public SXSSFExcelFile(List<T> data, Class<T> type, DataFormatDecider dataFormatDecider) {
        validateData(data);
        this.wb = new SXSSFWorkbook();
        this.resource = ExcelRenderResourceFactory.prepareRenderResource(type, wb, dataFormatDecider);
        renderExcel(data);
    }

    protected void validateData(List<T> data) { }

    protected abstract void renderExcel(List<T> data);

    protected void renderHeadersWithNewSheet(Sheet sheet, int rowIndex, int columnStartIndex) {
        Row row = sheet.createRow(rowIndex);
        int columnIndex = columnStartIndex;
        for (String dataFieldName : resource.getDataFieldNames()) {
            Cell cell = row.createCell(columnIndex++);
            cell.setCellStyle(resource.getCellStyle(dataFieldName, ExcelRenderLocation.HEADER));
            cell.setCellValue(resource.getExcelHeaderName(dataFieldName));
        }
    }

    protected void renderBody(Object data, int rowIndex, int columnStartIndex) {
        Row row = sheet.createRow(rowIndex);
        int columnIndex = columnStartIndex;
        for (String dataFieldName : resource.getDataFieldNames()) {
            Cell cell = row.createCell(columnIndex++);
            try {
                Field field = getField(data.getClass(), (dataFieldName));
                field.setAccessible(true);
                cell.setCellStyle(resource.getCellStyle(dataFieldName, ExcelRenderLocation.BODY));
                Object cellValue = field.get(data);
                renderCellValue(cell, cellValue);
            } catch (Exception e) {
                throw new ExcelInternalException(e.getMessage(), e);
            }
        }
    }

    private void renderCellValue(Cell cell, Object cellValue) {
        if (cellValue instanceof Number) {
            Number numberValue = (Number) cellValue;
            cell.setCellValue(numberValue.doubleValue());
            return;
        }
        cell.setCellValue(cellValue == null ? "" : cellValue.toString());
    }



    @Override
    public void write(OutputStream stream) throws IOException {
        wb.write(stream);
        wb.close();
        wb.dispose();
        stream.close();
    }
}
