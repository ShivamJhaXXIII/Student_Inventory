package com.shivam.Repository;

import com.shivam.Model.Student;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class StudentRepoImpl implements StudentRepo {

	private final Map<Integer, Student> students = new LinkedHashMap<>();

	@Override
	public Student save(Student student) {
		validateStudent(student);
		Student stored = copyOf(student);
		students.put(stored.getId(), stored);
		return copyOf(stored);
	}

	@Override
	public Optional<Student> findById(int id) {
		return Optional.ofNullable(students.get(id)).map(this::copyOf);
	}

	@Override
	public List<Student> findAll() {
		return students.values().stream()
			.map(this::copyOf)
			.toList();
	}

	@Override
	public List<Student> findByName(String name) {
		if (name == null || name.isBlank()) {
			return List.of();
		}

		String targetName = name.trim();
		return students.values().stream()
			.filter(student -> targetName.equals(student.getName()))
			.map(this::copyOf)
			.toList();
	}

	@Override
	public boolean deleteById(int id) {
		return students.remove(id) != null;
	}

	@Override
	public boolean existsById(int id) {
		return students.containsKey(id);
	}

	private void validateStudent(Student student) {
		Objects.requireNonNull(student, "student cannot be null");

		if (student.getId() <= 0) {
			throw new IllegalArgumentException("student id must be positive");
		}

		if (student.getName() == null || student.getName().isBlank()) {
			throw new IllegalArgumentException("student name cannot be blank");
		}
	}

	private Student copyOf(Student student) {
		List<com.shivam.Model.Grade> grades = student.getGrades() == null
			? new ArrayList<>()
			: new ArrayList<>(student.getGrades());
		return new Student(student.getId(), student.getName(), grades);
	}
}
