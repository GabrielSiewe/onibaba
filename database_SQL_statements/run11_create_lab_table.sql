CREATE TABLE `labs` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `invoice_id` int(10) unsigned NOT NULL,
  `method_id` int(10) unsigned NOT NULL,
  `desease_id` int(10) unsigned NOT NULL,
  `medecine_id` int(10) unsigned DEFAULT NULL,
  `result_explanation` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `labs_invoice_id_foreign` (`invoice_id`),
  KEY `labs_method_id_foreign` (`method_id`),
  KEY `labs_desease_id_foreign` (`desease_id`),
  KEY `labs_medecine_id_foreign` (`medecine_id`),
  CONSTRAINT `labs_desease_id_foreign` FOREIGN KEY (`desease_id`) REFERENCES `deseases` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `labs_invoice_id_foreign` FOREIGN KEY (`invoice_id`) REFERENCES `invoices` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `labs_medecine_id_foreign` FOREIGN KEY (`medecine_id`) REFERENCES `medecines` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `labs_method_id_foreign` FOREIGN KEY (`method_id`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;