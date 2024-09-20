package com.pooja.backend_college_directory_app.repository;

import com.pooja.backend_college_directory_app.model.FacultyProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyProfileRepository extends JpaRepository<FacultyProfile,Integer> {

    List<FacultyProfile> findByDepartmentId(Integer id);
}
