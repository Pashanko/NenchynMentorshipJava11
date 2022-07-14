CREATE TABLE IF NOT EXISTS students
(
    id               SERIAL      NOT NULL PRIMARY KEY,
    name             VARCHAR(45) NOT NULL CHECK (name !~ '.*[@*].*'),
    surname          VARCHAR(45) NOT NULL CHECK (name !~ '.*[@*].*'),
    date_of_birth    DATE        NOT NULL,
    phone_number     VARCHAR(14),
    primary_skill    VARCHAR(45),
    created_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS subjects
(
    id           SERIAL NOT NULL PRIMARY KEY,
    subject_name VARCHAR(45) NOT NULL,
    tutor        VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS exam_result
(
    id         SERIAL NOT NULL PRIMARY KEY,
    mark       INTEGER,
    student_id INTEGER REFERENCES students (id),
    subject_id INTEGER REFERENCES subjects (id)
);