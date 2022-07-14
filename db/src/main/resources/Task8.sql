CREATE FUNCTION get_average_mark(func_name TEXT, func_surname TEXT)
    RETURNS FLOAT
    LANGUAGE 'plpgsql'
AS
$$
DECLARE
    average_mark FLOAT;

BEGIN
    SELECT AVG(er.mark)
    INTO average_mark
    FROM students s
             INNER JOIN exam_result er ON s.id = er.student_id
    WHERE s.name = func_name
      AND s.surname = func_surname
    GROUP BY s.id;

    RETURN average_mark;
END;
$$;

SELECT get_average_mark('Name_4', 'Surname');