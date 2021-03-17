package com.guide.excel.style.wrap;

import org.apache.poi.ss.usermodel.CellStyle;

public class NoExcelWrap implements ExcelWrap {
    @Override
    public void apply(CellStyle cellStyle) {
        // Do nothing
    }
}
