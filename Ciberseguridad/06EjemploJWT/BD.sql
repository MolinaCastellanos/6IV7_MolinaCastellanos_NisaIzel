CREATE DATABASE jwt_db;
USE jwt_db;

CREATE TABLE usuarios(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    nombre varchar(35) not null,
    segundo_nombre varchar(35) null,
    apellido_paterno varchar(35) not null,
    apellido_materno varchar(35) not null,
    password VARCHAR(255) NOT NULL
);