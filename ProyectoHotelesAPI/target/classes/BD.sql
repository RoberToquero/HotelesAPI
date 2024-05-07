DROP DATABASE IF EXISTS api_hoteles;
CREATE DATABASE api_hoteles;
USE api_hoteles;

CREATE TABLE Hotel (
                       id_Hotel INT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(100) NOT NULL,
                       descripcion VARCHAR (100),
                       categoria VARCHAR(1),
                       tiene_piscina BOOLEAN,
                       localidad VARCHAR(50) NOT NULL
);

INSERT INTO Hotel VALUES (1,'Olid','Centro','4',true,'Valladolid'),
                         (2,'Paris', 'Centro','3',false,'Valladolid'),
                         (3,'Lasa Sport', 'Deportivo','4',true,'Valladolid'),
                         (4,'Felipe IV', 'Centro','2',false,'Valladolid');

CREATE TABLE Habitacion (
                            id_Habitacion INT PRIMARY KEY,
                            id_Hotel INT,
                            capacidad int,
                            precio_Noche DECIMAL(10, 2),
                            incluye_Desayuno BOOLEAN,
                            ocupada BOOLEAN,
                            FOREIGN KEY (id_Hotel) REFERENCES Hotel(id_Hotel)
);

INSERT INTO Habitacion (id_Habitacion, id_Hotel, capacidad, precio_Noche, incluye_Desayuno, ocupada)
VALUES
    (101, 1, 1, 150.00, true, false),
    (102, 1, 2, 100.00, false, false),
    (201, 2, 1, 300.00, true, true),
    (202, 2, 2, 280.00, false, false),
    (301, 3, 1, 80.00, true, false),
    (302, 4, 2, 120.00, false, true);