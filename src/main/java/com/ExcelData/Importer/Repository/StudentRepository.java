package com.ExcelData.Importer.Repository;

import com.ExcelData.Importer.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,String> {
}
