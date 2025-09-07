# Elevate-Labs-Project-CAMS
# College Admission Management System (CAMS)

A Java Swing application integrated with MySQL and JDBC to manage student applications, course allocations, and generate PDF merit lists.  
Built with Java 17, Maven, Swing GUI, Apache PDFBox for PDF generation, and MySQL for the database.  
**Features**:  
- Student/Course management, application processing, merit calculation based on cut-offs, and PDF export of admission lists.  
**Setup**:
1. Run `schema.sql` to set up the DB.
2. Update `config.properties` with your MySQL credentials.
3. Build with `mvn clean package`, then run `Main.java`.
**Output**:
Generates `MeritList_course_<id>.pdf` files with ranked admission lists.
