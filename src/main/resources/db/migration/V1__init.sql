CREATE TABLE `t_orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_number` VARCHAR(255) DEFAULT NULL,
  `sku_code` VARCHAR(255),
  `price` decimal(19, 2),
  `quantity` INT,
  PRIMARY KEY (`id`)
);