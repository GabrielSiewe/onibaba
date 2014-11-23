ALTER TABLE `persons` ADD `username` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL AFTER `id`;
ALTER TABLE `persons` ADD UNIQUE INDEX `persons_username_unique` (`username`);
ALTER TABLE `persons` ADD `password` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL AFTER `username`;