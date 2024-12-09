package com.ExcelData.Importer.Service;

import com.ExcelData.Importer.Dto.StudentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface ServiceStudent {

    String addAll(MultipartFile file) throws IOException;
    List<StudentDto> all();
    ByteArrayOutputStream generateStudentPdf() throws Exception;

}
