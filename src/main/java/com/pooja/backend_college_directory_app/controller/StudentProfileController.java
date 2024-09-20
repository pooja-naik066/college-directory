package com.pooja.backend_college_directory_app.controller;

import com.pooja.backend_college_directory_app.dto.FacultyProfileDTO;
import com.pooja.backend_college_directory_app.dto.StudentProfileDTO;
import com.pooja.backend_college_directory_app.model.StudentProfile;
import com.pooja.backend_college_directory_app.service.StudentProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentProfileController {

    @Autowired
    private StudentProfileService studentProfileService;

    @PostMapping("/create-profile")
    public ResponseEntity<StudentProfileDTO> createStudentProfile(@RequestBody StudentProfile studentProfile) {
        StudentProfileDTO savedProfile = studentProfileService.saveStudentProfile(studentProfile);
        System.out.println("Added student profile "+studentProfile);
        return new ResponseEntity<StudentProfileDTO>(savedProfile, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<StudentProfileDTO> updateStudentProfile(@RequestBody StudentProfile studentProfile){
        return new ResponseEntity<StudentProfileDTO>(studentProfileService.updateStudentProfile(studentProfile),
                HttpStatus.OK);
    }

    @GetMapping("/view-profile")
    public ResponseEntity<StudentProfileDTO> viewStudentProfile(){
        return new ResponseEntity<StudentProfileDTO>(studentProfileService.viewProfile(),
                HttpStatus.OK);
    }

    @GetMapping("/view-faculty")
    public ResponseEntity<List<FacultyProfileDTO>> findFacultiesByLoggedInStudentDepartment(){
       return new ResponseEntity<List<FacultyProfileDTO>>(studentProfileService.findFacultiesByLoggedInStudentDepartment()
               , HttpStatus.OK);
    }

}
