package com.example.crudwithvaadin;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtils {

    static XSSFWorkbook workbook;
    static XSSFSheet sheet;

    private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    //pass file path and sheet number to get
    public ExcelUtils(String excelPath, int s){
        try {
            workbook = new XSSFWorkbook(excelPath);
            sheet = workbook.getSheet(workbook.getSheetName(s));
        } catch (Exception exp) {
            log.info("No file found");
            System.out.println(exp.getCause());
            System.out.println(exp.getMessage());
            exp.printStackTrace();
        }
    }

    // count number of columns
    public static int getColumnCount(){
        int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();
        log.info("No of columns: " + columnCount);
        return columnCount;
    }

    // count number of rows
    public static int getRowCount() {
        int rowCount = sheet.getPhysicalNumberOfRows();
        log.info("No of Rows: " + rowCount);
        return rowCount;
    }

    // get the sheet name
    public static String getSheetName(int sheetNo1){
        String s = workbook.getSheetName(sheetNo1);
        log.info("Sheet name: " + s);
        return s;
    }

    // get and format data of excel
    public static String getCellData(int rowNum, int cellNum){
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(sheet.getRow(rowNum).getCell(cellNum));
    }

    public static void main(String[] args) {
        String path = "C:/Users/geeri/Desktop/my-excel.xlsx";
        ExcelUtils excel1 = new ExcelUtils(path, 0);
        excel1.getRowCount();
        excel1.getColumnCount();
        excel1.getSheetName(0);
    }
}
