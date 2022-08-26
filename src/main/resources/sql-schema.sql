drop schema ims;

CREATE SCHEMA IF NOT EXISTS `ims`;

USE `ims` ;

CREATE TABLE IF NOT EXISTS `ims`.`customers` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(40) DEFAULT NULL,
    `surname` VARCHAR(40) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ims`.`items` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(40) NOT NULL,
    `price` DECIMAL(7, 2) DEFAULT 0.00,
    `stock` INT DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ims`.`orders` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`order_number` VARCHAR(16) UNIQUE,
	`customer_id` INT,
	`order_total` DECIMAL(8, 2) DEFAULT 0.00,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`customer_id`) REFERENCES `ims`.`customers` (`id`) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS `ims`.`order_line_items` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `item_id` INT,
    `quantity` INT DEFAULT 0,
    `order_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`item_id`) REFERENCES `ims`.`items` (`id`) ON DELETE SET NULL,
    FOREIGN KEY (`order_id`) REFERENCES `ims`.`orders` (`id`) ON DELETE CASCADE
);
