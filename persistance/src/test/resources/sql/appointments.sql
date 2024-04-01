DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS business;
DROP TABLE IF EXISTS users;


CREATE TABLE IF NOT EXISTS users (
                                     userid SERIAL PRIMARY KEY,
                                     username VARCHAR(30) NOT NULL
);


CREATE TABLE IF NOT EXISTS services (
                                        id SERIAL PRIMARY KEY,
                                        servname VARCHAR(30) NOT NULL
);

CREATE TABLE appointments (
                appointmentid SERIAL PRIMARY KEY,
                serviceid INT REFERENCES services ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
                userid INT REFERENCES users ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
                startDate TIMESTAMP NOT NULL,
                endDate TIMESTAMP,
                confirmed BOOLEAN DEFAULT FALSE
);


