package ru.hogwarts.school_magic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school_magic.model.Faculty;
import ru.hogwarts.school_magic.model.Student;
import ru.hogwarts.school_magic.repository.FacultyRepository;

import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Integer id) {
        logger.info("Was invoked method for getFaculty with {}}: ", id);
        return facultyRepository.findById(id)
                .orElseThrow();
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Integer id) {
        logger.info("Was invoked method for delete faculty with {}}: ", id);
        facultyRepository.deleteById(id);
    }

    public List<Faculty> getFacultyByColor(String color) {
        logger.info("Was invoked method for getFacultyByColor with {}}: ", color);
        return facultyRepository.findFacultyByColor(color);
    }

    public List<Faculty> getFacultyByColorOrNameIgnoreCase(String color,
                                                           String name) {
        logger.info("Was invoked method for getFacultyByColorOrNameIgnoreCase with {} or {}}: ", color, name);
        return facultyRepository.findFacultyByColorOrNameIgnoreCase(name, color);
    }

    public Faculty getFacultyByStudent(Student student) {
        logger.info("Was invoked method for getFacultyByStudent");
        return facultyRepository.findFacultyByStudentsContains(student);
    }
}
