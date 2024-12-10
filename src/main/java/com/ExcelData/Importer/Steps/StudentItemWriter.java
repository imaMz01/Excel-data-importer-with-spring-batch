package com.ExcelData.Importer.Steps;

import com.ExcelData.Importer.Entity.Student;
import com.ExcelData.Importer.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@RequiredArgsConstructor
public class StudentItemWriter implements ItemWriter<Student> {

    private final StudentRepository studentRepository;
    @Override
    public void write(Chunk<? extends Student> chunks) throws Exception {
        for (Student chunk : chunks) {
            studentRepository.save(chunk);
        }
    }
}
