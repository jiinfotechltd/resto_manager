*12/01/2025*  (Swaroop Manade) RM-3/Dish_Entity_Creation

Step-1 : Drop existing Database if present.
 ```
 DROP DATABASE resto_manager;
 ```
Step-2 : Create a Database
```
CREATE DATABASE resto_manager ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE utf8mb4_unicode_ci;
```
Step-3 : Create a Table.
```
CREATE TABLE dish (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    image VARCHAR(255) NOT NULL UNIQUE,
    is_vegetarian BOOLEAN NOT NULL,
    category VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL
) 
ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE utf8mb4_unicode_ci;
```
Deployment Completed

*12/01/2025* (Swaroop Manade) RM-5/Table_Entity_Creation

Step-1 : Table Creation

```
CREATE TABLE restaurant_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, 
    table_number INT NOT NULL UNIQUE,
    is_vacant BOOLEAN NOT NULL,
    capacity INT NOT NULL
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE utf8mb4_unicode_ci;
```

*12/01/2025* (Swaroop Manade) RM-5/Table_Entity_Creation
Step-1: We need to old drop dish table;

```
    DROP TABLE dish;
```
Step-2 : Created new Dish entity table
``` 
    CREATE TABLE dish (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    image VARCHAR(255) NOT NULL UNIQUE,
    is_vegetarian BOOLEAN NOT NULL,
    category VARCHAR(255) NOT NULL,
    is_deleted boolean not null,
    deleted_at varchar(50),
    price DOUBLE NOT NULL
) 
ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE utf8mb4_unicode_ci;

```
Deployment Completed