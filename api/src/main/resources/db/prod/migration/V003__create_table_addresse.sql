CREATE TABLE `addressee` (
    `id` char(36) NOT NULL,
    `name` varchar(60) NOT NULL,
    `city` varchar(60) NOT NULL,
    `state` varchar(60) NOT NULL,
    `cep` varchar(8) NOT NULL,
    `houseNumber` bigint NOT NULL,
    `neighborhood` varchar(100) NOT NULL,
    `street` varchar(100) NOT NULL,
    `longitude` varchar(255) NOT NULL,
    `latitude` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
)