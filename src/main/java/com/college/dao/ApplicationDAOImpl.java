package com.college.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.college.model.Application;
import com.college.util.DBUtil;

public class ApplicationDAOImpl implements ApplicationDAO {

    @Override
    public void addApplication(Application app) throws Exception {
        String sql = "INSERT INTO Applications(student_id,course_id,status) VALUES(?,?,?)";
        try(Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, app.getStudentId());
            ps.setInt(2, app.getCourseId());
            ps.setString(3, app.getStatus());
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()) app.setApplicationId(rs.getInt(1));
            }
        }
    }

    @Override
    public Application findById(int id) throws Exception {
        String sql = "SELECT * FROM Applications WHERE application_id=?";
        try(Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    Application a = new Application();
                    a.setApplicationId(rs.getInt("application_id"));
                    a.setStudentId(rs.getInt("student_id"));
                    a.setCourseId(rs.getInt("course_id"));
                    a.setStatus(rs.getString("status"));
                    a.setRank(rs.getObject("Grade") != null ? rs.getInt("Grade") : null);
                    return a;
                }
            }
        }
        return null;
    }

    @Override
    public void updateStatus(int appId, String status) throws Exception {
        String sql = "UPDATE Applications SET status=? WHERE application_id=?";
        try(Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1,status);
            ps.setInt(2,appId);
            ps.executeUpdate();
        }
    }

    @Override
    public void updateGrade(int appId, int Grade) throws Exception {
        String sql = "UPDATE Applications SET Grade=? WHERE application_id=?";
        try(Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Grade);
            ps.setInt(2, appId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Application> getApplicationsByCourse(int courseId) throws Exception {
        String sql = "SELECT * FROM Applications WHERE course_id=?";
        List<Application> list = new ArrayList<>();
        try(Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1,courseId);
            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Application a = new Application();
                    a.setApplicationId(rs.getInt("application_id"));
                    a.setStudentId(rs.getInt("student_id"));
                    a.setCourseId(rs.getInt("course_id"));
                    a.setStatus(rs.getString("status"));
                    a.setRank(rs.getObject("Grade") != null ? rs.getInt("Grade") : null);
                    list.add(a);
                }
            }
        }
        return list;
    }
}
