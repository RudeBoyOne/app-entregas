CREATE TABLE `user_role` (
    `user_id` char(36) NOT NULL,
    `roles_id` bigint NOT NULL,
    KEY `fk_role` (`roles_id`),
    KEY `fk_user` (`user_id`),
    CONSTRAINT `fk_role` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`),
    CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
)