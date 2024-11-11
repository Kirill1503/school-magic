package ru.hogwarts.school_magic;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school_magic.controller.StudentController;
import ru.hogwarts.school_magic.model.Student;
import ru.hogwarts.school_magic.repository.StudentRepository;
import ru.hogwarts.school_magic.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    String name1;
    int age1;
    long id1;

    String name2;
    int age2;
    long id2;

    JSONObject jsonObject1;

    Student student1;

    Student student2;

    JSONObject jsonObject2;

    List<Student> students;

    @BeforeEach
    public void setUp() throws Exception {
        name1 = "Name1";
        age1 = 20;
        id1 = 1L;

        name2 = "Name2";
        age2 = 20;
        id2 = 2L;

        jsonObject1 = new JSONObject();
        jsonObject1.put(name1, age1);

        student1 = new Student();
        student1.setId(id1);
        student1.setName(name1);
        student1.setAge(age1);

        jsonObject2 = new JSONObject();
        jsonObject2.put(name2, age2);

        student2 = new Student();
        student2.setId(id2);
        student2.setName(name2);
        student2.setAge(age2);

        students = new ArrayList<>(List.of(student1, student2));

        when(studentRepository.save(any(Student.class))).thenReturn(student1);
        when(studentRepository.findById(any(Integer.class))).thenReturn(Optional.of(student1));
        when(studentRepository.findStudentByAge(any(Integer.class))).thenReturn(students);
        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(students);
        when(studentRepository.findByFaculty_Id(any(Long.class))).thenReturn(students);
    }

    @Test
    public void createStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(jsonObject1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id1))
                .andExpect(jsonPath("$.name").value(name1))
                .andExpect(jsonPath("$.age").value(age1));
    }

    @Test
    public void readStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .content(jsonObject1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id1))
                .andExpect(jsonPath("$.name").value(name1))
                .andExpect(jsonPath("$.age").value(age1));
    }

    @Test
    public void updateStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(jsonObject1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id1))
                .andExpect(jsonPath("$.name").value(name1))
                .andExpect(jsonPath("$.age").value(age1));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1")
                        .content(jsonObject1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentsByAgeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student?age=20")
                        .content(jsonObject1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].name").value(name1))
                .andExpect(jsonPath("$[0].age").value(age1))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].age").value(age2));
    }

    @Test
    public void getStudentsByAgeBetweenTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age/between?min=19&max=21")
                        .content(jsonObject1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].name").value(name1))
                .andExpect(jsonPath("$[0].age").value(age1))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].age").value(age2));
    }

    @Test
    public void getStudentsByFacultyIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/byFacultyId?facultyId=1")
                        .content(jsonObject1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].name").value(name1))
                .andExpect(jsonPath("$[0].age").value(age1))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].age").value(age2));
    }
}
