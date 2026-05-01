package com.shivam.Repository;

import com.shivam.Model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepo {

    Student save(Student student);

    Optional<Student> findById(int id);

    List<Student> findAll();

    List<Student> findByName(String name);

    boolean deleteById(int id);

    boolean existsById(int id);
}
