package com.college.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.college.dao.ApplicationDAO;
import com.college.dao.ApplicationDAOImpl;
import com.college.dao.CourseDAO;
import com.college.dao.CourseDAOImpl;
import com.college.dao.StudentDAO;
import com.college.dao.StudentDAOImpl;
import com.college.model.Application;
import com.college.model.Course;
import com.college.util.DBUtil;

public class AdmissionManager {
    private StudentDAO studentDAO = new StudentDAOImpl();
    private CourseDAO courseDAO = new CourseDAOImpl();
    private ApplicationDAO applicationDAO = new ApplicationDAOImpl();

    public void calculateMeritAndRank(int courseId) throws Exception {
        Course course = courseDAO.findById(courseId);
        if(course==null) throw new IllegalArgumentException("Course not found");

        List<Application> apps = applicationDAO.getApplicationsByCourse(courseId);
        double cutoff = course.getCutoff();

        List<Application> eligible = apps.stream()
                .filter(a -> {
                    try {
                        return studentDAO.findById(a.getStudentId()).getPercentage() >= cutoff;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        eligible.sort((a,b) -> {
            try {
                return Double.compare(
                        studentDAO.findById(b.getStudentId()).getPercentage(),
                        studentDAO.findById(a.getStudentId()).getPercentage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        int Grade = 1;
        for(Application a : eligible) applicationDAO.updateGrade(a.getApplicationId(), Grade++);

        int seats = course.getSeats();
        for(int i=0;i<eligible.size();i++) {
            if(i<seats) applicationDAO.updateStatus(eligible.get(i).getApplicationId(),"APPROVED");
            else applicationDAO.updateStatus(eligible.get(i).getApplicationId(),"REJECTED");
        }
    }



    public String generateMeritListPDF(int courseId) throws Exception {
        String sql = "SELECT c.code, c.name as course_name, s.name as student_name, s.email, s.percentage, a.Grade " +
                "FROM Applications a " +
                "JOIN Students s ON a.student_id = s.student_id " +
                "JOIN Courses c ON a.course_id = c.course_id " +
                "WHERE a.course_id=? AND a.status='APPROVED' ORDER BY a.Grade";

        String filename = "MeritList_course_" + courseId + ".pdf";

        try(Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            try(ResultSet rs = ps.executeQuery(); PDDocument document = new PDDocument()) {

                PDPage page = new PDPage(PDRectangle.LETTER);
                document.addPage(page);
                try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                    content.setLeading(15f);
                    content.beginText();
                    content.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    content.newLineAtOffset(25, 700);
                    content.showText("CourseCode | CourseName | StudentName | Email | Percentage | Rank");
                    content.newLine();
                    
                    content.setFont(PDType1Font.HELVETICA, 12);
                    while(rs.next()) {
                        String line = String.format("%s | %s | %s | %s | %.2f | %s",
                                rs.getString("code"),
                                rs.getString("course_name"),
                                rs.getString("student_name"),
                                rs.getString("email"),
                                rs.getDouble("percentage"),
                                rs.getObject("Grade")!=null ? rs.getInt("Grade") : "");
                        content.showText(line);
                        content.newLine();
                    }
                    
                    content.endText();
                }
                document.save(filename);
            }
        }

        return filename;
    }




    public ApplicationDAO getApplicationDAO() {
        return applicationDAO;
    }

    public void setApplicationDAO(ApplicationDAO applicationDAO) {
        this.applicationDAO = applicationDAO;
    }

    public CourseDAO getCourseDAO() {
        return courseDAO;
    }

    public void setCourseDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public StudentDAO getStudentDAO() {
        return studentDAO;
    }

    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }
}
