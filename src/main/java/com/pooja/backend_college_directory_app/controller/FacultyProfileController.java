package com.pooja.backend_college_directory_app.controller;

import com.pooja.backend_college_directory_app.dto.FacultyProfileDTO;
import com.pooja.backend_college_directory_app.dto.RequestResponse;
import com.pooja.backend_college_directory_app.dto.StudentProfileDTO;
import com.pooja.backend_college_directory_app.model.FacultyProfile;
import com.pooja.backend_college_directory_app.model.StudentProfile;
import com.pooja.backend_college_directory_app.repository.UserRepository;
import com.pooja.backend_college_directory_app.service.FacultyProfileService;
import com.pooja.backend_college_directory_app.service.StudentProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyProfileController {

    @Autowired
    private FacultyProfileService facultyProfileService;

    @PostMapping("/create-profile")
    public ResponseEntity<FacultyProfileDTO> createStudentProfile(@RequestBody FacultyProfile facultyProfile) {
        FacultyProfileDTO savedProfile = facultyProfileService.saveFacultyProfile(facultyProfile);
        System.out.println("Added faculty profile "+facultyProfile);
        return new ResponseEntity<FacultyProfileDTO>(savedProfile, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<FacultyProfileDTO> updateFacultyProfile(@RequestBody FacultyProfile facultyProfile){
        return new ResponseEntity<FacultyProfileDTO>(facultyProfileService.updateFacultyProfile(facultyProfile),
                HttpStatus.OK);
    }

    @GetMapping("/view-profile")
    public ResponseEntity<FacultyProfileDTO> viewFacultyProfile(){
        return new ResponseEntity<FacultyProfileDTO>(facultyProfileService.viewProfile(),
                HttpStatus.OK);
    }

    @GetMapping("/view-students")
    public ResponseEntity<List<StudentProfileDTO>> findStudentsByLoggedInFacultyDepartment(){
        return new ResponseEntity<List<StudentProfileDTO>>(facultyProfileService.findStudentsByLoggedInFacultyDepartment(),
                HttpStatus.OK);
    }

}



