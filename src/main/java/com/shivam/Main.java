package com.shivam;

import com.shivam.Model.Grade;
import com.shivam.Model.Student;
import com.shivam.Repository.StudentRepoImpl;
import com.shivam.Service.StudentService;
import com.shivam.Service.StudentServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        StudentService service = new StudentServiceImpl(new StudentRepoImpl());

        System.out.println("=== Student Grade Management System ===");
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1 -> registerStudent(service);
                    case 2 -> updateStudent(service);
                    case 3 -> viewStudentById(service);
                    case 4 -> listAllStudents(service);
                    case 5 -> searchStudentsByName(service);
                    case 6 -> deleteStudent(service);
                    case 7 -> addGradeToStudent(service);
                    case 8 -> showStudentAverage(service);
                    case 9 -> running = false;
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (RuntimeException ex) {
                System.out.println("Error: " + ex.getMessage());
            }

            System.out.println();
        }

        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Register student");
        System.out.println("2. Update student");
        System.out.println("3. View student by ID");
        System.out.println("4. List all students");
        System.out.println("5. Search students by name");
        System.out.println("6. Delete student");
        System.out.println("7. Add grade to student");
        System.out.println("8. Show student average");
        System.out.println("9. Exit");
    }

    private static void registerStudent(StudentService service) {
        Student student = readStudentDetails();
        Student saved = service.registerStudent(student);
        System.out.println("Student registered successfully:");
        printStudent(saved);
    }

    private static void updateStudent(StudentService service) {
        int id = readInt("Enter student ID to update: ");
        String name = readNonBlank("Enter new student name: ");

        Student updated = service.updateStudent(new Student(id, name));
        System.out.println("Student updated successfully:");
        printStudent(updated);
    }

    private static void viewStudentById(StudentService service) {
        int id = readInt("Enter student ID: ");
        service.getStudentById(id)
            .ifPresentOrElse(
                Main::printStudent,
                () -> System.out.println("Student not found for ID: " + id)
            );
    }

    private static void listAllStudents(StudentService service) {
        List<Student> students = service.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("Students:");
        students.forEach(Main::printStudent);
    }

    private static void searchStudentsByName(StudentService service) {
        String name = readNonBlank("Enter student name to search: ");
        List<Student> matches = service.findStudentsByName(name);

        if (matches.isEmpty()) {
            System.out.println("No students found with name: " + name);
            return;
        }

        System.out.println("Matched students:");
        matches.forEach(Main::printStudent);
    }

    private static void deleteStudent(StudentService service) {
        int id = readInt("Enter student ID to delete: ");
        boolean removed = service.deleteStudent(id);
        System.out.println(removed ? "Student deleted successfully." : "Student not found.");
    }

    private static void addGradeToStudent(StudentService service) {
        int studentId = readInt("Enter student ID: ");
        int gradeId = readInt("Enter grade ID: ");
        String courseId = readNonBlank("Enter course ID: ");
        String courseName = readNonBlank("Enter course name: ");
        double score = readDouble("Enter score: ");
        double maxScore = readDouble("Enter max score: ");

        Grade grade = new Grade(gradeId, courseId, courseName, score, maxScore, LocalDate.now());
        Student updated = service.addGradeToStudent(studentId, grade);

        System.out.println("Grade added successfully.");
        printStudent(updated);
    }

    private static void showStudentAverage(StudentService service) {
        int id = readInt("Enter student ID: ");
        double average = service.getStudentAverage(id);
        System.out.printf("Student average for ID %d: %.2f%n", id, average);
    }

    private static Student readStudentDetails() {
        int id = readInt("Enter student ID: ");
        String name = readNonBlank("Enter student name: ");
        return new Student(id, name);
    }

    private static void printStudent(Student student) {
        System.out.println("---------------------------------");
        System.out.println("ID: " + student.getId());
        System.out.println("Name: " + student.getName());
        System.out.printf("Average: %.2f%n", student.getAverageScore());

        if (student.getGrades() == null || student.getGrades().isEmpty()) {
            System.out.println("Grades: none");
            return;
        }

        System.out.println("Grades:");
        for (Grade grade : student.getGrades()) {
            System.out.printf(
                "  - Grade ID: %d, Course: %s (%s), Score: %.2f/%.2f, Date: %s%n",
                grade.getId(),
                grade.getCourseName(),
                grade.getCourseId(),
                grade.getScore(),
                grade.getMaxScore(),
                grade.getDate()
            );
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            if (!input.isBlank()) {
                return input;
            }
            System.out.println("Input cannot be blank.");
        }
    }
}