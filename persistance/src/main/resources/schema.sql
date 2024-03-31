DROP TABLE IF EXISTS business;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS users;

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

CREATE TYPE serviceCategory AS ENUM ('Limpieza', 'Belleza', 'Arreglos calificados', 'Mascotas', 'Exteriores', 'Eventos y Celebraciones', 'Transporte', 'Consultoría', 'Salud');
CREATE TYPE pricingType AS ENUM ('Per hour', 'Per total', 'Budget', 'TBD');

CREATE TABLE IF NOT EXISTS services (
    id SERIAL PRIMARY KEY,
    businessid INT REFERENCES business(id) ON DELETE CASCADE,
    servicename VARCHAR(255) NOT NULL,
    servicedescription VARCHAR(255),
    homeservice BOOLEAN,
    location VARCHAR(255) NOT NULL,
    category serviceCategory NOT NULL,
    minimalduration INT,
    pricingtype pricingType NOT NULL,
    price VARCHAR(255),
    additionalcharges BOOLEAN
    );
