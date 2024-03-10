package dev.arhimedes.excel.utils.processor;

import dev.arhimedes.excel.utils.annotation.ExcelColumn;
import dev.arhimedes.excel.utils.annotation.ExcelSheet;
import io.micrometer.common.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelProcessor {

    public static List<String> columnNames(Class<?> clazz){
        if(clazz.isAnnotationPresent(ExcelSheet.class)){
            List<String> columns = new ArrayList<>();

            Field[] fields = clazz.getDeclaredFields();

            for (Field field: fields){
                if(field.isAnnotationPresent(ExcelColumn.class)){
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if(StringUtils.isNotBlank(annotation.name())){
                        columns.add(annotation.name());
                    }
                    else {
                        columns.add(field.getName().toUpperCase());
                    }
                }
            }
            return columns;
        }
        throw new RuntimeException("Not a worksheet");
    }

    public static Map<Integer, Field> columnNameMap(Class<?> clazz){
        if(clazz.isAnnotationPresent(ExcelSheet.class)){

            Map<Integer, Field> map = new HashMap<>();

            Field[] fields = clazz.getDeclaredFields();

            for (Field field: fields){
                if (field.isAnnotationPresent(ExcelColumn.class)){
                    ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                    map.put(excelColumn.cellNumber(), field);
                }
            }

            return map;

        }
        throw new RuntimeException("No such excel sheet");
    }

}
