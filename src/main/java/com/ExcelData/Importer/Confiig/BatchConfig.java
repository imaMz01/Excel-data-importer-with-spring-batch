package com.ExcelData.Importer.Confiig;

import com.ExcelData.Importer.Dto.StudentDto;
import com.ExcelData.Importer.Entity.Student;
import com.ExcelData.Importer.Repository.StudentRepository;
import com.ExcelData.Importer.Steps.StudentItemProcessor;
import com.ExcelData.Importer.Steps.StudentItemReader;
import com.ExcelData.Importer.Steps.StudentItemWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final StudentRepository studentRepository;

    @Bean
    public Job importStudentJob(Step generateCertificatesStep) throws IOException {
        return new JobBuilder("generateCertificatesJob", jobRepository)
                .start(importStudentStep())
                .build();
    }

    @Bean
    public Step importStudentStep() throws IOException {
        return new StepBuilder("generateCertificatesStep", jobRepository)
                .<List<Student>, List<Student>>chunk(1, transactionManager)
                .reader(studentItemReader(null))
                .processor(studentItemProcessor())
                .writer(studentItemWriter())
                .build();
    }

    @Bean
    public StudentItemReader studentItemReader(MultipartFile file) throws IOException {
        return new StudentItemReader(file);
    }

    @Bean
    public ItemProcessor<List<Student>, List<Student>> studentItemProcessor() {
        return new StudentItemProcessor(); // Si vous avez un ItemProcessor
    }

    @Bean
    public ItemWriter<List<Student>> studentItemWriter() {
        return new StudentItemWriter(studentRepository); // Votre writer pour persister les étudiants dans la base de données
    }
}
