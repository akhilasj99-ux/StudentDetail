package com.example.demo.repository;

import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveStudent(Student student, int total, double average, String result) {

    	String sql =
    			"INSERT INTO Student (roll_no,name,course_name,m1,m2,m3,total,average,result) VALUES (?,?,?,?,?,?,?,?,?)";
    	 jdbcTemplate.update(sql,
                 student.getRollNo(),
                 student.getName(),
                 student.getCourseName(),
                 student.getM1(),
                 student.getM2(),
                 student.getM3(),
                 total,
                 average,
                 result);
    }
}
