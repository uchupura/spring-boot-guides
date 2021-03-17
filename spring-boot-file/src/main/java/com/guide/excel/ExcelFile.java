package com.guide.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface ExcelFile<T> {
    void write(OutputStream stream) throws IOException;

    void addRows(List<T> rows);
}
