package com.guide.excel.model;

import com.guide.excel.annotations.DefaultBodyStyle;
import com.guide.excel.annotations.DefaultHeaderStyle;
import com.guide.excel.annotations.ExcelColumn;
import com.guide.excel.annotations.ExcelColumnStyle;
import com.guide.excel.style.custom.BlueBodyStyle;
import com.guide.excel.style.custom.GrayHeaderStyle;

//@DefaultHeaderStyle(style = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "GREY_HEADER"))
//@DefaultBodyStyle(style = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
@DefaultHeaderStyle(style = @ExcelColumnStyle(excelCellStyleClass = GrayHeaderStyle.class))
@DefaultBodyStyle(style = @ExcelColumnStyle(excelCellStyleClass = BlueBodyStyle.class))
public class Car {
    @ExcelColumn(
            headerName = "회사"
//            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class)
    )
    private String company;
    @ExcelColumn(
            headerName = "차종"
//            headerStyle = @ExcelColumnStyle(excelCellStyleClass = BlueHeaderStyle.class)
    )
    private String name;
    @ExcelColumn(
            headerName = "가격"
//            headerStyle = @ExcelColumnStyle(excelCellStyleClass = BlueHeaderStyle.class)
    )
    private int price;
    @ExcelColumn(
            headerName = "평점"
//            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class)
    )
    private double rating;

    public Car(String company, String name, int price, double rating) {
        this.company = company;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }
}
