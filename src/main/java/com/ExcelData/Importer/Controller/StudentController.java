package com.ExcelData.Importer.Controller;


import com.ExcelData.Importer.Dto.StudentDto;
import com.ExcelData.Importer.Service.ServiceStudent;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public String importStudents(@RequestParam("file") MultipartFile file) throws Exception {

        String tempFilePath = saveTempFile(file);

        // Create a JobParameters with the file path
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("file", tempFilePath)  // Passer le chemin du fichier comme paramètre
                .toJobParameters();

        // Launch the Spring Batch job
        jobLauncher.run(importStudentJob, jobParameters);
        Files.deleteIfExists(Paths.get(tempFilePath));  // Supprimer le fichier temporaire
        System.out.println("Fichier temporaire supprimé : " + tempFilePath);
        return "Job started successfully!";
    }

    private String saveTempFile(MultipartFile file) throws IOException {
        // Save the file to disk to pass it to Spring Batch
        Path tempFile = Files.createTempFile("import-", ".xlsx");
        System.out.println("path ::"+tempFile );
        file.transferTo(tempFile.toFile());
        return tempFile.toString();
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
