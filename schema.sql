CREATE DATABASE IF NOT EXISTS college_admission;
USE college_admission;

CREATE TABLE Students(
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    percentage DOUBLE
);

CREATE TABLE Courses(
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(10),
    name VARCHAR(100),
    seats INT,
    cutoff DOUBLE
);

CREATE TABLE Applications(
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    course_id INT,
    status VARCHAR(20),
    rank INT,
    FOREIGN KEY(student_id) REFERENCES Students(student_id),
    FOREIGN KEY(course_id) REFERENCES Courses(course_id)
);

