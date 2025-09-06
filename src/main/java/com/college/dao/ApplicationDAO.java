package com.college.dao;

import java.util.List;

import com.college.model.Application;

public interface ApplicationDAO {
    void addApplication(Application app) throws Exception;
    Application findById(int id) throws Exception;
    void updateStatus(int appId, String status) throws Exception;
    void updateGrade(int appId, int Grade) throws Exception;
    List<Application> getApplicationsByCourse(int courseId) throws Exception;
}
