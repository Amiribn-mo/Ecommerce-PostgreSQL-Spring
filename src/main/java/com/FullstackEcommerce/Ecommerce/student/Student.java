package com.FullstackEcommerce.Ecommerce.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "studentInfo")
public class Student {

    // Define the ID field with @Id and use @GeneratedValue for automatic generation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    private String lastName;
    private LocalDate birthDate;
    private Integer age;

    // Constructor with arguments
    public Student(String firstName, String lastName, LocalDate birthDate, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setName(String name) {

    }
}
