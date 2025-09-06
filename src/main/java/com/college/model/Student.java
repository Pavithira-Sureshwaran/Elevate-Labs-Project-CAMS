package com.college.model;

public class Student {
    private int studentId;
    private String name;
    private String email;
    private String phone;
    private double percentage;

    public Student() {}
    public Student(String name,String email,String phone,double percentage) {
        this.name=name; this.email=email; this.phone=phone; this.percentage=percentage;
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId=studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name=name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email=email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone=phone; }

    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage=percentage; }

    @Override
    public String toString() { return studentId+","+name+","+email+","+percentage; }
}
