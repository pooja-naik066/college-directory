package com.pooja.backend_college_directory_app.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;


    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<StudentProfile> students=new HashSet<>();

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<FacultyProfile> faculty;
}
