package ru.hogwarts.school_magic.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school_magic.model.Faculty;
import ru.hogwarts.school_magic.model.Student;
import ru.hogwarts.school_magic.repository.FacultyRepository;

import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Integer id) {
        return facultyRepository.findById(id)
                .orElseThrow();
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Integer id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> getFacultyByColor(String color) {
        return facultyRepository.findFacultyByColor(color);
    }

    public List<Faculty> getFacultyByColorOrNameIgnoreCase(String color,
                                                           String name) {
        return facultyRepository.findFacultyByColorOrNameIgnoreCase(name, color);
    }

    public Faculty getFacultyByStudent(Student student) {
        return facultyRepository.findFacultyByStudentsContains(student);
    }
}
