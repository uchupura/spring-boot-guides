package com.guide.excel.style.configure;

import com.guide.excel.style.ExcelCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;

public abstract class CustomExcelCellStyle implements ExcelCellStyle {

    private ExcelCellStyleConfigure configurer = new ExcelCellStyleConfigure();

    public CustomExcelCellStyle() {
        configure(configurer);
    }

    public abstract void configure(ExcelCellStyleConfigure configurer);

    @Override
    public void apply(CellStyle cellStyle) {
        configurer.configure(cellStyle);
    }
}
