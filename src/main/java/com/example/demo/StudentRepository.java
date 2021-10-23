package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age = ?2")
    List<Student> findStudentsByFirstNameEqualsAndAgeEquals(String firstName,
                                                           Integer age);

    @Query("SELECT s from Student s WHERE s.lastName = ?1")
    List<Student> findStudentsByLastName(String lastName);
}

// https://docs.spring.io/spring-data/jpa/docs/2.5.5/reference/html/#jpa.query-methods
