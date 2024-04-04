DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS business;
DROP TABLE IF EXISTS users;
--DROP TYPE IF EXISTS serviceCategory;
--DROP TYPE IF EXISTS pricingType;

CREATE TABLE IF NOT EXISTS users (
    userid SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telephone VARCHAR(255),
    password VARCHAR(255) NOT NULL DEFAULT null, --todo: sacar el default null una vez que se implemente el login
    isprovider boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS business(
  id SERIAL PRIMARY KEY,
  userid INT references users(userid) ON DELETE CASCADE,
  businessname VARCHAR(255),
  businessTelephone VARCHAR(255),
  businessEmail VARCHAR(255),
  businessLocation VARCHAR(255)
);

--CREATE TYPE serviceCategory AS ENUM ('Limpieza', 'Belleza', 'Arreglos calificados', 'Mascotas', 'Exteriores', 'Eventos y Celebraciones', 'Transporte', 'Consultoria', 'Salud');
--CREATE TYPE pricingType AS ENUM ('Per hour', 'Per total', 'Budget', 'TBD');

CREATE TABLE IF NOT EXISTS services (
    id SERIAL PRIMARY KEY,
    businessid INT REFERENCES business(id) ON DELETE CASCADE,
    servicename VARCHAR(255) NOT NULL,
    servicedescription VARCHAR(255),
    homeservice BOOLEAN,
    location VARCHAR(255) NOT NULL,
    category  VARCHAR(50) CHECK (category IN ('Limpieza', 'Belleza', 'Arreglos calificados', 'Mascotas', 'Exteriores', 'Eventos y Celebraciones', 'Transporte', 'Consultoria', 'Salud')),
    minimalduration INT,
    pricingtype  VARCHAR(50) CHECK (pricingtype IN ('Per Hour', 'Per Total', 'Budget', 'TBD')),
    price VARCHAR(255),
    additionalcharges BOOLEAN,
    imageurl VARCHAR(255)
    );

