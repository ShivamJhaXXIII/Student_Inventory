package com.shivam.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Student entity representing a student in the grade management system.
 *
 * A Student has:
 * - id: unique identifier (positive integer)
 * - name: student's name (non-blank)
 * - grades: list of Grade records for courses taken
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {

    private int id;
    private String name;
    private List<Grade> grades = new ArrayList<>();

    /**
     * Convenience constructor for creating a student with id and name only.
     */
    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.grades = new ArrayList<>();
    }

    /**
     * Add a grade to this student's record.
     */
    public void addGrade(Grade grade) {
        if (grade != null) {
            this.grades.add(grade);
        }
    }

    /**
     * Remove a grade by its id.
     */
    public boolean removeGrade(int gradeId) {
        return this.grades.removeIf(g -> g.getId() == gradeId);
    }

    /**
     * Get all grades for a specific course.
     */
    public List<Grade> getGradesByCourse(String courseId) {
        return this.grades.stream()
            .filter(g -> courseId.equals(g.getCourseId()))
            .toList();
    }

    /**
     * Compute average score across all grades.
     */
    public double getAverageScore() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        return grades.stream()
            .mapToDouble(g -> g.getScore())
            .average()
            .orElse(0.0);
    }
}
