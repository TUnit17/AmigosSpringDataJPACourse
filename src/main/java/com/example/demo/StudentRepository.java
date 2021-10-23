package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.firstName = :firstName AND s.age = :age")
    List<Student> findStudentsByFirstNameEqualsAndAgeEquals(
            @Param("firstName") String firstName,
            @Param("age") Integer age);

    @Query("SELECT s from Student s WHERE s.lastName = ?1")
    List<Student> findStudentsByLastName(String lastName);

    // this is a native query because select * as opposed to select s
    @Query(value = "SELECT * FROM Student s WHERE s.first_name = :firstName", nativeQuery = true)
    List<Student> findStudentsByFirstNameNative(@Param("firstName") String firstName);
}

// https://docs.spring.io/spring-data/jpa/docs/2.5.5/reference/html/#jpa.query-methods
