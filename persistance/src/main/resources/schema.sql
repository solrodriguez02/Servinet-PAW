DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS business;
DROP TABLE IF EXISTS users;

--DROP TYPE IF EXISTS serviceCategory;
--DROP TYPE IF EXISTS pricingType;

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

--CREATE TYPE serviceCategory AS ENUM ('Limpieza', 'Belleza', 'Arreglos calificados', 'Mascotas', 'Exteriores', 'Eventos y Celebraciones', 'Transporte', 'Consultoria', 'Salud');
--CREATE TYPE pricingType AS ENUM ('Per hour', 'Per total', 'Budget', 'TBD');

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

CREATE OR REPLACE FUNCTION set_defaults() RETURNS trigger AS '
BEGIN
    IF NEW.businessname IS NULL THEN
        NEW.businessname := (SELECT username FROM users WHERE userid = NEW.userid);
    END IF;
    IF NEW.businessTelephone IS NULL THEN
        NEW.businessTelephone := (SELECT telephone FROM users WHERE userid = NEW.userid);
    END IF;
    IF NEW.businessEmail IS NULL THEN
        NEW.businessEmail := (SELECT email FROM users WHERE userid = NEW.userid);
    END IF;
    RETURN NEW;
END;
' LANGUAGE plpgsql;


CREATE TRIGGER set_defaults BEFORE INSERT ON business FOR EACH ROW EXECUTE FUNCTION set_defaults();

CREATE TABLE IF NOT EXISTS appointments (
    appointmentid SERIAL PRIMARY KEY,
    serviceid INT REFERENCES services ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    userid INT REFERENCES users ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    startDate TIMESTAMP NOT NULL,
    endDate TIMESTAMP,
    confirmed BOOLEAN DEFAULT FALSE
);




-- HARDCODIE INSERTS, BORRARRRRRRR {
INSERT INTO users (userid, username, password, name, surname, email, telephone) VALUES (1000, 'username', 'password', 'name', 'surname', 'email', 'telephone');
INSERT INTO business VALUES (1, 1000, 'businessname', 'businessTelephone', 'peluqueria@gmail.com', 'businessLocation');
INSERT INTO services VALUES (1,1,'Peluqueria Ramon','Veni, peinate y divertite!',false,'Recoleta','Belleza',60,'Per Hour',5000,true);
-- } NO OLVIDAR DE BORRAR