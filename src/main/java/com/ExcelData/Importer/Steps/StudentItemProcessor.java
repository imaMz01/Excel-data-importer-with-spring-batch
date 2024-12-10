package com.ExcelData.Importer.Steps;

import com.ExcelData.Importer.Entity.Student;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class StudentItemProcessor implements ItemProcessor<Student,Student> {
    @Override
    public Student process(Student item) throws Exception {
        if(item.getAverage()>=10)
            item.setAdmitted(true);
        return item;
    }
}
