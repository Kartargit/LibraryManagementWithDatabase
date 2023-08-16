package com.example.Library_Management_System.Services;

import com.example.Library_Management_System.Enums.Department;
import com.example.Library_Management_System.Models.Student;
import com.example.Library_Management_System.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    public String addStudent(Student student) throws Exception{
        if(student.getRollNo()!=null){
            throw new Exception("Id should not be sent as parameter");
        }
        studentRepository.save(student);
        return "Student has been added successfully";
    }
    public Department getDepartmentById(Integer rollNo)throws Exception{
        Optional<Student> optionalStudent = studentRepository.findById(rollNo);

        if(!optionalStudent.isPresent()){
            throw new Exception("RollNo entered is invalid");
        }
        Student student = optionalStudent.get();
        return student.getDepartment();
    }
}
