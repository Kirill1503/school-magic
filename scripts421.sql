ALTER TABLE student
    ADD CONSTRAINT check_students_age CHECK (age >= 16);

ALTER TABLE student
    ADD CONSTRAINT unique_students_name UNIQUE (name);

ALTER TABLE student
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE faculty
    ADD CONSTRAINT unique_faculties_name_and_color UNIQUE (name, color);

ALTER TABLE student
    ALTER COLUMN age SET DEFAULT 20;