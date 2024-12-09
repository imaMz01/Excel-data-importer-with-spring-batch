package com.ExcelData.Importer.Controller;


import com.ExcelData.Importer.Dto.StudentDto;
import com.ExcelData.Importer.Service.ServiceStudent;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final ServiceStudent serviceStudent;
    private final JobLauncher jobLauncher;
    private final Job importStudentJob;

    @PostMapping("/upload")
    public ResponseEntity<String> importData(@RequestParam(name = "file")MultipartFile file) throws IOException {
        return new ResponseEntity<>(serviceStudent.addAll(file), HttpStatus.OK);
    }

    @PostMapping("/import")
    public String importStudents(@RequestParam("file") MultipartFile file) {
        try {
            // Vous pouvez créer un JobParameters pour fournir le fichier à votre Job
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("file", file.getOriginalFilename())
                    .toJobParameters();

            jobLauncher.run(importStudentJob, jobParameters);

            return "Job started successfully!";
        } catch (Exception e) {
            return "Error starting job: " + e.getMessage();
        }
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> all(){
        return new ResponseEntity<>(serviceStudent.all(),HttpStatus.OK);
    }

    @GetMapping("/generatePdf")
    public ResponseEntity<InputStreamResource> generatePdf() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = serviceStudent.generateStudentPdf();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "inline; filename=students_list.pdf");
//        headers.add("Content-Type", MediaType.APPLICATION_PDF_VALUE);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=students_list.pdf");
        headers.add("Content-Type", "application/pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
    }
}
