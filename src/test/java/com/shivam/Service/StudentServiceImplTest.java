package com.shivam.Service;

import com.shivam.Model.Grade;
import com.shivam.Model.Student;
import com.shivam.Repository.StudentRepoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentServiceImplTest {

    private StudentServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new StudentServiceImpl(new StudentRepoImpl());
    }

    @Test
    void registerAndFetchStudentShouldWork() {
        service.registerStudent(new Student(1, "Shivam"));

        assertTrue(service.getStudentById(1).isPresent());
        assertEquals("Shivam", service.getStudentById(1).orElseThrow().getName());
    }

    @Test
    void addGradeToStudentShouldUpdateAverage() {
        service.registerStudent(new Student(1, "Shivam"));
        service.addGradeToStudent(1, new Grade(10, "CS101", "Computer Science", 80.0, 100.0, LocalDate.of(2026, 5, 1)));
        service.addGradeToStudent(1, new Grade(11, "CS102", "Data Structures", 90.0, 100.0, LocalDate.of(2026, 5, 1)));

        assertEquals(85.0, service.getStudentAverage(1), 0.0001);
        assertEquals(2, service.getStudentById(1).orElseThrow().getGrades().size());
    }

    @Test
    void updateStudentShouldRequireExistingRecord() {
        assertThrows(IllegalArgumentException.class, () -> service.updateStudent(new Student(1, "Missing")));
    }

    @Test
    void addGradeShouldRejectMissingStudent() {
        Grade grade = new Grade(10, "CS101", "Computer Science", 80.0, 100.0, LocalDate.of(2026, 5, 1));
        assertThrows(IllegalArgumentException.class, () -> service.addGradeToStudent(1, grade));
    }
}

