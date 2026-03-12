package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/student")
    public ResponseEntity<?> getStudentDetails(@Valid @RequestBody Student student,
                                               BindingResult result) {

        if (result.hasErrors()) {

            List<Map<String, String>> errorList = new ArrayList<>();

            for (FieldError error : result.getFieldErrors()) {

                Map<String, String> errorMap = new HashMap<>();

                errorMap.put("error code",
                        String.valueOf(HttpStatus.BAD_REQUEST.value()));
                errorMap.put("error field", error.getField());
                errorMap.put("error message", error.getDefaultMessage());

                errorList.add(errorMap);
            }

            return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
        }

        studentService.saveStudent(student);

        int total = studentService.getTotal(student);
        double average = studentService.getAverage(student);
        String resultStatus = studentService.getResult(student);

        Map<String, Object> response = new HashMap<>();

        response.put("rollNo", student.getRollNo());
        response.put("name", student.getName());
        response.put("courseName", student.getCourseName());
        response.put("total", total);
        response.put("average", average);
        response.put("result", resultStatus);

        if (resultStatus.equalsIgnoreCase("Pass")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

