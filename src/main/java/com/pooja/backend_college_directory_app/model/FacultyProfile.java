package com.pooja.backend_college_directory_app.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FacultyProfile {
    @Id
    private Integer userId;

    @OneToOne
    @MapsId
    private User user;

    private String officeHours;

    @ManyToOne
    @JsonBackReference
    private Department department;

    private String email;
    private String phone;
}

