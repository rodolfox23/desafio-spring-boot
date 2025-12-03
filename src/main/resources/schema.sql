CREATE TABLE IF NOT EXISTS usuario (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created TIMESTAMP NOT NULL,
    modified TIMESTAMP NOT NULL,
    last_login TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS phone (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(50) NOT NULL,
    citycode VARCHAR(10) NOT NULL,
    contrycode VARCHAR(10) NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES usuario(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS token (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) UNIQUE,
    token_type VARCHAR(50),
    revoked BOOLEAN,
    expired BOOLEAN,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES usuario(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS estado_tarea (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    estado VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS tarea (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT,
    user_id BIGINT,
    estado_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES usuario(user_id) ON DELETE CASCADE,
    FOREIGN KEY (estado_id) REFERENCES estado_tarea(id)
);

