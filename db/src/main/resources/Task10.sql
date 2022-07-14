CREATE FUNCTION get_student_at_red_zone()
    RETURNS TABLE
            (
                student_name    VARCHAR,
                student_surname VARCHAR
            )
    LANGUAGE 'plpgsql'
AS
$$

BEGIN
    RETURN QUERY
        SELECT s.name, s.surname
        FROM students s
                 INNER JOIN exam_result er ON s.id = er.subject_id
        WHERE er.mark <= 3
        GROUP BY s.name, s.surname
        HAVING COUNT(er.mark) >= 2;

END ;
$$;

SELECT *
FROM get_student_at_red_zone();