CREATE TABLE task (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    base DECIMAL(19),
    exponent DECIMAL(19),
    status VARCHAR,
    result DECIMAL(100),
    progress INTEGER
);