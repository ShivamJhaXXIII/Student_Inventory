package com.shivam.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Grade entity representing a grade received by a student in a course.
 *
 * A Grade has:
 * - id: unique identifier (positive integer)
 * - courseId: course identifier (non-null, non-blank)
 * - courseName: optional display name of the course
 * - score: the score received (0 <= score <= maxScore)
 * - maxScore: maximum possible score (> 0)
 * - date: optional date the grade was recorded (defaults to now if not provided)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Grade {

    private int id;
    private String courseId;
    private String courseName;
    private double score;
    private double maxScore;
    private LocalDate date;

    /**
     * Convenience constructor for common usage.
     */
    public Grade(int id, String courseId, String courseName, double score, double maxScore) {
        this.id = id;
        this.courseId = Objects.requireNonNull(courseId, "courseId cannot be null");
        this.courseName = courseName;
        this.score = score;
        this.maxScore = maxScore;
        this.date = LocalDate.now();
    }

    /**
     * Compute percentage score (0-100).
     */
    public double getPercentage() {
        if (maxScore <= 0) {
            return 0.0;
        }
        return (score / maxScore) * 100.0;
    }

    /**
     * Check if score is valid (0 <= score <= maxScore and maxScore > 0).
     */
    public boolean isValid() {
        return maxScore > 0 && score >= 0 && score <= maxScore &&
               courseId != null && !courseId.isBlank();
    }

    /**
     * Validate and throw exception if invalid.
     */
    public void validate() {
        if (!isValid()) {
            throw new IllegalArgumentException(
                String.format("Invalid grade: courseId=%s, score=%.2f, maxScore=%.2f",
                    courseId, score, maxScore));
        }
    }
}
