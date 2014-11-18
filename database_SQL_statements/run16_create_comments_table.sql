CREATE TABLE `comments` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `person_id` int(10) unsigned NOT NULL,
  `object_id` int(10) unsigned DEFAULT NULL,
  `object_model` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'self note',
  `message` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `comments_person_id_foreign` (`person_id`),
  KEY `comments_object_id_index` (`object_id`),
  CONSTRAINT `comments_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;