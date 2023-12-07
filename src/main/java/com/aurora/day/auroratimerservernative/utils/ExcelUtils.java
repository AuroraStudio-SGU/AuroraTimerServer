package com.aurora.day.auroratimerservernative.utils;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {

    private static final Log logger = LogFactory.get(ExcelUtils.class);

    public static List<List<Object>> readAll(File excelFile){
        List<List<Object>> result = new ArrayList<>();
        try(InputStream is = new FileInputStream(excelFile);){
            HSSFWorkbook workbook = new HSSFWorkbook(is);
            //默认获取表1
            HSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                List<Object> rowList = new ArrayList<>();
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING -> rowList.add(cell.getStringCellValue());
                        case NUMERIC -> rowList.add(cell.getNumericCellValue());
                        case BOOLEAN -> rowList.add(cell.getBooleanCellValue());
                        case FORMULA -> rowList.add(cell.getCellFormula());
                    }
                }
                result.add(rowList);
            }
        }catch (Exception e){
            logger.error("Excel操作失败:{}",e);
        }
        return result;
    }
}
