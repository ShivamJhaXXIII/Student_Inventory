package com.shivam.Repository;

import com.shivam.Model.Grade;
import com.shivam.Model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentRepoImplTest {

    private StudentRepoImpl repo;

    @BeforeEach
    void setUp() {
        repo = new StudentRepoImpl();
    }

    @Test
    void saveShouldStoreAndReturnCopy() {
        Student student = new Student(1, "Shivam");
        student.addGrade(new Grade(10, "CS101", "Computer Science", 92.0, 100.0, LocalDate.of(2026, 5, 1)));

        Student saved = repo.save(student);

        assertEquals(1, repo.findAll().size());
        assertEquals("Shivam", saved.getName());
        assertNotSame(student, saved);
        assertNotSame(student.getGrades(), saved.getGrades());
    }

    @Test
    void saveShouldReplaceExistingStudentWithSameId() {
        repo.save(new Student(1, "First Name"));
        repo.save(new Student(1, "Updated Name"));

        assertEquals(1, repo.findAll().size());
        assertTrue(repo.findById(1).isPresent());
        assertEquals("Updated Name", repo.findById(1).orElseThrow().getName());
    }

    @Test
    void findByNameShouldReturnMatchingStudents() {
        repo.save(new Student(1, "Asha"));
        repo.save(new Student(2, "Asha"));
        repo.save(new Student(3, "Ravi"));

        List<Student> matches = repo.findByName("Asha");

        assertEquals(2, matches.size());
        assertTrue(matches.stream().allMatch(student -> "Asha".equals(student.getName())));
    }

    @Test
    void findByNameShouldReturnEmptyListForBlankInput() {
        repo.save(new Student(1, "Asha"));

        assertTrue(repo.findByName(null).isEmpty());
        assertTrue(repo.findByName("   ").isEmpty());
    }

    @Test
    void findByIdShouldReturnEmptyWhenMissing() {
        assertTrue(repo.findById(99).isEmpty());
    }

    @Test
    void deleteByIdShouldRemoveExistingStudent() {
        repo.save(new Student(1, "Shivam"));

        assertTrue(repo.deleteById(1));
        assertFalse(repo.existsById(1));
        assertTrue(repo.findAll().isEmpty());
    }

    @Test
    void saveShouldRejectNullStudent() {
        assertThrows(NullPointerException.class, () -> repo.save(null));
    }

    @Test
    void saveShouldRejectInvalidStudentData() {
        assertThrows(IllegalArgumentException.class, () -> repo.save(new Student(0, "Valid Name")));
        assertThrows(IllegalArgumentException.class, () -> repo.save(new Student(1, "   ")));
    }
}

