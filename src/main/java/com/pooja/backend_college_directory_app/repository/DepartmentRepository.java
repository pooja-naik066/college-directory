package com.pooja.backend_college_directory_app.repository;

import com.pooja.backend_college_directory_app.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
}
