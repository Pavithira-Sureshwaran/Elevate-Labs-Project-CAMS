
package com.college;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.college.dao.ApplicationDAO;
import com.college.dao.ApplicationDAOImpl;
import com.college.dao.CourseDAO;
import com.college.dao.CourseDAOImpl;
import com.college.dao.StudentDAO;
import com.college.dao.StudentDAOImpl;
import com.college.model.Application;
import com.college.model.Course;
import com.college.model.Student;
import com.college.service.AdmissionManager;

public class AdminPanel extends JFrame {

    private StudentDAO studentDAO = new StudentDAOImpl();
    private CourseDAO courseDAO = new CourseDAOImpl();
    private ApplicationDAO applicationDAO = new ApplicationDAOImpl();
    private AdmissionManager admissionManager = new AdmissionManager();

    private JTable studentTable, courseTable, applicationTable;

    public AdminPanel() {
        setTitle("College Admission Management System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Students", createStudentPanel());
        tabs.addTab("Courses", createCoursePanel());
        tabs.addTab("Applications", createApplicationPanel());
        tabs.addTab("Merit List & PDF", createMeritPanel());

        add(tabs);
        setVisible(true);
    }

    // ================= STUDENT PANEL =================
    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        studentTable = new JTable(new DefaultTableModel(new String[]{"ID", "Name", "Email", "Phone", "Percentage"}, 0));
        panel.add(new JScrollPane(studentTable), BorderLayout.CENTER);

        JPanel form = new JPanel(new FlowLayout());
        JTextField nameField = new JTextField(10);
        JTextField emailField = new JTextField(10);
        JTextField phoneField = new JTextField(10);
        JTextField percField = new JTextField(5);
        JButton addBtn = new JButton("Add Student");

        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Email:"));
        form.add(emailField);
        form.add(new JLabel("Phone:"));
        form.add(phoneField);
        form.add(new JLabel("Percentage:"));
        form.add(percField);
        form.add(addBtn);

        addBtn.addActionListener(e -> {
            try {
                Student s = new Student(nameField.getText(), emailField.getText(), phoneField.getText(), Double.parseDouble(percField.getText()));
                studentDAO.addStudent(s);
                refreshStudents();
                JOptionPane.showMessageDialog(this, "Student Added Successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        panel.add(form, BorderLayout.SOUTH);
        refreshStudents();
        return panel;
    }

    private void refreshStudents() {
        try {
            DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
            model.setRowCount(0);
            List<Student> students = studentDAO.getAllStudents();
            for (Student s : students) {
                model.addRow(new Object[]{s.getStudentId(), s.getName(), s.getEmail(), s.getPhone(), s.getPercentage()});
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // ================= COURSE PANEL =================
    private JPanel createCoursePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        courseTable = new JTable(new DefaultTableModel(new String[]{"ID", "Code", "Name", "Seats", "Cutoff"}, 0));
        panel.add(new JScrollPane(courseTable), BorderLayout.CENTER);

        JPanel form = new JPanel(new FlowLayout());
        JTextField codeField = new JTextField(6);
        JTextField nameField = new JTextField(10);
        JTextField seatsField = new JTextField(5);
        JTextField cutoffField = new JTextField(5);
        JButton addBtn = new JButton("Add Course");

        form.add(new JLabel("Code:"));
        form.add(codeField);
        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Seats:"));
        form.add(seatsField);
        form.add(new JLabel("Cutoff:"));
        form.add(cutoffField);
        form.add(addBtn);

        addBtn.addActionListener(e -> {
            try {
                Course c = new Course(codeField.getText(), nameField.getText(), Integer.parseInt(seatsField.getText()), Double.parseDouble(cutoffField.getText()));
                courseDAO.addCourse(c);
                refreshCourses();
                JOptionPane.showMessageDialog(this, "Course Added Successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        panel.add(form, BorderLayout.SOUTH);
        refreshCourses();
        return panel;
    }

    private void refreshCourses() {
        try {
            DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
            model.setRowCount(0);
            List<Course> courses = courseDAO.getAllCourses();
            for (Course c : courses) {
                model.addRow(new Object[]{c.getCourseId(), c.getCode(), c.getName(), c.getSeats(), c.getCutoff()});
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // ================= APPLICATION PANEL =================
    private JPanel createApplicationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        applicationTable = new JTable(new DefaultTableModel(new String[]{"application_id", "student_id", "course_id", "status", "Grade"}, 0));
        panel.add(new JScrollPane(applicationTable), BorderLayout.CENTER);

        JPanel form = new JPanel(new FlowLayout());
        JTextField studentField = new JTextField(5);
        JTextField courseField = new JTextField(5);
        JButton applyBtn = new JButton("Apply");

        form.add(new JLabel("Student ID:"));
        form.add(studentField);
        form.add(new JLabel("Course ID:"));
        form.add(courseField);
        form.add(applyBtn);

        applyBtn.addActionListener(e -> {
            try {
                Application app = new Application();
                app.setStudentId(Integer.parseInt(studentField.getText()));
                app.setCourseId(Integer.parseInt(courseField.getText()));
                app.setStatus("APPLIED");
                applicationDAO.addApplication(app);
                refreshApplications();
                JOptionPane.showMessageDialog(this, "Application Submitted!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        panel.add(form, BorderLayout.SOUTH);
        refreshApplications();
        return panel;
    }

    private void refreshApplications() {
        try {
            DefaultTableModel model = (DefaultTableModel) applicationTable.getModel();
            model.setRowCount(0);
            List<Course> courses = courseDAO.getAllCourses();
            for (Course c : courses) {
                List<Application> apps = applicationDAO.getApplicationsByCourse(c.getCourseId());
                for (Application a : apps) {
                    model.addRow(new Object[]{a.getApplicationId(), a.getStudentId(), a.getCourseId(), a.getStatus(), a.getGrade()});
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // ================= MERIT PANEL =================
    private JPanel createMeritPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        JTextField courseIdField = new JTextField(5);
        JButton calcBtn = new JButton("Calculate Merit & Generate PDF");

        panel.add(new JLabel("Course ID:"));
        panel.add(courseIdField);
        panel.add(calcBtn);

        calcBtn.addActionListener(e -> {
            try {
                int courseId = Integer.parseInt(courseIdField.getText());
                admissionManager.calculateMeritAndRank(courseId);
                String pdfPath = admissionManager.generateMeritListPDF(courseId);
                JOptionPane.showMessageDialog(this, "Merit List Calculated!\nPDF saved at: " + pdfPath);
                refreshApplications();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        return panel;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(AdminPanel::new);

    }

    public AdmissionManager getAdmissionManager() {
        return admissionManager;
    }

    public void setAdmissionManager(AdmissionManager admissionManager) {
        this.admissionManager = admissionManager;
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
