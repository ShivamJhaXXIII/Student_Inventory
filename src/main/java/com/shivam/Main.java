package com.shivam;

import com.shivam.Model.Grade;
import com.shivam.Model.Student;
import com.shivam.Repository.StudentRepoImpl;
import com.shivam.Service.StudentService;
import com.shivam.Service.StudentServiceImpl;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        StudentService service = new StudentServiceImpl(new StudentRepoImpl());

        service.registerStudent(new Student(1, "Shivam"));
        service.addGradeToStudent(1, new Grade(101, "CS101", "Computer Science", 92.0, 100.0, LocalDate.now()));

        Student student = service.getStudentById(1).orElseThrow();
        System.out.println("Student: " + student.getName());
        System.out.println("Grades: " + student.getGrades().size());
        System.out.println("Average: " + service.getStudentAverage(1));
    }
}