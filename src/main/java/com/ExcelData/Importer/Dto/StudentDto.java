package com.ExcelData.Importer.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String tel;
    private double average;

}
