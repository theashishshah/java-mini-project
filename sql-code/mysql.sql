CREATE DATABASE java_project_db;
USE java_project_db;

CREATE TABLE students (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    gpa FLOAT,
    grade CHAR(1),
    is_enrolled BOOLEAN
);

CREATE TABLE courses (
    course_id INT PRIMARY KEY,
    title VARCHAR(100),
    rating FLOAT,
    category CHAR(1),
    is_active BOOLEAN
);
INSERT INTO students (id, name, gpa, grade, is_enrolled) VALUES
(1, 'Alice Smith', 3.8, 'A', TRUE),
(2, 'Bob Johnson', 2.9, 'B', TRUE),
(3, 'Charlie Davis', 3.2, 'B', FALSE),
(4, 'Diana Wilson', 3.5, 'A', TRUE),
(5, 'Ethan Brown', 1.8, 'D', FALSE),
(6, 'Fiona Clark', 3.9, 'A', TRUE),
(7, 'George Lewis', 2.5, 'C', TRUE),
(8, 'Hannah Walker', 3.1, 'B', TRUE),
(9, 'Ian Hall', 2.0, 'D', FALSE),
(10, 'Julia Allen', 3.6, 'A', TRUE);


INSERT INTO courses (course_id, title, rating, category, is_active) VALUES
(101, 'Introduction to Java', 4.5, 'P', TRUE),
(102, 'Data Structures', 4.2, 'C', TRUE),
(103, 'Web Development', 3.8, 'W', TRUE),
(104, 'Database Systems', 4.0, 'D', TRUE),
(105, 'Operating Systems', 4.3, 'C', FALSE),
(106, 'Computer Networks', 3.7, 'C', TRUE),
(107, 'Software Engineering', 3.9, 'S', TRUE),
(108, 'Machine Learning', 4.6, 'A', FALSE),
(109, 'Cyber Security', 4.1, 'S', TRUE),
(110, 'Cloud Computing', 3.6, 'C', TRUE);


SHOW DATABASES;