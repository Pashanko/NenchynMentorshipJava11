CREATE MATERIALIZED VIEW students_snapshot AS
SELECT s.name, s.surname, sub.subject_name, er.mark
FROM students s
         INNER JOIN exam_result er ON s.id = er.student_id
         INNER JOIN subjects sub ON sub.id = er.subject_id;

SELECT *
FROM students_snapshot;

UPDATE students
SET name = 'Updated Name_1'
WHERE name = 'Updated Name'
  AND surname = 'Surname';

SELECT s.name, s.surname, sub.subject_name, er.mark
FROM students s
         INNER JOIN exam_result er ON s.id = er.student_id
         INNER JOIN subjects sub ON sub.id = er.subject_id;