package com.guide.excel.exception;

public class NoExcelColumnAnnotationsException extends ExcelException {
    public NoExcelColumnAnnotationsException(String message) {
        super(message, null);
    }
}
