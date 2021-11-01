package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean // this will inject the following parameters inside
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        StudentIdCardRepository studentIdCardRepository) {
        return args -> {
            generateRandomStudentIdCard(studentIdCardRepository);
            studentRepoMethod(studentRepository);
        };

    }

    private void studentRepoMethod(StudentRepository studentRepository) {
        studentRepository.findById(1L)
                .ifPresentOrElse(System.out::println, () -> {
                    System.out.println("not found in studentRepository");
                });

        studentRepository.deleteById(1L);
    }

    private void paginateStudents(StudentRepository studentRepository) {
        PageRequest pageRequest = PageRequest.of(0,5);
        Page<Student> page = studentRepository.findAll(pageRequest);
        System.out.println(page);
    }

    private void sortStudents(StudentRepository studentRepository) {
        Sort sort = Sort
                .by("firstName").ascending()
                .and(Sort.by("age").descending());
        studentRepository
                .findAll(sort)
                .forEach(student -> System.out.println(student.getFirstName() + " " + student.getAge()));
    }

    private void generateRandomStudentIdCard(StudentIdCardRepository studentIdCardRepository){
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = String.format("%s.%s@email.com", firstName,lastName);
        int age = faker.number().numberBetween(17, 66);
        Student student = new Student(
                firstName,
                lastName,
                email,
                age
        );
        StudentIdCard studentIdCard = new StudentIdCard("123456789", student);
        studentIdCardRepository.save(studentIdCard);

        studentIdCardRepository.findById(1L)
                .ifPresentOrElse(System.out::println, () -> {
                    System.out.println("Not found 1L");
                });
    }

    private void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i < 20 ; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@email.com", firstName,lastName);
            int age = faker.number().numberBetween(17, 66);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    age
            );

            studentRepository.save(student);
        }
    }

    private void printStudentById(StudentRepository studentRepository, long id) {
        studentRepository
                .findById(id)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Student with ID"+ id+ "not found"));
    }

}