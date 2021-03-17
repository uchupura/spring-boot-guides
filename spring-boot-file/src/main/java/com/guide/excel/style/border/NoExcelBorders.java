package com.guide.excel.style.border;

import org.apache.poi.ss.usermodel.CellStyle;

public class NoExcelBorders implements ExcelBorders {
    @Override
    public void apply(CellStyle cellStyle) {
        // Do nothing
    }
}
