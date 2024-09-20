package com.pooja.backend_college_directory_app.repository;

import com.pooja.backend_college_directory_app.model.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Integer> {

    List<StudentProfile> findByDepartmentId(Integer id);
}
