-- liquibase formatted sql

-- changeset myUser:1
CREATE INDEX student_name_index ON students (name);
-- changeset myUser:2
CREATE INDEX faculty_color_name_index ON faculties (name, color);
-- changeset myUser:3
DROP INDEX student_name_index;
-- changeset myUser:4
DROP INDEX faculty_color_name_index;

