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

    public List<String> getStudentsWhoseNameStartsWithPrefix(String prefix) {
            return studentRepository.findAll().stream()
                    .filter(student -> student.getName().startsWith(prefix.toUpperCase()))
                    .map(student -> student.getName().toUpperCase())
                    .toList();
    }

    public Double getAverageAgeAllStudents() {
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }

    public void printStudentsName() {
        logger.info("Was invoked method for printStudentsName student");
        List<String> listNames = studentRepository
                .findAll()
                .stream()
                .map(Student::getName)
                .toList();

        if (listNames.size() < 6) {
            throw new RuntimeException("Need at least 6 students");
        }

        System.out.println(listNames.get(0));
        System.out.println(listNames.get(1));

        new Thread(() -> {
            System.out.println(listNames.get(2));
            System.out.println(listNames.get(3));
        } );

        new Thread(() -> {
            System.out.println(listNames.get(4));
            System.out.println(listNames.get(5));
        } );
        logger.info("Method printStudentsName completed");
    }

    public void printStudentsNameSynchronized() {
        logger.info("Was invoked method for printStudentsNameSynchronized student");
        List<String> listNames = studentRepository
                .findAll()
                .stream()
                .map(Student::getName)
                .toList();

        if (listNames.size() < 6) {
            throw new RuntimeException("Need at least 6 students");
        }

        System.out.println(listNames.get(0));
        System.out.println(listNames.get(1));

        synchronizedMethod(listNames);

        logger.info("Method printStudentsNameSynchronized completed");
    }

    private synchronized void synchronizedMethod(List<String> studentsName) {
        new Thread(() -> {
            System.out.println(studentsName.get(2));
            System.out.println(studentsName.get(3));
        }).start();

        new Thread(() -> {
            System.out.println(studentsName.get(4));
            System.out.println(studentsName.get(5));
        }).start();
    }
}
