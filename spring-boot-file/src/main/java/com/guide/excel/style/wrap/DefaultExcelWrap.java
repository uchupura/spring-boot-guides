package com.guide.excel.style.wrap;

import org.apache.poi.ss.usermodel.CellStyle;

public enum DefaultExcelWrap implements ExcelWrap {
    WRAP_TEXT(Boolean.TRUE),
    NO_WRAP_TEXT(Boolean.FALSE);

    private final Boolean wrap;

    DefaultExcelWrap(Boolean wrap) {
        this.wrap = wrap;
    }
    @Override
    public void apply(CellStyle cellStyle) {
        cellStyle.setWrapText(wrap);
    }
}
