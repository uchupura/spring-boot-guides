package com.guide.excel.style;

import com.guide.excel.style.ExcelCellStyle;
import com.guide.excel.style.align.DefaultExcelAlign;
import com.guide.excel.style.align.ExcelAlign;
import com.guide.excel.style.border.DefaultExcelBorders;
import com.guide.excel.style.border.ExcelBorderStyle;
import com.guide.excel.style.color.DefaultExcelColor;
import com.guide.excel.style.color.ExcelColor;
import com.guide.excel.style.wrap.DefaultExcelWrap;
import com.guide.excel.style.wrap.ExcelWrap;
import org.apache.poi.ss.usermodel.CellStyle;

public enum DefaultExcelCellStyle implements ExcelCellStyle {

    GREY_HEADER(DefaultExcelColor.rgb(217, 217, 217),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.CENTER_CENTER, DefaultExcelWrap.WRAP_TEXT),
    BLUE_HEADER(DefaultExcelColor.rgb(223, 235, 246),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.CENTER_CENTER, DefaultExcelWrap.WRAP_TEXT),
    BODY(DefaultExcelColor.rgb(255, 255, 255),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.RIGHT_CENTER, DefaultExcelWrap.WRAP_TEXT);

    private final ExcelColor backgroundColor;
    /**
     * like CSS margin or padding rule,
     * List<DefaultExcelBorder> represents rgb TOP RIGHT BOTTOM LEFT
     */
    private final DefaultExcelBorders borders;
    private final ExcelAlign align;
    private final ExcelWrap wrap;

    DefaultExcelCellStyle(ExcelColor backgroundColor, DefaultExcelBorders borders, ExcelAlign align, ExcelWrap wrap) {
        this.backgroundColor = backgroundColor;
        this.borders = borders;
        this.align = align;
        this.wrap = wrap;
    }

    @Override
    public void apply(CellStyle cellStyle) {
        backgroundColor.applyForeground(cellStyle);
        borders.apply(cellStyle);
        align.apply(cellStyle);
        wrap.apply(cellStyle);
    }

}
