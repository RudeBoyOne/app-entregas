CREATE TABLE `item` (
    `id` char(36) NOT NULL,
    `code` varchar(255) NOT NULL,
    `typeItem` varchar(255) NOT NULL,
    `sender_id` char(36) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_sender_item` (`sender_id`),
    CONSTRAINT `fk_sender_item` FOREIGN KEY (`sender_id`) REFERENCES `sender` (`id`)
)