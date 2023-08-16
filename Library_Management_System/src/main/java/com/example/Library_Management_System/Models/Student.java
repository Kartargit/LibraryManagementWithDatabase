package com.example.Library_Management_System.Models;

import com.example.Library_Management_System.Enums.Department;
import com.example.Library_Management_System.Enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id //primaryKey
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rollNo;

    private String name;
    private Integer age;

    @Enumerated(value = EnumType.STRING)
    private Gender gender; //user defined data type and contains only 2 values male or female

    @Enumerated(value = EnumType.STRING)
    private Department department;

    @Column(unique = true)
    private String emailId;

    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL)
    private LibraryCard libraryCard;//need to set libraryCard object here
}
