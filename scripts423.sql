SELECT
    student.name AS student_name,
    student.age AS student_age,
    faculty.name AS faculty_name
FROM
    student
        JOIN
    faculty ON student.faculty_id = faculty.id;

SELECT
    student.name AS student_name,
    student.age AS student_age
FROM
    student
        JOIN
    avatar ON student.id = avatar.student_id;
