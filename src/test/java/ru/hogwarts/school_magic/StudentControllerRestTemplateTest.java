package ru.hogwarts.school_magic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school_magic.model.Student;
import ru.hogwarts.school_magic.repository.StudentRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    Student student;

    @BeforeEach
    public void setUp() {
        student = new Student("AAA", 18);
    }

    @AfterEach
    public void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    public void contextLoads() {
        Assertions.assertThat(studentRepository).isNotNull();
    }

    @Test
    public void createStudentTest() {
        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student,
                Student.class);
        assertNotNull(studentResponseEntity);
        Assertions.assertThat(studentResponseEntity.getStatusCode().value()).isEqualTo(200);


        Assertions.assertThat(studentResponseEntity.getBody().getName()).isEqualTo("AAA");
        Assertions.assertThat(studentResponseEntity.getBody().getAge()).isEqualTo(18);
    }

    @Test
    public void readStudentTest() {
        student = studentRepository.save(student);
        ResponseEntity<Student> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:"
                        + port + "/student/"
                        + student.getId(), Student.class);

        assertNotNull(studentResponseEntity);
        Assertions.assertThat(studentResponseEntity.getStatusCode().value()).isEqualTo(200);

        Assertions.assertThat(studentResponseEntity.getBody().getName()).isEqualTo("AAA");
        Assertions.assertThat(studentResponseEntity.getBody().getAge()).isEqualTo(18);
    }

    @Test
    public void updateStudentTest() {
        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        Student createdStudent = studentResponseEntity.getBody();
        assertNotNull(createdStudent);
        Long studentId = createdStudent.getId();
        assertNotNull(studentId);

        createdStudent.setAge(20);

        String putUrl = "http://localhost:" + port + "/student";
        restTemplate.put(putUrl, createdStudent);

        ResponseEntity<Student> updatedResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + studentId,
                Student.class
        );

        assertEquals(HttpStatus.OK, updatedResponseEntity.getStatusCode());
        Student updatedStudent = updatedResponseEntity.getBody();
        assertNotNull(updatedStudent);
        assertEquals(20, createdStudent.getAge());
        assertEquals(studentId, createdStudent.getId());
    }

    @Test
    public void deleteStudentTest() {
        ResponseEntity<Student> facultyResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        assertEquals(HttpStatus.OK, facultyResponseEntity.getStatusCode());
        Student createdStudent = facultyResponseEntity.getBody();
        assertNotNull(createdStudent);
        Long studentId = createdStudent.getId();
        assertNotNull(studentId);

        String deleteUrl = "http://localhost:" + port + "/student/" + studentId;
        restTemplate.delete(deleteUrl);

        ResponseEntity<Student> updatedResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + studentId,
                Student.class
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, updatedResponseEntity.getStatusCode());
        Student updatedStudent = updatedResponseEntity.getBody();
        Assertions.assertThat(updatedStudent.getId()).isEqualTo(null);
        Assertions.assertThat(updatedStudent.getName()).isEqualTo(null);
        Assertions.assertThat(updatedStudent.getAge()).isEqualTo(0);
    }



    @Test
    public void getStudentByAgeTest() {
        Student student1 = new Student("AAA", 18);
        Student student2 = new Student("BBB", 18);

        ResponseEntity<Student> studentResponseEntity1 = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student1,
                Student.class
        );

        Student studentGetBody1 = studentResponseEntity1.getBody();

        ResponseEntity<Student> studentResponseEntity2 = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student2,
                Student.class
        );

        Student studentGetBody2 = studentResponseEntity2.getBody();

        assertEquals(HttpStatus.OK, studentResponseEntity1.getStatusCode());
        assertEquals(HttpStatus.OK, studentResponseEntity2.getStatusCode());

        var age = 18;

        ResponseEntity<List<Student>> listResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student?age=" + age,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<Student>>() {
                });


        assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
        List<Student> studentList = listResponseEntity.getBody();
        assertNotNull(studentList);
        assertEquals(2, studentList.size());
        Assertions.assertThat(studentList.contains(studentGetBody1)).isTrue();
        Assertions.assertThat(studentList.contains(studentGetBody2)).isTrue();
    }

    @Test
    public void getStudentByAgeBetweenTest() {
        // Создаем объекты Student с разными возрастами
        Student student1 = new Student("AAA", 14);
        Student student2 = new Student("BBB", 25);
        Student student3 = new Student("CCC", 30);

        // Сохраняем объекты Student через POST-запросы
        ResponseEntity<Student> responseEntity1 = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student1,
                Student.class
        );

        ResponseEntity<Student> responseEntity2 = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student2,
                Student.class
        );

        ResponseEntity<Student> responseEntity3 = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student3,
                Student.class
        );

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity3.getStatusCode());

        int minAge = 10;
        int maxAge = 25;

        ResponseEntity<List<Student>> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/age/between?min=" + minAge + "&max=" + maxAge,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {}
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Student> studentsInRange = responseEntity.getBody();
        assertNotNull(studentsInRange);

        assertTrue(studentsInRange.stream().anyMatch(s -> s.getName().equals("AAA") && s.getAge() == 14));
        assertTrue(studentsInRange.stream().anyMatch(s -> s.getName().equals("BBB") && s.getAge() == 25));
        assertFalse(studentsInRange.stream().anyMatch(s -> s.getName().equals("CCC")));
    }
}
