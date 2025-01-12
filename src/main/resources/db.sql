CREATE DATABASE resto_manager CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE dish (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    image VARCHAR(255) NOT NULL UNIQUE,
    is_vegetarian BOOLEAN NOT NULL,
    category VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE utf8mb4_unicode_ci;

CREATE TABLE restaurant_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_number INT NOT NULL UNIQUE,
    is_vacant BOOLEAN NOT NULL,
    capacity INT NOT NULL
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE utf8mb4_unicode_ci;