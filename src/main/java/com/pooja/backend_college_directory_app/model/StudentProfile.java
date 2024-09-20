package com.pooja.backend_college_directory_app.model;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class StudentProfile {
    @Id
    private Integer userId;

    @OneToOne
    @MapsId
    private User user;

    private String year;
    private String email;
    private String phone;

    @ManyToOne
    private Department department;
}
