package dev.arhimedes.excel.utils;

import dev.arhimedes.excel.utils.annotation.ExcelColumn;
import dev.arhimedes.excel.utils.exception.ExcelProcessingException;
import dev.arhimedes.excel.utils.processor.ExcelProcessor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelUtility<T> {

    public void write(List<T> tList, Class<T> clazz, ByteArrayOutputStream outputStream, String sheetName){
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(sheetName + LocalDate.now());

            // Create a map of column indices to fields
            Map<Integer, Field> columnIndexFieldMap = ExcelProcessor.columnNameMap(clazz);

            // styling to the cell
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            headerStyle.setShrinkToFit(true);
            headerStyle.setWrapText(false);

            Row headerRow = sheet.createRow(0);

            CellStyle style = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            style.setDataFormat(format.getFormat("0"));

            // Create header cells based on the ExcelColumn annotation
            for (Map.Entry<Integer, Field> entry : columnIndexFieldMap.entrySet()) {
                int cellIndex = entry.getKey();
                Field field = entry.getValue();
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);

                Cell headerRowCell = headerRow.createCell(cellIndex);
                headerRowCell.setCellValue(excelColumn.name());
                headerRowCell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(cellIndex);
            }

            int rowIndex = 1;

            for (T t : tList) {
                Row row = sheet.createRow(rowIndex++);
                for (Map.Entry<Integer, Field> entry : columnIndexFieldMap.entrySet()) {
                    int cellIndex = entry.getKey();
                    Field field = entry.getValue();

                    field.setAccessible(true);
                    Object value = field.get(t);
                    Cell cell = row.createCell(cellIndex);

                    if (value != null) {
                        String fieldType = field.getType().getSimpleName();
                        switch (fieldType) {
                            case "String" -> cell.setCellValue((String) value);

                            case "Double" -> cell.setCellValue((Double) value);

                            case "Long" -> {
                                cell.setCellValue((Long) value);
                                cell.setCellStyle(style);
                            }

                            case "Boolean" -> cell.setCellValue((Boolean) value);

                            case "Integer" -> cell.setCellValue((Integer) value);

                            case "LocalDate" -> cell.setCellValue(((LocalDate) value).toString());

                            case "LocalDateTime" -> cell.setCellValue(((LocalDateTime) value).toString());

                            default -> cell.setCellValue(value.toString());
                        }
                    }
                }
            }

            // Adjust the column widths to fit the content
            for (int i = 0; i < columnIndexFieldMap.size(); i++) {
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 512); // add padding
            }

            workbook.write(outputStream);

        }catch (Exception e){
            throw new ExcelProcessingException(e.getLocalizedMessage());
        }
    }

    public List<T> read(MultipartFile excelFile, Class<T> clazz) {
        List<T> resultList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(excelFile.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.getRow(0);

            for (Cell cell : headerRow) {
                int cellIndex = cell.getColumnIndex();
                String headerName = cell.getStringCellValue();
                System.out.println("Header index: " + cellIndex + ", Header name: " + headerName);
            }


            // Create a map of column indices to fields
            Map<Integer, Field> columnIndexFieldMap = ExcelProcessor.columnNameMap(clazz);


            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {

                Row row = sheet.getRow(rowIndex);
                T instance = clazz.getDeclaredConstructor().newInstance();

                for (Map.Entry<Integer, Field> entry: columnIndexFieldMap.entrySet()) {

                    int cellIndex = entry.getKey();
                    // Get the field directly from the map
                    Field field = entry.getValue();

                    Cell cell = row.getCell(cellIndex);

                    if (cell != null) {

                        field.setAccessible(true);
                        String fieldType = field.getType().getSimpleName();

                        switch (fieldType) {

                            case "String" -> field.set(instance, cell.getStringCellValue());

                            case "Double" -> field.set(instance, cell.getNumericCellValue());

                            case "Long" -> field.set(instance, (long) cell.getNumericCellValue());

                            case "Boolean" -> field.set(instance, cell.getBooleanCellValue());

                            case "Integer" -> field.set(instance, (int) cell.getNumericCellValue());

                            case "LocalDate" -> field.set(instance, LocalDate.parse(cell.getStringCellValue()));

                            case "LocalDateTime" -> field.set(instance, LocalDateTime.parse(cell.getStringCellValue()));

                            default -> field.set(instance, cell.toString());
                        }
                    }
                }
                resultList.add(instance);
            }
        } catch (Exception e) {
            throw new ExcelProcessingException(e.getLocalizedMessage());
        }

        return resultList;
    }

}
