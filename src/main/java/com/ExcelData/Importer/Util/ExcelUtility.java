package com.ExcelData.Importer.Util;

import com.ExcelData.Importer.Entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ExcelUtility {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static String SHEET = "student";

    public static boolean hasExcelFormat(MultipartFile file){
        return TYPE.equals(file.getContentType());
    }

    public static List<Student> excelToList(InputStream inputStream) throws IOException {
        log.info("data : {}",inputStream);
        Workbook workbook = new XSSFWorkbook(inputStream);
        log.info("workbook {}",workbook);
        log.info("number of sheet : {}",workbook.getNumberOfSheets());
        Sheet sheet = workbook.getSheet(SHEET);
        List<Student> students = new ArrayList<>();
        for(Row row : sheet){
            if(row.getRowNum() == 0) continue;
            if(row.getCell(0)!=null && row.getCell(1)!=null)
                if(!row.getCell(0).getStringCellValue().isEmpty() && !row.getCell(1).getStringCellValue().isEmpty()
                && row.getCell(5).getNumericCellValue()>=0 && row.getCell(5).getNumericCellValue()<=20)
                    students.add(new Student(UUID.randomUUID().toString(),row.getCell(0).getStringCellValue(),
                        row.getCell(1).getStringCellValue(),row.getCell(2).getStringCellValue()
                        ,row.getCell(3).getStringCellValue(),"0"+String.valueOf((long)row.getCell(4).getNumericCellValue())
                        ,row.getCell(5).getNumericCellValue(),false));


        }
        workbook.close();
        return students;
    }
}

