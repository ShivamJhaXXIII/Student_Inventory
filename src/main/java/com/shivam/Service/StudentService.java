package com.shivam.Service;

import com.shivam.Model.Grade;
import com.shivam.Model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student registerStudent(Student student);

    Student updateStudent(Student student);

    Optional<Student> getStudentById(int id);

    List<Student> getAllStudents();

    List<Student> findStudentsByName(String name);

    boolean deleteStudent(int id);

    Student addGradeToStudent(int studentId, Grade grade);

    double getStudentAverage(int studentId);
}

