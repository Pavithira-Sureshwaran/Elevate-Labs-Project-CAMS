package com.college.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.college.model.Student;
import com.college.util.DBUtil;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public void addStudent(Student student) throws Exception {
        String sql = "INSERT INTO Students(name,email,phone,percentage) VALUES(?,?,?,?)";
        try(Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getPhone());
            ps.setDouble(4, student.getPercentage());
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()) student.setStudentId(rs.getInt(1));
            }
        }
    }

    @Override
    public Student findById(int id) {
        String sql = "SELECT * FROM Students WHERE student_id=?";
        try(Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    Student s = new Student();
                    s.setStudentId(rs.getInt("student_id"));
                    s.setName(rs.getString("name"));
                    s.setEmail(rs.getString("email"));
                    s.setPhone(rs.getString("phone"));
                    s.setPercentage(rs.getDouble("percentage"));
                    return s;
                }
            }
            
            }
            catch(Exception e){
                System.out.println(e);
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() throws Exception {
        String sql = "SELECT * FROM Students";
        List<Student> list = new ArrayList<>();
        try(Connection con = DBUtil.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            while(rs.next()) {
                Student s = new Student();
                s.setStudentId(rs.getInt("student_id"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setPhone(rs.getString("phone"));
                s.setPercentage(rs.getDouble("percentage"));
                list.add(s);
            }
        }
        return list;
    }
}
