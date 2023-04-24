CREATE TABLE `delivery` (
    `id` char(36) NOT NULL,
    `deliveryDate` datetime(6) DEFAULT NULL,
    `shippingDate` datetime(6) NOT NULL,
    `cancellationDate` datetime(6) DEFAULT NULL,
    `status` varchar(60) NOT NULL,
    `tax` decimal(19, 2) NOT NULL,
    `addressee_id` char(36) NOT NULL,
    `item_id` char(36) NOT NULL,
    `sender_id` char(36) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_addressee` (`addressee_id`),
    KEY `fk_item` (`item_id`),
    KEY `fk_sender_delivery` (`sender_id`),
    CONSTRAINT `fk_sender_delivery` FOREIGN KEY (`sender_id`) REFERENCES `sender` (`id`),
    CONSTRAINT `fk_addressee` FOREIGN KEY (`addressee_id`) REFERENCES `addressee` (`id`),
    CONSTRAINT `fk_item` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
)

