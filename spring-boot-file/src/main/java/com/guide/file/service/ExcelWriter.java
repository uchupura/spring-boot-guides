package com.guide.file.service;

import com.guide.file.common.ExcelConstant;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.Builder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class ExcelWriter {

    private Sheet sheet;
    private Workbook workbook;
    private Map<String, Object> model;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Builder
    public ExcelWriter(Workbook workbook, Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        this.workbook = workbook;
        this.model = model;
        this.request = request;
        this.response = response;
    }

    public void create() throws Exception {
        applyFileNameForRequest(mapToFileName());

        applyContentTypeForRequest();

        sheet = workbook.createSheet();

        createHead(mapToHeadList());
    }

    /**
     * 다운로드 할 파일 이름 설정
     */
    private void applyFileNameForRequest(String fileName) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String encodedFileName = FileNameEncoder.encode(userAgent.getBrowser().getGroup(), fileName);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + appendFileExtension(encodedFileName) + "\"");
    }
    private String mapToFileName() {
        return (String) model.get(ExcelConstant.FILE_NAME);
    }
    private String appendFileExtension(String fileName) {
        if (workbook instanceof XSSFWorkbook || workbook instanceof SXSSFWorkbook) {
            fileName += ".xlsx";
        }
        if (workbook instanceof HSSFWorkbook) {
            fileName += ".xls";
        }

        return fileName;
    }

    /**
     * Content-Type 설정
     */
    private void applyContentTypeForRequest() {
        if (workbook instanceof XSSFWorkbook || workbook instanceof SXSSFWorkbook) {
            response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        }
        if (workbook instanceof HSSFWorkbook) {
            response.setHeader("Content-Type", "application/vnd.ms-excel");
        }
    }

    /**
     * 엑셀 파일 컬럼 설정
     */
    private void createHead(List<String> headList) {
        createRow(headList, 0);
    }
    private List<String> mapToHeadList() {
        return (List<String>) model.get(ExcelConstant.HEAD);
    }

    /**
     * 엑셀 ROW 추가
     */
    int rowNum = 1;
    public void createBody(List<String> body) throws Exception {
        createRow(body, rowNum++);
        if (workbook instanceof SXSSFWorkbook && rowNum % ExcelConstant.WINDOW_SIZE == 0) {
            ((SXSSFSheet)sheet).flushRows(ExcelConstant.WINDOW_SIZE);
        }
    }
    public void createBodyList(List<List<String>> bodyList) throws Exception {
        int rowSize = bodyList.size();
        for (int i = 0; i < rowSize; i++) {
            createBody(bodyList.get(i));
        }
    }

    public void close() throws Exception {
        workbook.write(response.getOutputStream());
        if(workbook instanceof SXSSFWorkbook)
            ((SXSSFWorkbook)workbook).dispose();
    }

    private void createRow(List<String> cellList, int rowNum) {
        int size = cellList.size();
        Row row = sheet.createRow(rowNum);

        for (int i = 0; i < size; i++) {
            row.createCell(i).setCellValue(cellList.get(i));
        }
    }

    private enum FileNameEncoder {
        IE(Browser.IE, it -> {
            try {
                return URLEncoder.encode(it, StandardCharsets.UTF_8.name()).replaceAll("\\+", "%20");
            } catch (UnsupportedEncodingException e) {
                return it;
            }
        }),
        FIREFOX(Browser.FIREFOX, getDefaultEncodeOperator()),
        OPERA(Browser.OPERA, getDefaultEncodeOperator()),
        CHROME(Browser.CHROME, getDefaultEncodeOperator()),
        UNKNOWN(Browser.UNKNOWN, UnaryOperator.identity());

        private final Browser browser;
        private UnaryOperator<String> encodeOperator;

        private static final Map<Browser, Function<String, String>> FILE_NAME_ENCODER_MAP;

        static {
            FILE_NAME_ENCODER_MAP = EnumSet.allOf(FileNameEncoder.class).stream()
                    .collect(Collectors.toMap(FileNameEncoder::getBrowser, FileNameEncoder::getEncodeOperator));
        }

        FileNameEncoder(Browser browser, UnaryOperator<String> encodeOperator) {
            this.browser = browser;
            this.encodeOperator = encodeOperator;
        }

        protected Browser getBrowser() {
            return browser;
        }

        protected UnaryOperator<String> getEncodeOperator() {
            return encodeOperator;
        }

        private static UnaryOperator<String> getDefaultEncodeOperator() {
            return it -> new String(it.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }

        public static String encode(Browser browser, String fileName) {
            return FILE_NAME_ENCODER_MAP.get(browser).apply(fileName);
        }
    }
}
