package com.pooja.backend_college_directory_app.service;

import com.pooja.backend_college_directory_app.dto.FacultyProfileDTO;
import com.pooja.backend_college_directory_app.dto.RequestResponse;
import com.pooja.backend_college_directory_app.dto.StudentProfileDTO;
import com.pooja.backend_college_directory_app.exception.ResourceNotFoundException;
import com.pooja.backend_college_directory_app.model.Department;
import com.pooja.backend_college_directory_app.model.FacultyProfile;
import com.pooja.backend_college_directory_app.model.StudentProfile;
import com.pooja.backend_college_directory_app.model.User;
import com.pooja.backend_college_directory_app.repository.DepartmentRepository;
import com.pooja.backend_college_directory_app.repository.FacultyProfileRepository;
import com.pooja.backend_college_directory_app.repository.StudentProfileRepository;
import com.pooja.backend_college_directory_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class StudentProfileService {
    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private FacultyProfileRepository facultyProfileRepository;

    public StudentProfileDTO saveStudentProfile(StudentProfile studentProfile) {
        // Step 1: Get the logged-in user's authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // Get the username of the logged-in user

        // Step 2: Find the User entity using the username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Step 3: Associate the studentProfile with the current user
        studentProfile.setUser(user);

        // Step 4: Associate the department with the student, if provided
        Optional.ofNullable(studentProfile.getDepartment()).ifPresent(department -> {
            Department foundDepartment = departmentRepository.findById(department.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + department.getId()));

            // Step 5: Set department in student profile
            studentProfile.setDepartment(foundDepartment);

        });

        // Step 7: Save the updated student profile and return it
        StudentProfile savedStudent= studentProfileRepository.save(studentProfile);
        StudentProfileDTO dto=new StudentProfileDTO();
        dto.setStudentName(savedStudent.getUser().getName());
        dto.setEmail(savedStudent.getEmail());
        dto.setPhone(savedStudent.getPhone());
        dto.setYear(savedStudent.getYear());
        dto.setDepartmentName(savedStudent.getDepartment().getName());
        return  dto;
    }

    public StudentProfileDTO updateStudentProfile( StudentProfile updatedStudentProfile) {
        // Step 1: Get the logged-in user's authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // Get the username of the logged-in user

        // Step 2: Find the User entity using the username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Step 3: Find the existing student profile by studentId
        StudentProfile existingStudentProfile = studentProfileRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found with ID: " +user.getId()));


        // Step 5: Update the existing profile fields
        existingStudentProfile.setYear(updatedStudentProfile.getYear());
        existingStudentProfile.setEmail(updatedStudentProfile.getEmail());
        existingStudentProfile.setPhone(updatedStudentProfile.getPhone());

        // Step 6: Associate the department with the updated student, if provided
        Optional.ofNullable(updatedStudentProfile.getDepartment()).ifPresent(department -> {
            Department foundDepartment = departmentRepository.findById(department.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + department.getId()));

            // Set the new department in the student profile
            existingStudentProfile.setDepartment(foundDepartment);
        });

        // Step 7: Save and return the updated student profile
        StudentProfile updatedStudent= studentProfileRepository.save(existingStudentProfile);
        StudentProfileDTO dto=new StudentProfileDTO();
        dto.setStudentName(updatedStudent.getUser().getName());
        dto.setEmail(updatedStudent.getEmail());
        dto.setPhone(updatedStudent.getPhone());
        dto.setYear(updatedStudent.getYear());
        dto.setDepartmentName(updatedStudent.getDepartment().getName());
        return  dto;

    }


    public StudentProfileDTO viewProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // Get the username of the logged-in user

        // Step 2: Find the User entity using the username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Step 3: Find the existing student profile by studentId
        StudentProfile existingStudentProfile = studentProfileRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found with ID: " +user.getId()));

        StudentProfileDTO dto=new StudentProfileDTO();
        dto.setStudentName(existingStudentProfile.getUser().getName());
        dto.setYear(existingStudentProfile.getYear());
        dto.setPhone(existingStudentProfile.getPhone());
        dto.setEmail(existingStudentProfile.getEmail());
        dto.setDepartmentName(existingStudentProfile.getDepartment().getName());

        return dto;
    }

    public List<FacultyProfileDTO> findFacultiesByLoggedInStudentDepartment() {
        // Step 1: Get the logged-in user's authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // Get the username of the logged-in user

        // Step 2: Find the User entity using the username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Step 3: Find the StudentProfile associated with the logged-in user
        StudentProfile studentProfile = studentProfileRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found"));

        // Step 4: Get the department associated with the student
        Department department = studentProfile.getDepartment();

        if (department == null) {
            throw new ResourceNotFoundException("Department not associated with this student");
        }

        // Step 5: Find the faculties assigned to the department
        List<FacultyProfile> facultyProfiles = facultyProfileRepository.findByDepartmentId(department.getId());

        // Step 6: Convert faculty profiles to DTOs for a clean response
        List<FacultyProfileDTO> facultyProfileDTOs = new ArrayList<>();
        for (FacultyProfile faculty : facultyProfiles) {
            FacultyProfileDTO facultyDTO = new FacultyProfileDTO();
            facultyDTO.setFacultyName(faculty.getUser().getName());
            facultyDTO.setOfficeHours(faculty.getOfficeHours());
            facultyDTO.setDepartmentName(department.getName());
            facultyDTO.setPhone(faculty.getPhone());
            facultyDTO.setEmail(faculty.getEmail());

            facultyProfileDTOs.add(facultyDTO);
        }

        return facultyProfileDTOs;
    }



}
