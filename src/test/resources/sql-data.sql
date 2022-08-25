INSERT INTO `customers` (`first_name`, `surname`) VALUES ('jordan', 'harrison');
INSERT INTO `items` (`name`, `price`, `stock`) VALUES ('TV', 500.00, 9);
INSERT INTO `orders` (`order_number`, `customer_id`, `order_total`) VALUES ('abc1234', 1, 500.00);
INSERT INTO `order_line_items` (`item_id`, `quantity`, `order_id`) VALUES (1, 1, 1);
