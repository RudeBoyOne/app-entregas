CREATE TABLE `sender` (
    `id` char(36) NOT NULL,
    `contract` bigint NOT NULL,
    `cpf` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
)