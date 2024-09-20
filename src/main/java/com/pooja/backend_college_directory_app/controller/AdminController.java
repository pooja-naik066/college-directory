package com.pooja.backend_college_directory_app.controller;

import com.pooja.backend_college_directory_app.dto.DepartmentDTO;
import com.pooja.backend_college_directory_app.dto.RequestResponse;
import com.pooja.backend_college_directory_app.model.Department;
import com.pooja.backend_college_directory_app.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("create-department")
    public ResponseEntity<RequestResponse> createDepartment(@RequestBody Department department){
        return ResponseEntity.ok(departmentService.createDepartment(department));
    }

    @PutMapping("update-department/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Integer departmentId,
                                                          @RequestBody Department department){
        return new ResponseEntity<DepartmentDTO>(departmentService.updateDepartment(departmentId,department),
                HttpStatus.OK);
    }

    @DeleteMapping("delete-department/{departmentId}")
    public ResponseEntity<RequestResponse> deleteDepartment(@PathVariable Integer departmentId){
        return new ResponseEntity<RequestResponse>(departmentService.deleteDepartment(departmentId),
                HttpStatus.OK);
    }

    @GetMapping("view-departments")
    public ResponseEntity<RequestResponse> getAllDepartment(){
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }



}
