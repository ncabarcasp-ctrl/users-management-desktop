SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS crud_usuarios
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE crud_usuarios;

-- ------------------------------------------------------------
-- Tabla radios
-- ------------------------------------------------------------
DROP TABLE IF EXISTS radios;

CREATE TABLE radios (
    id                 VARCHAR(36)   NOT NULL PRIMARY KEY,
    name               VARCHAR(100)  NOT NULL,
    frequency          DECIMAL(8,3)  NOT NULL,
    transmission_type  ENUM('AM', 'FM') NOT NULL,
    created_at         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_radios_frequency CHECK (frequency > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

LOCK TABLES radios WRITE;
INSERT INTO radios (id, name, frequency, transmission_type) VALUES
    ('1b6f0a52-3c1e-4d8a-9a53-1f0b7a2d9c01', 'Tropicana',     98.500, 'FM'),
    ('7c2e9d14-5b8a-4f3e-8d21-6a4c0e9b3f02', 'Caracol Radio', 1010.000, 'AM'),
    ('a93d4e07-2f6b-4c18-b5e9-0d7a1c8e4f03', 'La FM',         94.900, 'FM');
UNLOCK TABLES;

-- ------------------------------------------------------------
-- Tabla users
-- ------------------------------------------------------------
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        ENUM('ADMIN', 'MEMBER', 'REVIEWER') NOT NULL,
    status      ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'BLOCKED') NOT NULL DEFAULT 'PENDING',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Usuario administrador inicial (el hash es un marcador, no una clave real)
LOCK TABLES users WRITE;
INSERT INTO users (id, name, email, password, role, status) VALUES
    ('00000000-0000-0000-0000-000000000001', 'Administrador', 'admin@example.com',
     '$2a$12$placeholderHashReplaceWithRealBCryptHash', 'ADMIN', 'ACTIVE');
UNLOCK TABLES;
