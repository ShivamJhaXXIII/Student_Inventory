package com.shivam.Service;

import com.shivam.Model.Grade;
import com.shivam.Model.Student;
import com.shivam.Repository.StudentRepo;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;

    public StudentServiceImpl(StudentRepo studentRepo) {
        this.studentRepo = Objects.requireNonNull(studentRepo, "studentRepo cannot be null");
    }

    @Override
    public Student registerStudent(Student student) {
        return studentRepo.save(validateStudent(student));
    }

    @Override
    public Student updateStudent(Student student) {
        if (!studentRepo.existsById(validateStudent(student).getId())) {
            throw new IllegalArgumentException("student does not exist: " + student.getId());
        }
        return studentRepo.save(student);
    }

    @Override
    public Optional<Student> getStudentById(int id) {
        return studentRepo.findById(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    @Override
    public List<Student> findStudentsByName(String name) {
        return studentRepo.findByName(name);
    }

    @Override
    public boolean deleteStudent(int id) {
        return studentRepo.deleteById(id);
    }

    @Override
    public Student addGradeToStudent(int studentId, Grade grade) {
        Grade validatedGrade = validateGrade(grade);
        Student student = studentRepo.findById(studentId)
            .orElseThrow(() -> new IllegalArgumentException("student does not exist: " + studentId));

        student.addGrade(validatedGrade);
        return studentRepo.save(student);
    }

    @Override
    public double getStudentAverage(int studentId) {
        return studentRepo.findById(studentId)
            .map(Student::getAverageScore)
            .orElseThrow(() -> new IllegalArgumentException("student does not exist: " + studentId));
    }

    private Student validateStudent(Student student) {
        Objects.requireNonNull(student, "student cannot be null");
        if (student.getId() <= 0) {
            throw new IllegalArgumentException("student id must be positive");
        }
        if (student.getName() == null || student.getName().isBlank()) {
            throw new IllegalArgumentException("student name cannot be blank");
        }
        return student;
    }

    private Grade validateGrade(Grade grade) {
        Objects.requireNonNull(grade, "grade cannot be null");
        grade.validate();
        return grade;
    }
}

