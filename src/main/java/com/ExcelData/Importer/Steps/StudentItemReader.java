package com.ExcelData.Importer.Steps;

import com.ExcelData.Importer.Entity.Student;
import com.ExcelData.Importer.Util.ExcelUtility;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StudentItemReader implements ItemReader<List<Student>> {

    private InputStream inputStream;

    public StudentItemReader(MultipartFile file) throws IOException {
        if(ExcelUtility.hasExcelFormat(file)){
            inputStream= file.getInputStream();
        }

    }

    @Override
    public List<Student> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return ExcelUtility.excelToList(inputStream);
    }
}
