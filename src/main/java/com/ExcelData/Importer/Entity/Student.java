package com.ExcelData.Importer.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String tel;
    private double average;
    private boolean admitted;
}
