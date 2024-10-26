package ru.hogwarts.school_magic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school_magic.model.Faculty;
import ru.hogwarts.school_magic.model.Student;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
    List<Faculty> findFacultyByColor(String color);

    List<Faculty> findFacultyByColorOrNameIgnoreCase(String color, String name);

    Faculty findFacultyByStudentsContains(Student student);
}
