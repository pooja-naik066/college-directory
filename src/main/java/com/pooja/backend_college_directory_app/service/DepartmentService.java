package com.pooja.backend_college_directory_app.service;

import com.pooja.backend_college_directory_app.dto.DepartmentDTO;
import com.pooja.backend_college_directory_app.dto.RequestResponse;
import com.pooja.backend_college_directory_app.exception.ResourceNotFoundException;
import com.pooja.backend_college_directory_app.model.Department;

import com.pooja.backend_college_directory_app.model.StudentProfile;
import com.pooja.backend_college_directory_app.repository.DepartmentRepository;
import com.pooja.backend_college_directory_app.repository.FacultyProfileRepository;
import com.pooja.backend_college_directory_app.repository.StudentProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private FacultyProfileRepository facultyProfileRepository;


    public RequestResponse createDepartment(Department department) {
        RequestResponse response=new RequestResponse();
        try {
            Department newDepartment=new Department();
            newDepartment.setName(department.getName());
            newDepartment.setDescription(department.getDescription());
            newDepartment.setStudents(new HashSet<>());
            newDepartment.setFaculty(new HashSet<>());
            Department savedDepartment = departmentRepository.save(newDepartment);
            if (savedDepartment.getId() > 0) {
                response.setMessage("Department saved successfully");
                response.setDepartment(savedDepartment);
                response.setStatusCode(200);
            }
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return  response;
    }

    public RequestResponse getAllDepartments() {
        RequestResponse response = new RequestResponse();
        try {
            // Step 1: Fetch all departments
            List<Department> departments = departmentRepository.findAll();

            // Step 2: Convert each Department entity into a DepartmentDTO
            List<DepartmentDTO> departmentDTOList = departments.stream().map(department -> {
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setDepartmentId(department.getId());
                departmentDTO.setDepartmentName(department.getName());
                departmentDTO.setDepartmentDescription(department.getDescription());

                return departmentDTO;
            }).collect(Collectors.toList());

            // Step 3: Set the response with the list of DepartmentDTOs
            response.setStatusCode(200);
            response.setList(departmentDTOList);
            response.setMessage("Departments fetched successfully");

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while fetching departments: " + e.getMessage());
        }

        return response;
    }


    public DepartmentDTO updateDepartment(Integer departmentId, Department department) {
        Department existingDepartment=departmentRepository.findById(departmentId).orElseThrow(()->
                new ResourceNotFoundException("Department with id"+departmentId+" not found"));
        existingDepartment.setDescription(department.getDescription());
        existingDepartment.setName(existingDepartment.getName());
        Department updatedDepartment=departmentRepository.save(existingDepartment);

        DepartmentDTO dto=new DepartmentDTO();
        dto.setDepartmentId(existingDepartment.getId());
        dto.setDepartmentName(existingDepartment.getName());
        dto.setDepartmentDescription(existingDepartment.getDescription());
        return dto;
    }

    public RequestResponse deleteDepartment(Integer departmentId) {
        RequestResponse response=new RequestResponse();
        Department existingDepartment=departmentRepository.findById(departmentId).orElseThrow(()->
                new ResourceNotFoundException("Department with id"+departmentId+" not found"));
            try {
                departmentRepository.delete(existingDepartment);
                response.setMessage("Department with id "+departmentId+" deleted sucessfully");
                response.setStatusCode(200);
            }
            catch (Exception e){
                response.setMessage("Students/Faculties are associated with this department");
                response.setStatusCode(500);
            }
            return response;

    }
}
