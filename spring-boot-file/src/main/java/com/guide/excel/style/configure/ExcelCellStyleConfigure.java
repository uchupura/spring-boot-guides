package com.guide.excel.style.configure;

import com.guide.excel.style.align.ExcelAlign;
import com.guide.excel.style.align.NoExcelAlign;
import com.guide.excel.style.border.ExcelBorders;
import com.guide.excel.style.border.NoExcelBorders;
import com.guide.excel.style.color.DefaultExcelColor;
import com.guide.excel.style.color.ExcelColor;
import com.guide.excel.style.color.NoExcelColor;
import com.guide.excel.style.wrap.ExcelWrap;
import com.guide.excel.style.wrap.NoExcelWrap;
import org.apache.poi.ss.usermodel.CellStyle;

public class ExcelCellStyleConfigure {
    private ExcelAlign excelAlign = new NoExcelAlign();
    private ExcelColor excelColor = new NoExcelColor();
    private ExcelBorders excelBorders = new NoExcelBorders();
    private ExcelWrap excelWrap = new NoExcelWrap();

    public ExcelCellStyleConfigure() {

    }

    public ExcelCellStyleConfigure align(ExcelAlign excelAlign) {
        this.excelAlign = excelAlign;
        return this;
    }

    public ExcelCellStyleConfigure color(int red, int blue, int green) {
        this.excelColor = DefaultExcelColor.rgb(red, blue, green);
        return this;
    }

    public ExcelCellStyleConfigure borders(ExcelBorders excelBorders) {
        this.excelBorders = excelBorders;
        return this;
    }

    public ExcelCellStyleConfigure wrap(ExcelWrap excelWrap) {
        this.excelWrap = excelWrap;
        return this;
    }

    public void configure(CellStyle cellStyle) {
        excelAlign.apply(cellStyle);
        excelColor.applyForeground(cellStyle);
        excelBorders.apply(cellStyle);
        excelWrap.apply(cellStyle);
    }
}
