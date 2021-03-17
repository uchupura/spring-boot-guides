package com.guide.excel.exception;

public class UnSupportedExcelTypeException extends ExcelException {
    public UnSupportedExcelTypeException(String message) {
        super(message, null);
    }
}
