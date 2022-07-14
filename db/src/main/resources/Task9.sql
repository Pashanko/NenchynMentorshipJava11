CREATE FUNCTION get_average_mark(func_subj_name TEXT)
    RETURNS FLOAT
    LANGUAGE 'plpgsql'
AS
$$
DECLARE
    average_mark FLOAT;

BEGIN
    SELECT AVG(er.mark)
    INTO average_mark
    FROM subjects s
             INNER JOIN exam_result er ON s.id = er.subject_id
    WHERE s.subject_name = func_subj_name
    GROUP BY s.id;

    RETURN average_mark;
END;
$$;

SELECT get_average_mark('Subject_1')