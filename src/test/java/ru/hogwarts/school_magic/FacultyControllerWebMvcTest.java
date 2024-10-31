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
import ru.hogwarts.school_magic.controller.FacultyController;
import ru.hogwarts.school_magic.model.Faculty;
import ru.hogwarts.school_magic.model.Student;
import ru.hogwarts.school_magic.repository.FacultyRepository;
import ru.hogwarts.school_magic.service.FacultyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    String name1;
    String color1;
    long id1;

    String name2;
    String color2;
    long id2;

    JSONObject jsonObject1;

    Faculty faculty1;

    Faculty faculty2;

    JSONObject jsonObject2;

    List<Faculty> faculties;

    JSONObject jsonObject3;

    @BeforeEach
    public void setUp() throws Exception {
        name1 = "Name1";
        color1 = "Black";
        id1 = 1L;

        name2 = "Name2";
        color2 = "Black";
        id2 = 2L;

        jsonObject1 = new JSONObject();
        jsonObject1.put(name1, color1);

        faculty1 = new Faculty();
        faculty1.setId(id1);
        faculty1.setName(name1);
        faculty1.setColor(color1);

        jsonObject2 = new JSONObject();
        jsonObject2.put(name2, color2);

        faculty2 = new Faculty();
        faculty2.setId(id2);
        faculty2.setName(name2);
        faculty2.setColor(color2);

        jsonObject3 = new JSONObject();
        jsonObject3.put(name1, color1);
        jsonObject3.put(name2, color2);

        faculties = new ArrayList<>(List.of(faculty1, faculty2));


        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty1);
        when(facultyRepository.findById(any(Integer.class))).thenReturn(Optional.of(faculty1));
        when(facultyRepository.findFacultyByColor(any(String.class))).thenReturn(faculties);
        when(facultyRepository.findFacultyByColorOrNameIgnoreCase(any(String.class), any(String.class))).thenReturn(faculties);
        when(facultyRepository.findFacultyByStudentsContains(any(Student.class))).thenReturn(faculty1);
    }

    @Test
    public void createFacultyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(jsonObject1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id1))
                .andExpect(jsonPath("$.name").value(name1))
                .andExpect(jsonPath("$.color").value(color1));
    }

    @Test
    public void readFacultyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1")
                        .content(jsonObject1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id1))
                .andExpect(jsonPath("$.name").value(name1))
                .andExpect(jsonPath("$.color").value(color1));
    }

    @Test
    public void updateFacultyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(jsonObject1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id1))
                .andExpect(jsonPath("$.name").value(name1))
                .andExpect(jsonPath("$.color").value(color1));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/1")
                        .content(jsonObject1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getFacultyByColorTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?color=Black")
                        .content(jsonObject3.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].name").value(name1))
                .andExpect(jsonPath("$[0].color").value(color1))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].color").value(color2));
    }

    @Test
    public void getFacultyByColorOrNameIgnoreCaseTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/colorOrName?color=Black&name=")
                        .content(jsonObject3.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].name").value(name1))
                .andExpect(jsonPath("$[0].color").value(color1))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].color").value(color2));
    }

    @Test
    public void getFacultyByStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/byStudent")
                        .content(jsonObject1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id1))
                .andExpect(jsonPath("$.name").value(name1))
                .andExpect(jsonPath("$.color").value(color1));
    }
}
