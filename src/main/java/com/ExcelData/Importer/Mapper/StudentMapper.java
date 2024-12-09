package com.ExcelData.Importer.Mapper;

import com.ExcelData.Importer.Dto.StudentDto;
import com.ExcelData.Importer.Entity.Student;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDto toDto(Student user);
    Student toEntity(StudentDto userDto);
    List<StudentDto> toDto(List<Student> user);
    List<Student> toEntity(List<StudentDto> userDto);
}
