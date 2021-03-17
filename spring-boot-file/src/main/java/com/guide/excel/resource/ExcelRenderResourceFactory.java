package com.guide.excel.resource;

import com.guide.excel.annotations.DefaultBodyStyle;
import com.guide.excel.annotations.DefaultHeaderStyle;
import com.guide.excel.annotations.ExcelColumn;
import com.guide.excel.annotations.ExcelColumnStyle;
import com.guide.excel.exception.InvalidExcelCellStyleException;
import com.guide.excel.exception.NoExcelColumnAnnotationsException;
import com.guide.excel.resource.collection.PreCalculatedCellStyleMap;
import com.guide.excel.style.ExcelCellStyle;
import com.guide.excel.style.NoExcelCellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.guide.excel.utils.SuperClassReflectionUtils.getAllFields;
import static com.guide.excel.utils.SuperClassReflectionUtils.getAnnotation;

public class ExcelRenderResourceFactory {
    public static ExcelRenderResource prepareRenderResource(Class<?> type, Workbook wb,
                                                            DataFormatDecider dataFormatDecider) {
        PreCalculatedCellStyleMap styleMap = new PreCalculatedCellStyleMap(dataFormatDecider);
        Map<String, String> headerNamesMap = new LinkedHashMap<>();
        List<String> fieldNames = new ArrayList<>();

        ExcelColumnStyle classDefinedHeaderStyle = getHeaderExcelColumnStyle(type);
        ExcelColumnStyle classDefinedBodyStyle = getBodyExcelColumnStyle(type);

        for (Field field : getAllFields(type)) {
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                styleMap.put(
                        String.class,
                        ExcelCellKey.of(field.getName(), ExcelRenderLocation.HEADER),
                        getCellStyle(decideAppliedStyleAnnotation(classDefinedHeaderStyle, annotation.headerStyle())), wb);
                Class<?> fieldType = field.getType();
                styleMap.put(
                        fieldType,
                        ExcelCellKey.of(field.getName(), ExcelRenderLocation.BODY),
                        getCellStyle(decideAppliedStyleAnnotation(classDefinedBodyStyle, annotation.bodyStyle())), wb);
                fieldNames.add(field.getName());
                headerNamesMap.put(field.getName(), annotation.headerName());
            }
        }

        if (styleMap.isEmpty()) {
            throw new NoExcelColumnAnnotationsException(String.format("Class %s has not @ExcelColumn at all", type));
        }
        return new ExcelRenderResource(styleMap, headerNamesMap, fieldNames);
    }

    private static ExcelColumnStyle getHeaderExcelColumnStyle(Class<?> clazz) {
        Annotation annotation = getAnnotation(clazz, DefaultHeaderStyle.class);
        if (annotation == null) {
            return null;
        }
        return ((DefaultHeaderStyle) annotation).style();
    }

    private static ExcelColumnStyle getBodyExcelColumnStyle(Class<?> clazz) {
        Annotation annotation = getAnnotation(clazz, DefaultBodyStyle.class);
        if (annotation == null) {
            return null;
        }
        return ((DefaultBodyStyle) annotation).style();
    }

    private static ExcelColumnStyle decideAppliedStyleAnnotation(ExcelColumnStyle classAnnotation,
                                                                 ExcelColumnStyle fieldAnnotation) {
        if (fieldAnnotation.excelCellStyleClass().equals(NoExcelCellStyle.class) && classAnnotation != null) {
            return classAnnotation;
        }
        return fieldAnnotation;
    }

    private static ExcelCellStyle getCellStyle(ExcelColumnStyle excelColumnStyle) {
        Class<? extends ExcelCellStyle> excelCellStyleClass = excelColumnStyle.excelCellStyleClass();
        // 1. Case of Enum
        if (excelCellStyleClass.isEnum()) {
            String enumName = excelColumnStyle.enumName();
            return findExcelCellStyle(excelCellStyleClass, enumName);
        }

        // 2. Case of Class
        try {
            return excelCellStyleClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InvalidExcelCellStyleException(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private static ExcelCellStyle findExcelCellStyle(Class<?> excelCellStyles, String enumName) {
        try {
            return (ExcelCellStyle) Enum.valueOf((Class<Enum>) excelCellStyles, enumName);
        } catch (NullPointerException e) {
            throw new InvalidExcelCellStyleException("enumName must not be null", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidExcelCellStyleException(
                    String.format("Enum %s does not name %s", excelCellStyles.getName(), enumName), e);
        }
    }
}
