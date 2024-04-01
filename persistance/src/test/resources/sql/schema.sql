--CONSULTAR: como compartir archivos schema.sql en test y main, problemas de incompatibilidad de sintaxis
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
                                       businessid SERIAL PRIMARY KEY,
                                       userid INT references users(userid) ON DELETE CASCADE,
                                       businessname VARCHAR(255),
                                       businessTelephone VARCHAR(255),
                                       businessEmail VARCHAR(255),
                                       businessLocation VARCHAR(255)
);