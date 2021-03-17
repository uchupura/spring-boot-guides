package com.guide.excel.annotations;

import com.guide.excel.style.ExcelCellStyle;

public @interface ExcelColumnStyle {
    Class<? extends ExcelCellStyle> excelCellStyleClass();

    String enumName() default "";
}
