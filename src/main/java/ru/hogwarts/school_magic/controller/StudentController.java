package ru.hogwarts.school_magic.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school_magic.model.Student;
import ru.hogwarts.school_magic.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping()
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")
    public Student readStudent(@PathVariable int id) {
        return studentService.getStudent(id);
    }

    @PutMapping()
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
    }

    @GetMapping()
    public List<Student> getStudentsByAge(@RequestParam int age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping("/age/between")
    public List<Student> getStudentsByAgeBetween(@RequestParam int min,
                                                 @RequestParam int max) {
        return studentService.getStudentsByAgeBetween(min, max);
    }

    @GetMapping("/byFacultyId")
    public List<Student> getStudentsByFacultyId(@RequestParam int facultyId) {
        return studentService.getStudentsByFacultyId(facultyId);
    }

    @GetMapping("/allCountOfStudents")
    public Integer getAllCountOfStudents() {
        return studentService.getCountOfStudents();
    }

    @GetMapping("/averageAgeOfStudents")
    public Double getAverageAgeOfStudents() {
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("/maxIdStudents")
    public List<Student> findStudentByIdIsAfter() {
        return studentService.getStudentWithMaxId();
    }
}
