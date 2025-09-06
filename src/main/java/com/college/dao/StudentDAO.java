package com.college.dao;

import java.util.List;

import com.college.model.Student;

public interface StudentDAO {
    void addStudent(Student student) throws Exception;
    Student findById(int id) throws Exception;
    List<Student> getAllStudents() throws Exception;
}
