drop schema ims;

CREATE SCHEMA IF NOT EXISTS `ims`;

USE `ims` ;

CREATE TABLE IF NOT EXISTS `ims`.`customers` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(40) DEFAULT NULL,
    `surname` VARCHAR(40) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
<<<<<<< Updated upstream
=======

CREATE TABLE IF NOT EXISTS items (
`id` INT NOT NULL AUTO_INCREMENT,
`item_name` VARCHAR(255) UNIQUE NOT NULL,
`price` DECIMAL(10,2) NOT NULL,
PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS orders (
`id` INT NOT NULL AUTO_INCREMENT,
`customer_id` INT NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`customer_id`) REFERENCES `customers`(`id`)
);
>>>>>>> Stashed changes
