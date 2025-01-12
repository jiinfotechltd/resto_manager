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