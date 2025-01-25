CREATE DATABASE resto_manager CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE dish (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    image VARCHAR(255),
    is_vegetarian BOOLEAN NOT NULL,
    category VARCHAR(255) NOT NULL,
    is_deleted boolean not null,
    deleted_at varchar(50),
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

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id BIGINT NOT NULL,
    total_amount DOUBLE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    is_paid BOOLEAN NOT NULL,
    FOREIGN KEY (table_id) REFERENCES restaurant_table(id)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE utf8mb4_unicode_ci;

CREATE TABLE order_dishes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    dish_id BIGINT NOT NULL,
    dish_quant INT NOT NULL,
    dish_name VARCHAR(255) NOT NULL,
    dish_price double NOT NULL,
    dish_total_amount double NOT NULL
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE utf8mb4_unicode_ci;
