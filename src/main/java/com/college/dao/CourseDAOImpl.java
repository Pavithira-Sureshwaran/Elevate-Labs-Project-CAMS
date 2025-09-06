package com.college.dao;

import com.college.model.Course;
import com.college.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {
    @Override
    public void addCourse(Course course) throws Exception {
        String sql = "INSERT INTO Courses(code,name,seats,cutoff) VALUES(?,?,?,?)";
        try(Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, course.getCode());
            ps.setString(2, course.getName());
            ps.setInt(3, course.getSeats());
            ps.setDouble(4, course.getCutoff());
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()) course.setCourseId(rs.getInt(1));
            }
        }
    }

    @Override
    public Course findById(int id) throws Exception {
        String sql = "SELECT * FROM Courses WHERE course_id=?";
        try(Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    Course c = new Course();
                    c.setCourseId(rs.getInt("course_id"));
                    c.setCode(rs.getString("code"));
                    c.setName(rs.getString("name"));
                    c.setSeats(rs.getInt("seats"));
                    c.setCutoff(rs.getDouble("cutoff"));
                    return c;
                }
            }
        }
        return null;
    }

    @Override
    public List<Course> getAllCourses() throws Exception {
        String sql = "SELECT * FROM Courses";
        List<Course> list = new ArrayList<>();
        try(Connection con = DBUtil.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            while(rs.next()) {
                Course c = new Course();
                c.setCourseId(rs.getInt("course_id"));
                c.setCode(rs.getString("code"));
                c.setName(rs.getString("name"));
                c.setSeats(rs.getInt("seats"));
                c.setCutoff(rs.getDouble("cutoff"));
                list.add(c);
            }
        }
        return list;
    }
}
