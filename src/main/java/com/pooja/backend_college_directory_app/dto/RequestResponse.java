package com.pooja.backend_college_directory_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pooja.backend_college_directory_app.model.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestResponse {
    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String phone;
    private Role role;
    private String username;
    private String email;
    private String password;
    private User user;
    private List<User> userList;
    private FacultyProfile facultyProfile;
    private FacultyProfileDTO facultyProfileDTO;
    private StudentProfile studentProfile;
    private Department department;
    private DepartmentDTO departmentDTO;
    private Set<StudentProfile> studentProfileSet;
    private List<DepartmentDTO> list;
    private List<FacultyProfileDTO> facultyProfileList;

}
