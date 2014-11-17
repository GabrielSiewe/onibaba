CREATE TABLE `desease_medecins` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `desease_id` int(10) unsigned NOT NULL,
  `medecine_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `desease_medecins_desease_id_foreign` (`desease_id`),
  KEY `desease_medecins_medecine_id_foreign` (`medecine_id`),
  CONSTRAINT `desease_medecins_desease_id_foreign` FOREIGN KEY (`desease_id`) REFERENCES `deseases` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `desease_medecins_medecine_id_foreign` FOREIGN KEY (`medecine_id`) REFERENCES `medecines` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;