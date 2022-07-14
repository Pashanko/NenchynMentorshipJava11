CREATE OR REPLACE FUNCTION trigger_set_timestamp()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_datetime = NOW();
    RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER set_timestamp
    BEFORE UPDATE
    ON students
    FOR EACH ROW
EXECUTE PROCEDURE
    trigger_set_timestamp();

UPDATE students
SET name = 'Updated Name'
WHERE name = 'Name_4'
  AND surname = 'Surname';