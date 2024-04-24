--CONSULTAR: como compartir archivos schema.sql en test y main, problemas de incompatibilidad de sintaxis

/*

--------------- VERSION ANTERIOR, FIJARSE DE CAMBIAR LOS TESTS EN BASE AL NUEVO SCHEMA -------------------


DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS business;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
                                     userid SERIAL PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE,
            password VARCHAR(255) NOT NULL, --todo: sacar el default null una vez que se implemente el login
                                     name VARCHAR(255) NOT NULL,
                                     surname VARCHAR(255) NOT NULL,
                                     email VARCHAR(255) NOT NULL UNIQUE,
                                     telephone VARCHAR(255),
                                     isprovider boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS business(
                                       businessid SERIAL PRIMARY KEY,
                                       userid INT references users(userid) ON DELETE CASCADE,
                                       businessname VARCHAR(255),
                                       businessTelephone VARCHAR(255),
                                       businessEmail VARCHAR(255),
                                       businessLocation VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS services (
                                        id SERIAL PRIMARY KEY,
                                        businessid INT REFERENCES business(businessid) ON DELETE CASCADE,
    servicename VARCHAR(255) NOT NULL,
    servicedescription VARCHAR(255),
    homeservice BOOLEAN,
    location VARCHAR(255) NOT NULL,
    category  VARCHAR(50) CHECK (category IN ('Limpieza', 'Belleza', 'Arreglos calificados', 'Mascotas', 'Exteriores', 'Eventos y Celebraciones', 'Transporte', 'Consultoria', 'Salud')),
    minimalduration INT,
    pricingtype  VARCHAR(50) CHECK (pricingtype IN ('Per Hour', 'Per Total', 'Budget', 'TBD')),
    price VARCHAR(255),
    additionalcharges BOOLEAN
    );

*/


CREATE TABLE IF NOT EXISTS users (
     userid SERIAL PRIMARY KEY,
     username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telephone VARCHAR(255),
    isprovider boolean NOT NULL DEFAULT FALSE
    );

CREATE TABLE IF NOT EXISTS business(
   businessid SERIAL PRIMARY KEY,
   userid INT references users(userid) ON DELETE CASCADE,
    businessname VARCHAR(255),
    businessTelephone VARCHAR(255),
    businessEmail VARCHAR(255),
    businessLocation VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS images(
     imageid SERIAL PRIMARY KEY,
     imageBytes BYTEA[]
);

CREATE TABLE IF NOT EXISTS services (
    id SERIAL PRIMARY KEY,
    businessid INT REFERENCES business(businessid) ON DELETE CASCADE,
    servicename VARCHAR(255) NOT NULL,
    servicedescription VARCHAR(255),
    homeservice BOOLEAN,
    location VARCHAR(255) NOT NULL,
    category  VARCHAR(50) CHECK (category IN ('Limpieza', 'Belleza', 'Arreglos Calificados','Peluqueria', 'Mascotas', 'Exteriores', 'Eventos y Celebraciones', 'Transporte', 'Consultoria', 'Salud')),
    minimalduration INT,
    pricingtype  VARCHAR(50) CHECK (pricingtype IN ('Por hora', 'Total', 'Producto', 'A determinar')),
    price VARCHAR(255),
    additionalcharges BOOLEAN,
    imageId INT references images(imageid),
    neighbourhood VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS appointments (
    appointmentid SERIAL PRIMARY KEY,
    serviceid INT REFERENCES services ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    userid INT REFERENCES users ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    startDate TIMESTAMP NOT NULL,
    endDate TIMESTAMP,
    location VARCHAR(255),
    confirmed BOOLEAN DEFAULT FALSE
    );

CREATE TABLE IF NOT EXISTS questions (
    questionid SERIAL PRIMARY KEY,
    serviceid INT REFERENCES services ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    userid INT REFERENCES users ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    question VARCHAR(255) NOT NULL,
    response VARCHAR(255),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS ratings (
    ratingid SERIAL PRIMARY KEY,
    serviceid INT REFERENCES services ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    userid INT REFERENCES users ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    rating INTEGER CHECK (rating >= 1 AND rating <= 5) NOT NULL,
    comment VARCHAR(255),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );