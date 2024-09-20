package com.pooja.backend_college_directory_app.dto;

import lombok.Data;

@Data
public class StudentProfileDTO {

    private String studentName;
    private String year;
    private String email;
    private String phone;
    private String departmentName;
}
