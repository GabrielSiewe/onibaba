CREATE TABLE `inventorys` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `medecine_id` int(10) unsigned NOT NULL,
  `quantity` int(11) DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `inventorys_medecine_id_unique` (`medecine_id`),
  CONSTRAINT `inventorys_medecine_id_foreign` FOREIGN KEY (`medecine_id`) REFERENCES `medecines` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;