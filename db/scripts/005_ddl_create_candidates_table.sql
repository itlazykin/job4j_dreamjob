CREATE TABLE candidates
(
        id serial PRIMARY KEY,
        name VARCHAR NOT NULL,
        description VARCHAR NOT NULL,
        creation_date TIMESTAMP,
        city_id INT REFERENCES cities(id),
        file_id INT REFERENCES files(id)
);