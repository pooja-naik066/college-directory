package com.pooja.backend_college_directory_app.service;

import com.pooja.backend_college_directory_app.dto.DepartmentDTO;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FacultyProfileService {
    @Autowired
    private FacultyProfileRepository facultyProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private StudentProfileRepository studentProfileRepository;


    public FacultyProfileDTO saveFacultyProfile(FacultyProfile facultyProfile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // Get the username of the logged-in user

        // Step 2: Find the User entity using the username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Step 3: Associate the studentProfile with the current user
        facultyProfile.setUser(user);

        // Step 4: Associate the department with the student, if provided
        Optional.ofNullable(facultyProfile.getDepartment()).ifPresent(department -> {
            Department foundDepartment = departmentRepository.findById(department.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + department.getId()));

            // Step 5: Set department in student profile
            facultyProfile.setDepartment(foundDepartment);

        });

        // Step 7: Save the updated student profile and return it
        FacultyProfile savedProfile=facultyProfileRepository.save(facultyProfile);

        FacultyProfileDTO dto=new FacultyProfileDTO();
        dto.setFacultyName(savedProfile.getUser().getName());
        dto.setEmail(savedProfile.getEmail());
        dto.setPhone(savedProfile.getPhone());
        dto.setOfficeHours(savedProfile.getOfficeHours());
        dto.setDepartmentName(savedProfile.getDepartment().getName());
        return  dto;
    }

    public FacultyProfileDTO updateFacultyProfile(FacultyProfile updatedFacultyProfile) {
        // Step 1: Get the logged-in user's authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // Get the username of the logged-in user

        // Step 2: Find the User entity using the username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Step 3: Find the existing student profile by studentId
        FacultyProfile existingFacultyProfile = facultyProfileRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Faculty profile not found with ID: " +user.getId()));


        // Step 5: Update the existing profile fields
        existingFacultyProfile.setOfficeHours(updatedFacultyProfile.getOfficeHours());
        existingFacultyProfile.setPhone(updatedFacultyProfile.getPhone());
        existingFacultyProfile.setEmail(updatedFacultyProfile.getEmail());

        // Step 6: Associate the department with the updated student, if provided
        Optional.ofNullable(updatedFacultyProfile.getDepartment()).ifPresent(department -> {
            Department foundDepartment = departmentRepository.findById(department.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + department.getId()));

            // Set the new department in the student profile
            existingFacultyProfile.setDepartment(foundDepartment);
        });

        // Step 7: Save and return the updated student profile
        FacultyProfile updatedFaculty=facultyProfileRepository.save(existingFacultyProfile);
        FacultyProfileDTO dto=new FacultyProfileDTO();
        dto.setFacultyName(updatedFaculty.getUser().getName());
        dto.setEmail(updatedFaculty.getEmail());
        dto.setPhone(updatedFaculty.getPhone());
        dto.setOfficeHours(updatedFaculty.getOfficeHours());
        dto.setDepartmentName(updatedFaculty.getDepartment().getName());
        return  dto;
    }

    public FacultyProfileDTO viewProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // Get the username of the logged-in user

        // Step 2: Find the User entity using the username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Step 3: Find the existing student profile by studentId
        FacultyProfile existingFacultyProfile = facultyProfileRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Faculty profile not found with ID: " +user.getId()));

        FacultyProfileDTO dto=new FacultyProfileDTO();
        dto.setFacultyName(existingFacultyProfile.getUser().getName());
        dto.setOfficeHours(existingFacultyProfile.getOfficeHours());
        dto.setEmail(existingFacultyProfile.getEmail());
        dto.setPhone(existingFacultyProfile.getPhone());
        dto.setDepartmentName(existingFacultyProfile.getDepartment().getName());

        return dto;
    }

    public List<StudentProfileDTO> findStudentsByLoggedInFacultyDepartment() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // Get the username of the logged-in user

        // Step 2: Find the User entity using the username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Step 3: Find the faculty profile using the user ID
        FacultyProfile facultyProfile = facultyProfileRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Faculty profile not found"));

        // Step 4: Get the department from the faculty profile
        Department department = facultyProfile.getDepartment();
        if (department == null) {
            throw new ResourceNotFoundException("Faculty is not assigned to any department");
        }

        // Step 5: Find all students in the department


        List<StudentProfile> students = studentProfileRepository.findByDepartmentId(department.getId());


        // Step 6: Convert StudentProfile entities to StudentProfileDTO
        List<StudentProfileDTO> studentDTOs = students.stream().map(student -> {
            StudentProfileDTO dto = new StudentProfileDTO();
            dto.setStudentName(student.getUser().getName());
            dto.setPhone(student.getPhone());
            dto.setEmail(student.getEmail());
            dto.setYear(student.getYear());
            dto.setDepartmentName(department.getName());
            return dto;
        }).collect(Collectors.toList());

        // Step 7: Return the list of student DTOs
        return studentDTOs;
    }
}
