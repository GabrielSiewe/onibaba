CREATE TABLE `desease_methods` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `desease_id` int(10) unsigned NOT NULL,
  `method_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `desease_methods_method_id_foreign` (`method_id`),
  KEY `desease_methods_desease_id_foreign` (`desease_id`),
  CONSTRAINT `desease_methods_desease_id_foreign` FOREIGN KEY (`desease_id`) REFERENCES `deseases` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `desease_methods_method_id_foreign` FOREIGN KEY (`method_id`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;