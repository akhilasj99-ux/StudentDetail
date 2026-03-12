package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public int getTotal(Student student) {
        return student.getM1() + student.getM2() + student.getM3();
    }

    public double getAverage(Student student) {
        return getTotal(student) / 3.0;
    }

    public String getResult(Student student) {

        if (getAverage(student) >= 35) {
            return "Pass";
        } else {
            return "Fail";
        }
    }

    public void saveStudent(Student student) {

        int total = getTotal(student);
        double average = getAverage(student);
        String result = getResult(student);

        repository.saveStudent(student, total, average, result);
    }
}

