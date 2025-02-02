package com.FullstackEcommerce.Ecommerce.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {

        return studentRepository.findAll();

    }

    public void addNewStudent(Student student) {
        System.out.println("boooooom!");
    }

    public void deleteStudent(Long studentId) {
       boolean exists =  studentRepository.existsById(studentId);
        if(!exists) {
            throw new IllegalStateException("Student not found");
        }
        studentRepository.deleteById(studentId);
    }
@Transactional
    public void updatestudent(Long studentId, String name, String email) {
Student student = studentRepository.findById(studentId)
        .orElseThrow(() -> new IllegalStateException("Student not found"));
if (name != null && !(name.length() >0) && !Objects.equals(student,name)) {
   student.setName(name) ;
}


    }
}