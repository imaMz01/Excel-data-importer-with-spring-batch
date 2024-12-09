package com.ExcelData.Importer.Service;

import com.ExcelData.Importer.Dto.StudentDto;
import com.ExcelData.Importer.Mapper.StudentMapper;
import com.ExcelData.Importer.Repository.StudentRepository;
import com.ExcelData.Importer.Util.ExcelUtility;
import com.ExcelData.Importer.Util.PdfGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceStudentImp implements ServiceStudent{

    private final StudentRepository studentRepository;
    private final StudentMapper mapper;

    @Override
    public String addAll(MultipartFile file) throws IOException {
        if(ExcelUtility.hasExcelFormat(file)){
            studentRepository.saveAll(ExcelUtility.excelToList(file.getInputStream()));
            return "the file is imported successfully";
        }
        return "Please upload an excel file !!!";
    }

    @Override
    public List<StudentDto> all() {
        return mapper.toDto(studentRepository.findAll());
    }

    @Override
    public ByteArrayOutputStream generateStudentPdf() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfGenerator.generatePdf(studentRepository.findAll(),outputStream);
        return outputStream;
    }
}
