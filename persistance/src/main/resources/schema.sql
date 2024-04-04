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

insert into users(username, name, surname, email, telephone, password, isprovider) values ('solro', 'sol', 'rodri', 'solrodriguezgiana@gmail.com', '113452343', 'solro', true);

insert into business (userid, businessname, businessTelephone, businessEmail, businessLocation) values (1, 'Sol nails shop', '11365335', 'solrodriguezgiana@gmail.com', 'Palermo');

insert into services(businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges) values (1, 'Uñas capping', 'Servicio de uñas, multiples colores y esmaltes de todo tipo. Diseño a eleccion del cliente. Arte en uñas. Consulte por disponibilidad.', FALSE, 'Palermo', 'Belleza', 60, 'Per Total', '10000', TRUE);

insert into services(businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges) values (1, 'Uñas francecita', 'Servicio de uñas francesitas.', FALSE, 'Palermo', 'Belleza', 60, 'Per Total', '5000', TRUE);

insert into services(businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges) values (1, 'Limpieza de cuticula', 'Servicio de uñas: limpieza de cuticula express.', FALSE, 'Palermo', 'Belleza', 60, 'Per Total', '3000', TRUE);

insert into services(businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges, imageurl) values (1, 'Ya no se que otro servicio inventar de uñas', 'Servicio de uñas: necesito que este texto sea largo. Chatgpt: La vida es un viaje lleno de sorpresas y aventuras. Cada día es una oportunidad para explorar, aprender y crecer. .', TRUE, 'Palermo', 'Belleza', 60, 'Per Total', '3000', FALSE, 'https://www.minutoneuquen.com/u/fotografias/m/2023/5/28/f1280x720-605218_736893_5050.jpg');

insert into services(businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges) values (1, 'Uñas capping1', 'Servicio de uñas, multiples colores y esmaltes de todo tipo. Diseño a eleccion del cliente. Arte en uñas. Consulte por disponibilidad.', FALSE, 'Palermo', 'Belleza', 60, 'Per Total', '10000', TRUE);
insert into services(businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges) values (1, 'Uñas capping2', 'Servicio de uñas, multiples colores y esmaltes de todo tipo. Diseño a eleccion del cliente. Arte en uñas. Consulte por disponibilidad.', FALSE, 'Palermo', 'Limpieza', 60, 'Per Total', '10000', TRUE);
insert into services(businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges) values (1, 'Uñas capping3', 'Servicio de uñas, multiples colores y esmaltes de todo tipo. Diseño a eleccion del cliente. Arte en uñas. Consulte por disponibilidad.', FALSE, 'Palermo', 'Belleza', 60, 'Per Total', '10000', TRUE);
insert into services(businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges) values (1, 'Uñas capping4', 'Servicio de uñas, multiples colores y esmaltes de todo tipo. Diseño a eleccion del cliente. Arte en uñas. Consulte por disponibilidad.', FALSE, 'Palermo', 'Belleza', 60, 'Per Total', '10000', TRUE);
insert into services(businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges) values (1, 'Uñas capping5', 'Servicio de uñas, multiples colores y esmaltes de todo tipo. Diseño a eleccion del cliente. Arte en uñas. Consulte por disponibilidad.', FALSE, 'Palermo', 'Belleza', 60, 'Per Total', '10000', TRUE);
insert into services(businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges) values (1, 'Uñas capping6', 'Servicio de uñas, multiples colores y esmaltes de todo tipo. Diseño a eleccion del cliente. Arte en uñas. Consulte por disponibilidad.', FALSE, 'Palermo', 'Belleza', 60, 'Per Total', '10000', TRUE);
insert into services(businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges) values (1, 'Uñas capping7', 'Servicio de uñas, multiples colores y esmaltes de todo tipo. Diseño a eleccion del cliente. Arte en uñas. Consulte por disponibilidad.', FALSE, 'Palermo', 'Belleza', 60, 'Per Total', '10000', TRUE);
insert into services(businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges) values (1, 'Uñas capping8', 'Servicio de uñas, multiples colores y esmaltes de todo tipo. Diseño a eleccion del cliente. Arte en uñas. Consulte por disponibilidad.', FALSE, 'Palermo', 'Belleza', 60, 'Per Total', '10000', TRUE);
insert into services(businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges) values (1, 'Uñas capping9', 'Servicio de uñas, multiples colores y esmaltes de todo tipo. Diseño a eleccion del cliente. Arte en uñas. Consulte por disponibilidad.', FALSE, 'Palermo', 'Limpieza', 60, 'Per Total', '10000', TRUE);
insert into services(businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges) values (1, 'Uñas capping10', 'Servicio de uñas, multiples colores y esmaltes de todo tipo. Diseño a eleccion del cliente. Arte en uñas. Consulte por disponibilidad.', FALSE, 'Palermo', 'Limpieza', 60, 'Per Total', '10000', TRUE);
insert into services(businessid, servicename, servicedescription, homeservice, location, category, minimalduration, pricingtype, price, additionalcharges) values (1, 'Uñas capping11', 'Servicio de uñas, multiples colores y esmaltes de todo tipo. Diseño a eleccion del cliente. Arte en uñas. Consulte por disponibilidad.', FALSE, 'Palermo', 'Limpieza', 60, 'Per Total', '10000', TRUE);
