DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
    userid SERIAL PRIMARY KEY,
    prueba varchar(5),
    username VARCHAR(255) NOT NULL UNIQUE
);