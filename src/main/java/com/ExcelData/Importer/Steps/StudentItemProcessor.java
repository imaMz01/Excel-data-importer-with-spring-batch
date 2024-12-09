package com.ExcelData.Importer.Steps;

import com.ExcelData.Importer.Entity.Student;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class StudentItemProcessor implements ItemProcessor<List<Student>,List<Student>> {
    @Override
    public List<Student> process(List<Student> item) throws Exception {
        item.forEach(
                student -> {
                    if(student.getAverage()>=10)
                        student.setAdmitted(true);
                }
        );
        return item;
    }
}
