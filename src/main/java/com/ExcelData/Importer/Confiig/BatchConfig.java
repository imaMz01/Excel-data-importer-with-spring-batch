package com.ExcelData.Importer.Confiig;

import com.ExcelData.Importer.Entity.Student;
import com.ExcelData.Importer.Repository.StudentRepository;
import com.ExcelData.Importer.Steps.StudentItemProcessor;
import com.ExcelData.Importer.Steps.StudentItemReader;
import com.ExcelData.Importer.Steps.StudentItemWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final StudentRepository studentRepository;

    @Bean
    public Job importStudentJob(Step generateCertificatesStep) throws IOException {
        return new JobBuilder("fetchDataFromExcelFileJob", jobRepository)
                .start(importStudentStep())
                .build();
    }

    @Bean
    public Step importStudentStep() throws IOException {
        return new StepBuilder("fetchDataFromExcelFileStep", jobRepository)
                .<Student, Student>chunk(3, transactionManager)
                .reader(studentItemReader(null))
                .processor(studentItemProcessor())
                .writer(studentItemWriter())
                .build();
    }

    @Bean
    @StepScope  // Utilisation de StepScope pour permettre l'accès aux JobParameters
    public StudentItemReader studentItemReader(@Value("#{jobParameters['file']}") String filePath) throws IOException {
        return new StudentItemReader(filePath);
    }

    @Bean
    public ItemProcessor<Student, Student> studentItemProcessor() {
        return new StudentItemProcessor();
    }

    @Bean
    public ItemWriter<Student> studentItemWriter() {
        return new StudentItemWriter(studentRepository);
    }
}
