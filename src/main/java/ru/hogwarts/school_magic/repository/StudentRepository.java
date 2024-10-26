package ru.hogwarts.school_magic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school_magic.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findStudentByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    List<Student> findByFaculty_Id(Long faculty_id);
}

