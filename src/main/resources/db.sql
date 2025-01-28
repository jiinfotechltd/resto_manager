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

CREATE TABLE billing (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    amount_paid DOUBLE NOT NULL,
    payment_status VARCHAR(255) NOT NULL,
    table_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE utf8mb4_unicode_ci;

CREATE TABLE users (
    uid                 INT          AUTO_INCREMENT,
    email               VARCHAR(255) NOT NULL,
    password            VARCHAR(255) NOT NULL,
    is_email_verified   BOOLEAN      DEFAULT FALSE,
    profile_pic         VARCHAR(255),
    profile_bg          VARCHAR(255),
    created_at          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    first_name          VARCHAR(100) NOT NULL,
    last_name           VARCHAR(100) NOT NULL,
    is_deleted BOOLEAN  DEFAULT      FALSE,
    deleted_At          TIMESTAMP,
    is_trail_user       BOOLEAN      DEFAULT TRUE,
    contact             VARCHAR(15),
    contact1            VARCHAR(15),
    is_contact_verified BOOLEAN      DEFAULT FALSE,
    is_enable BOOLEAN   DEFAULT      TRUE,

    PRIMARY KEY (uid),
    UNIQUE (email)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE utf8mb4_unicode_ci;