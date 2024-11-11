-- liquibase formatted sql

-- changeset kkatyshev:1
CREATE TABLE students
(
    id   SERIAL PRIMARY KEY,
    name TEXT,
    age  INTEGER
);


CREATE TABLE faculties
(
    id    SERIAL PRIMARY KEY,
    name  TEXT,
    color TEXT
);

CREATE INDEX index_for_students ON students (name);

CREATE INDEX index_for_faculties ON faculties (name, color);
