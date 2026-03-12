package com.example.demo.model;

import jakarta.validation.constraints.*;

public class Student {

    @Positive(message = "Roll number must be positive")
    private int rollNo;

    @NotBlank(message = "Name cannot be empty")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters")
    private String name;

    @NotBlank(message = "Course name cannot be empty")
    private String courseName;

    @Min(value = 0, message = "Marks must be between 0 and 100")
    @Max(value = 100, message = "Marks must be between 0 and 100")
    private int m1;

    @Min(value = 0, message = "Marks must be between 0 and 100")
    @Max(value = 100, message = "Marks must be between 0 and 100")
    private int m2;

    @Min(value = 0, message = "Marks must be between 0 and 100")
    @Max(value = 100, message = "Marks must be between 0 and 100")
    private int m3;

    public Student() {}

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getM1() {
        return m1;
    }

    public void setM1(int m1) {
        this.m1 = m1;
    }

    public int getM2() {
        return m2;
    }

    public void setM2(int m2) {
        this.m2 = m2;
    }

    public int getM3() {
        return m3;
    }

    public void setM3(int m3) {
        this.m3 = m3;
    }
}

