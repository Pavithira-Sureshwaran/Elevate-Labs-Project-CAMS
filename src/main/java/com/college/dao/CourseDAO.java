package com.college.dao;

import com.college.model.Course;
import java.util.List;

public interface CourseDAO {
    void addCourse(Course course) throws Exception;
    Course findById(int id) throws Exception;
    List<Course> getAllCourses() throws Exception;
}
