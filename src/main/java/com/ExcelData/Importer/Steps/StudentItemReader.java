package com.ExcelData.Importer.Steps;

import com.ExcelData.Importer.Entity.Student;
import com.ExcelData.Importer.Util.ExcelUtility;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class StudentItemReader implements ItemReader<Student> {

    private final String filePath;
    private Iterator<Student> studentIterator;
    private List<Student> students;

    public StudentItemReader(@Value("#{jobParameters['file']}") String filePath) throws IOException {
        this.filePath = filePath;
    }

    @Override
    public Student read() throws Exception {
        if (students == null) {
            try (FileInputStream fis = new FileInputStream(filePath)) {
                students = ExcelUtility.excelToList(fis);
                studentIterator = students.iterator();
            } catch (IOException e) {
                throw new Exception("Error reading Excel file", e);
            }
        }
        return studentIterator.hasNext() ? studentIterator.next() : null;
    }
}
