package com.college.model;

public class Course {
    private int courseId;
    private String code;
    private String name;
    private int seats;
    private double cutoff;

    public Course() {}
    public Course(String code,String name,int seats,double cutoff) {
        this.code=code; this.name=name; this.seats=seats; this.cutoff=cutoff;
    }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId=courseId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code=code; }

    public String getName() { return name; }
    public void setName(String name) { this.name=name; }

    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats=seats; }

    public double getCutoff() { return cutoff; }
    public void setCutoff(double cutoff) { this.cutoff=cutoff; }

    @Override
    public String toString() { return courseId+","+code+","+name+","+seats+","+cutoff; }
}
