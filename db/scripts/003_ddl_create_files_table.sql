CREATE TABLE files
(
    id serial PRIMARY KEY,
    name VARCHAR NOT NULL,
    path VARCHAR NOT NULL UNIQUE
);