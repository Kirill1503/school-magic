package ru.hogwarts.school_magic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school_magic.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findStudentByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    List<Student> findByFaculty_Id(Long faculty_id);

    @Query(value = "SELECT COUNT(*) FROM Student", nativeQuery = true)
    Integer findCountOfStudents();

    @Query(value = "SELECT AVG(age) FROM Student", nativeQuery = true)
    Double findAverageAgeOfStudents();

    @Query(value = "SELECT * from student order by (student.id) DESC LIMIT 5", nativeQuery = true)
    List<Student> findStudentsWithMaxId();
}

