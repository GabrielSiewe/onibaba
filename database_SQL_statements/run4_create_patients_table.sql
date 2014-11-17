CREATE TABLE `patients` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `person_id` int(10) unsigned NOT NULL,
  `nurse_id` int(10) unsigned NOT NULL,
  `education` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `experience` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `patients_person_id_unique` (`person_id`),
  KEY `patients_nurse_id_foreign` (`nurse_id`),
  CONSTRAINT `patients_nurse_id_foreign` FOREIGN KEY (`nurse_id`) REFERENCES `nurses` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `patients_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;