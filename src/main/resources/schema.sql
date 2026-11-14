CREATE DATABASE IF NOT EXISTS crud_usuarios
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE crud_usuarios;

CREATE TABLE IF NOT EXISTS users (
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        ENUM('ADMIN', 'MEMBER', 'REVIEWER') NOT NULL,
    status      ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'BLOCKED') NOT NULL DEFAULT 'PENDING',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS radios (
    id                 VARCHAR(36)   NOT NULL PRIMARY KEY,
    name               VARCHAR(100)  NOT NULL,
    frequency          DECIMAL(8,3)  NOT NULL,
    transmission_type  ENUM('AM', 'FM') NOT NULL,
    created_at         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_radios_frequency CHECK (frequency > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO users (id, name, email, password, role, status)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'Administrador',
    'admin@example.com',
    '$2a$12$placeholderHashReplaceWithRealBCryptHash',
    'ADMIN',
    'ACTIVE'
);