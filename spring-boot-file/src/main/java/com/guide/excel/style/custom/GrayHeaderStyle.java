package com.guide.excel.style.custom;

import com.guide.excel.style.align.DefaultExcelAlign;
import com.guide.excel.style.border.DefaultExcelBorders;
import com.guide.excel.style.border.ExcelBorderStyle;
import com.guide.excel.style.configure.ExcelCellStyleConfigure;
import com.guide.excel.style.configure.CustomExcelCellStyle;
import com.guide.excel.style.wrap.DefaultExcelWrap;

public class GrayHeaderStyle extends CustomExcelCellStyle {
    @Override
    public void configure(ExcelCellStyleConfigure configurer) {
        configurer.color(217, 217, 217);
        configurer.align(DefaultExcelAlign.CENTER_CENTER);
        configurer.borders(DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN));
        configurer.wrap(DefaultExcelWrap.WRAP_TEXT);
    }
}
