package ru.hogwarts.school_magic.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school_magic.model.Faculty;
import ru.hogwarts.school_magic.model.Student;
import ru.hogwarts.school_magic.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping()
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("{id}")
    public Faculty readFaculty(@PathVariable int id) {
        return facultyService.getFaculty(id);
    }

    @PutMapping()
    public Faculty updateFaculty(@RequestBody Faculty faculty) {
        return facultyService.updateFaculty(faculty);
    }

    @DeleteMapping("{id}")
    public void deleteFaculty(@PathVariable int id) {
        facultyService.deleteFaculty(id);
    }

    @GetMapping()
    public List<Faculty> getFacultyByColor(@RequestParam String color) {
        return facultyService.getFacultyByColor(color);
    }

    @GetMapping("/colorOrName")
    public List<Faculty> getFacultyByColorOrNameIgnoreCase(@RequestParam(required = false) String color,
                                                           @RequestParam(required = false) String name) {
        return facultyService.getFacultyByColorOrNameIgnoreCase(color, name);
    }

    @GetMapping("/byStudent")
    public Faculty getFacultyByStudent(@RequestBody Student student) {
        return facultyService.getFacultyByStudent(student);
    }
}
