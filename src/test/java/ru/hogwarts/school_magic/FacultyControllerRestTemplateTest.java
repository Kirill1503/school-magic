package ru.hogwarts.school_magic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school_magic.model.Faculty;
import ru.hogwarts.school_magic.repository.FacultyRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FacultyControllerRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    Faculty faculty;

    @BeforeEach
    public void setUp() {
        faculty = new Faculty("AAA", "RED");
    }

    @AfterEach
    public void tearDown() {
        facultyRepository.deleteAll();
    }

    @Test
    public void contextLoads() {
        assertThat(facultyRepository).isNotNull();
    }

    @Test
    public void createFacultyTest() {
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                faculty,
                Faculty.class);
        assertNotNull(facultyResponseEntity);
        assertThat(facultyResponseEntity.getStatusCode().value()).isEqualTo(200);


        assertThat(facultyResponseEntity.getBody().getName()).isEqualTo("AAA");
        assertThat(facultyResponseEntity.getBody().getColor()).isEqualTo("RED");
    }

    @Test
    public void readFacultyTest() {
        faculty = facultyRepository.save(faculty);
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.getForEntity(
                "http://localhost:"
                        + port + "/faculty/"
                        + faculty.getId(), Faculty.class);

        assertNotNull(facultyResponseEntity);
        assertThat(facultyResponseEntity.getStatusCode().value()).isEqualTo(200);

        assertThat(facultyResponseEntity.getBody().getName()).isEqualTo("AAA");
        assertThat(facultyResponseEntity.getBody().getColor()).isEqualTo("RED");
    }

    @Test
    public void updateFacultyTest() {
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                faculty,
                Faculty.class
        );

        assertEquals(HttpStatus.OK, facultyResponseEntity.getStatusCode());
        Faculty createdFaculty = facultyResponseEntity.getBody();
        assertNotNull(createdFaculty);
        Long facultyId = createdFaculty.getId();
        assertNotNull(facultyId);

        createdFaculty.setColor("BLACK");

        String putUrl = "http://localhost:" + port + "/faculty";
        restTemplate.put(putUrl, createdFaculty);

        ResponseEntity<Faculty> updatedResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + facultyId,
                Faculty.class
        );

        assertEquals(HttpStatus.OK, updatedResponseEntity.getStatusCode());
        Faculty updatedFaculty = updatedResponseEntity.getBody();
        assertNotNull(updatedFaculty);
        assertEquals("BLACK", createdFaculty.getColor());
        assertEquals(facultyId, createdFaculty.getId());
    }

    @Test
    public void deleteFacultyTest() {
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                faculty,
                Faculty.class
        );

        assertEquals(HttpStatus.OK, facultyResponseEntity.getStatusCode());
        Faculty createdFaculty = facultyResponseEntity.getBody();
        assertNotNull(createdFaculty);
        Long facultyId = createdFaculty.getId();
        assertNotNull(facultyId);

        String deleteUrl = "http://localhost:" + port + "/faculty/" + facultyId;
        restTemplate.delete(deleteUrl);

        ResponseEntity<Faculty> updatedResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + facultyId,
                Faculty.class
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, updatedResponseEntity.getStatusCode());
        Faculty updatedFaculty = updatedResponseEntity.getBody();
        assertThat(updatedFaculty.getId()).isEqualTo(null);
        assertThat(updatedFaculty.getName()).isEqualTo(null);
        assertThat(updatedFaculty.getColor()).isEqualTo(null);
    }

    @Test
    public void getFacultyByColorTest() {
        Faculty faculty1 = new Faculty("AAA", "BLUE");
        Faculty faculty2 = new Faculty("BBB", "BLUE");

        ResponseEntity<Faculty> facultyResponseEntity1 = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                faculty1,
                Faculty.class
        );

        Faculty facultyGetBody1 = facultyResponseEntity1.getBody();

        ResponseEntity<Faculty> facultyResponseEntity2 = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                faculty2,
                Faculty.class
        );

        Faculty facultyGetBody2 = facultyResponseEntity2.getBody();

        assertEquals(HttpStatus.OK, facultyResponseEntity1.getStatusCode());
        assertEquals(HttpStatus.OK, facultyResponseEntity2.getStatusCode());

        String color = "BLUE";

        HttpEntity<String> httpEntity = new HttpEntity<>(color);

        ResponseEntity<List<Faculty>> listResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty?color=" + color,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<List<Faculty>>() {
                });


        assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
        List<Faculty> facultyList = listResponseEntity.getBody();
        assertNotNull(facultyList);
        assertEquals(2, facultyList.size());
        assertThat(facultyList.contains(facultyGetBody1)).isTrue();
        assertThat(facultyList.contains(facultyGetBody2)).isTrue();
    }

    @Test
    public void getFacultyByColorOrNameTest() {
        Faculty faculty1 = new Faculty("AAA", "BLUE");
        Faculty faculty2 = new Faculty("BBB", "BLUE");

        ResponseEntity<Faculty> facultyResponseEntity1 = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                faculty1,
                Faculty.class
        );

        ResponseEntity<Faculty> facultyResponseEntity2 = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                faculty2,
                Faculty.class
        );

        assertEquals(HttpStatus.OK, facultyResponseEntity1.getStatusCode());
        assertEquals(HttpStatus.OK, facultyResponseEntity2.getStatusCode());

        String colorOrName1 = "blue";
        String colorOrName2 = "aaa";

        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.APPLICATION_JSON);

        HttpHeaders headers2 = new HttpHeaders();
        headers2.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<List<Faculty>> listResponseEntity1 = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/colorOrName?color=" + colorOrName1,
                HttpMethod.GET,
                new HttpEntity<>(headers1),
                new ParameterizedTypeReference<List<Faculty>>() {
                });

        ResponseEntity<List<Faculty>> listResponseEntity2 = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/colorOrName?name=" + colorOrName2,
                HttpMethod.GET,
                new HttpEntity<>(headers2),
                new ParameterizedTypeReference<List<Faculty>>() {
                });


        assertEquals(HttpStatus.OK, listResponseEntity1.getStatusCode());
        assertEquals(HttpStatus.OK, listResponseEntity2.getStatusCode());
        List<Faculty> facultyList1 = listResponseEntity1.getBody();
        List<Faculty> facultyList2 = listResponseEntity2.getBody();
        assertNotNull(facultyList1);
        assertNotNull(facultyList2);
    }
}
