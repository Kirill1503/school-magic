CREATE TABLE Person (
    person_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INTEGER CHECK (age > 18),
    has_license BOOLEAN NOT NULL,
    car_id INT REFERENCES Car(car_id)
);

CREATE TABLE Car (
    car_id SERIAL PRIMARY KEY,
    stamp TEXT NOT NULL,
    model TEXT NOT NULL,
    price NUMERIC(15, 2) CHECK (price >= 0)
);