package ru.hogwarts.school_magic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school_magic.model.Student;
import ru.hogwarts.school_magic.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        logger.debug("Was return object {} for createStudent", Student.class);
        return studentRepository.save(student);
    }

    public Student getStudent(int id) {
        logger.info("Was invoked method for get student with {}", id);
        logger.debug("Was return object {} for getStudent", Student.class);
        return studentRepository.findById(id)
                .orElseThrow();
    }

    public Student updateStudent(Student student) {
        logger.info("Was invoked method for update student");
        logger.debug("Was return object {} for updateStudent", Student.class);
        return studentRepository.save(student);
    }

    public void deleteStudent(Integer id) {
        logger.info("Was invoked method for delete student with {}", id);
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentsByAge(int age) {
        logger.info("Was invoked method for getStudentsByAge student with {}", age);
        return studentRepository.findStudentByAge(age);
    }

    public List<Student> getStudentsByAgeBetween(int min, int max) {
        logger.info("Was invoked method for getStudentsByAgeBetween student with {} and {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public List<Student> getStudentsByFacultyId(long facultyId) {
        logger.info("Was invoked method for getStudentsByFacultyId student with {}", facultyId);
        return studentRepository.findByFaculty_Id(facultyId);
    }

    public Integer getCountOfStudents() {
        logger.info("Was invoked method for getCountOfStudents student");
        return studentRepository.findCountOfStudents();
    }

    public Double getAverageAgeOfStudents() {
        logger.info("Was invoked method for getAverageAgeOfStudents student");
        return studentRepository.findAverageAgeOfStudents();
    }

    public List<Student> getStudentWithMaxId() {
        logger.info("Was invoked method for getStudentWithMaxId student");
        return studentRepository.findStudentsWithMaxId();
    }
}
