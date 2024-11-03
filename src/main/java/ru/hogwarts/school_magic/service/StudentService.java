package ru.hogwarts.school_magic.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school_magic.model.Student;
import ru.hogwarts.school_magic.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(int id) {
        return studentRepository.findById(id)
                .orElseThrow();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Integer id) {
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentsByAge(int age) {
        return studentRepository.findStudentByAge(age);
    }

    public List<Student> getStudentsByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public List<Student> getStudentsByFacultyId(long facultyId) {
        return studentRepository.findByFaculty_Id(facultyId);
    }

    public Integer getCountOfStudents() {
        return studentRepository.findCountOfStudents();
    }

    public Double getAverageAgeOfStudents() {
        return studentRepository.findAverageAgeOfStudents();
    }

    public List<Student> getStudentWithMaxId() {
        return studentRepository.findStudentsWithMaxId();
    }
}
