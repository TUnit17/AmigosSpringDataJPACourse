package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student s1 = new Student(
                    "Student1",
                    "Jones",
                    "maria.jones@amigoscode.edu",
                    21
            );

            Student s2 = new Student(
                    "Student2",
                    "Jones",
                    "maria2.jones@amigoscode.edu",
                    25
            );

            Student s3 = new Student(
                    "Student3",
                    "Ali",
                    "ahmed.ali@amigoscode.edu",
                    18
            );

            studentRepository.saveAll(List.of(s1, s2, s3));

            System.out.println("Number of students: "+studentRepository.count());

            Optional<Student> student2 = studentRepository.findById(2L);
            System.out.println("Student 2:" + student2);

            printStudentById(studentRepository, 2L);
            printStudentById(studentRepository, 3L);

            System.out.println("Select all students");
            List<Student> students = studentRepository.findAll();
            students.forEach(System.out::println);

            System.out.println("Delete");
            studentRepository.deleteById(1L);

            System.out.println("After delete: "+studentRepository.count());

        };
    }

    private void printStudentById(StudentRepository studentRepository, long id) {
        studentRepository
                .findById(id)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Student with ID"+ id+ "not found"));
    }

}