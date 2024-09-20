package com.pooja.backend_college_directory_app.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
public class FacultyProfileDTO {
    private String facultyName;
    private String officeHours;
    private String email;
    private String phone;
    private String departmentName;

}
