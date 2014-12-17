PRAGMA synchronous = OFF;
PRAGMA journal_mode = MEMORY;
BEGIN TRANSACTION;
CREATE TABLE `appointments` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `doctor_id` integer  NOT NULL
,  `person_id` integer  NOT NULL
,  `appointment_date` date NOT NULL
,  `appointment_time` time NOT NULL
,  `description` varchar(255) DEFAULT NULL
,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  CONSTRAINT `appointments_doctor_id_foreign` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
,  CONSTRAINT `appointments_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE `comments` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `person_id` integer  NOT NULL
,  `object_id` integer  DEFAULT NULL
,  `object_model` varchar(255) DEFAULT 'self note'
,  `message` varchar(255) NOT NULL
,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  CONSTRAINT `comments_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE `daily_sales` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `amount` double NOT NULL DEFAULT '0'
,  `date` date NOT NULL
,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
);
CREATE TABLE `desease_medecins` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `desease_id` integer  NOT NULL
,  `medecine_id` integer  NOT NULL
,  CONSTRAINT `desease_medecins_desease_id_foreign` FOREIGN KEY (`desease_id`) REFERENCES `deseases` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
,  CONSTRAINT `desease_medecins_medecine_id_foreign` FOREIGN KEY (`medecine_id`) REFERENCES `medecines` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE `desease_methods` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `desease_id` integer  NOT NULL
,  `method_id` integer  NOT NULL
,  CONSTRAINT `desease_methods_desease_id_foreign` FOREIGN KEY (`desease_id`) REFERENCES `deseases` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
,  CONSTRAINT `desease_methods_method_id_foreign` FOREIGN KEY (`method_id`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE `deseases` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `name` varchar(255) NOT NULL
,  `description` varchar(255) NOT NULL
,  `symptoms` varchar(255) NOT NULL
,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  UNIQUE (`name`)
);
CREATE TABLE `doctors` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `person_id` integer  NOT NULL
,  `resume` varchar(255) DEFAULT NULL
,  `education` varchar(255) DEFAULT NULL
,  `specialization` varchar(255) DEFAULT NULL
,  `experience` varchar(255) DEFAULT NULL
,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  UNIQUE (`person_id`)
,  CONSTRAINT `doctors_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
INSERT INTO `doctors` VALUES (1,1,NULL,'GSU',NULL,'CS Professor','2014-12-08 20:20:02','2014-12-08 20:20:02');
CREATE TABLE `inventorys` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `medecine_id` integer  NOT NULL
,  `quantity` integer DEFAULT '0'
,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  UNIQUE (`medecine_id`)
,  CONSTRAINT `inventorys_medecine_id_foreign` FOREIGN KEY (`medecine_id`) REFERENCES `medecines` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE `invoices` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `total_cost` double DEFAULT '0'
,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
);
CREATE TABLE `labs` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `invoice_id` integer  NOT NULL
,  `method_id` integer  NOT NULL
,  `desease_id` integer  NOT NULL
,  `medecine_id` integer  DEFAULT NULL
,  `result_explanation` varchar(255) NOT NULL
,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  CONSTRAINT `labs_desease_id_foreign` FOREIGN KEY (`desease_id`) REFERENCES `deseases` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
,  CONSTRAINT `labs_invoice_id_foreign` FOREIGN KEY (`invoice_id`) REFERENCES `invoices` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
,  CONSTRAINT `labs_medecine_id_foreign` FOREIGN KEY (`medecine_id`) REFERENCES `medecines` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
,  CONSTRAINT `labs_method_id_foreign` FOREIGN KEY (`method_id`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE `medecines` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `name` varchar(255) NOT NULL
,  `description` varchar(255) NOT NULL
,  `ingredients` varchar(255) NOT NULL
,  `cost` double NOT NULL
,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  UNIQUE (`name`)
);
CREATE TABLE `methods` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `name` varchar(255) NOT NULL
,  `description` varchar(255) DEFAULT NULL
,  `cost` double NOT NULL
,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  UNIQUE (`name`)
);
CREATE TABLE `nurses` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `person_id` integer  NOT NULL
,  `doctor_id` integer  NOT NULL
,  `education` varchar(255) DEFAULT NULL
,  `specialization` varchar(255) DEFAULT NULL
,  `experience` varchar(255) DEFAULT NULL
,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  UNIQUE (`person_id`)
,  CONSTRAINT `nurses_doctor_id_foreign` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
,  CONSTRAINT `nurses_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
INSERT INTO `nurses` VALUES (10,18,1,'GSU',NULL,'Computer Science ','2014-12-13 13:44:05','2014-12-13 13:44:05');
CREATE TABLE `patients` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `person_id` integer  NOT NULL
,  `nurse_id` integer  NOT NULL
,  `occupation` varchar(100) DEFAULT NULL
,  `is_insured` integer DEFAULT NULL
,  `marital_status` varchar(20) DEFAULT NULL
,  `last_exam_date` timestamp NULL DEFAULT '0000-00-00 00:00:00'
,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  UNIQUE (`person_id`)
,  CONSTRAINT `patients_nurse_id_foreign` FOREIGN KEY (`nurse_id`) REFERENCES `nurses` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
,  CONSTRAINT `patients_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE `persons` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `username` varchar(7) NOT NULL DEFAULT ''
,  `password` varchar(20) DEFAULT NULL
,  `photo_path` varchar(100) DEFAULT NULL
,  `last_name` varchar(100) DEFAULT NULL
,  `first_name` varchar(100) DEFAULT NULL
,  `heigth` double DEFAULT NULL
,  `weigth` double DEFAULT NULL
,  `gender` varchar(6) DEFAULT NULL
,  `salary` double  DEFAULT NULL
,  `email` varchar(255) DEFAULT NULL
,  `ssn` varchar(11) DEFAULT NULL
,  `allergies` varchar(255) DEFAULT NULL
,  `birthday` datetime DEFAULT NULL
,  `phone` varchar(13) DEFAULT NULL
,  `secret_answer` varchar(255) DEFAULT NULL
,  `secret_question_id` integer  DEFAULT NULL
,  `title` varchar(7) DEFAULT NULL
,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  UNIQUE (`username`)
,  UNIQUE (`ssn`)
,  UNIQUE (`email`)
,  CONSTRAINT `persons_secret_question_id_foreign` FOREIGN KEY (`secret_question_id`) REFERENCES `secret_questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
INSERT INTO `persons` VALUES (1,'000000','Onibaba',NULL,'Bhola','Jaman',NULL,NULL,'Male',300000,'jbhola@cs.gsu.edu ','666777889','none','1950-01-01 00:00:00','404-413-5720','Toyota',5,'doctor','2014-12-08 20:20:01','2014-12-08 20:20:01');
INSERT INTO `persons` VALUES (18,'GSiewe','Onibaba',NULL,'Siewe','Gabriel',NULL,NULL,'Male',70000,'gabrielsiewe1@gmail.com','667-89-0987','none','2000-12-31 00:00:00','404-978-7013',NULL,NULL,'nurse','2014-12-13 13:44:05','2014-12-13 13:44:05');
CREATE TABLE `prescriptions` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `appointment_id` integer  NOT NULL
,  `invoice_id` integer  NOT NULL
,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
,  CONSTRAINT `prescriptions_appointment_id_foreign` FOREIGN KEY (`appointment_id`) REFERENCES `appointments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
,  CONSTRAINT `prescriptions_invoice_id_foreign` FOREIGN KEY (`invoice_id`) REFERENCES `invoices` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE `secret_questions` (
  `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT
,  `question` varchar(100) DEFAULT NULL
);
INSERT INTO `secret_questions` VALUES (1,'What is the first name of the person you first kissed?');
INSERT INTO `secret_questions` VALUES (2,'What is the last name of the teacher who gave you your first failing grade?');
INSERT INTO `secret_questions` VALUES (3,'What is the name of the place your wedding reception was held?');
INSERT INTO `secret_questions` VALUES (4,'In what city or town did you meet your spouse/partner?');
INSERT INTO `secret_questions` VALUES (5,'What was the make and model of your first car?');
INSERT INTO `secret_questions` VALUES (6,'What is the default password for Recovery?');
CREATE INDEX "nurses_nurses_doctor_id_foreign" ON "nurses" (`doctor_id`);
CREATE INDEX "prescriptions_prescriptions_invoice_id_foreign" ON "prescriptions" (`invoice_id`);
CREATE INDEX "prescriptions_prescriptions_appointment_id_foreign" ON "prescriptions" (`appointment_id`);
CREATE INDEX "appointments_appointments_doctor_id_foreign" ON "appointments" (`doctor_id`);
CREATE INDEX "appointments_appointments_person_id_foreign" ON "appointments" (`person_id`);
CREATE INDEX "desease_medecins_desease_medecins_desease_id_foreign" ON "desease_medecins" (`desease_id`);
CREATE INDEX "desease_medecins_desease_medecins_medecine_id_foreign" ON "desease_medecins" (`medecine_id`);
CREATE INDEX "comments_comments_person_id_foreign" ON "comments" (`person_id`);
CREATE INDEX "comments_comments_object_id_index" ON "comments" (`object_id`);
CREATE INDEX "persons_persons_secret_question_id_foreign" ON "persons" (`secret_question_id`);
CREATE INDEX "patients_patients_nurse_id_foreign" ON "patients" (`nurse_id`);
CREATE INDEX "desease_methods_desease_methods_method_id_foreign" ON "desease_methods" (`method_id`);
CREATE INDEX "desease_methods_desease_methods_desease_id_foreign" ON "desease_methods" (`desease_id`);
CREATE INDEX "labs_labs_invoice_id_foreign" ON "labs" (`invoice_id`);
CREATE INDEX "labs_labs_method_id_foreign" ON "labs" (`method_id`);
CREATE INDEX "labs_labs_desease_id_foreign" ON "labs" (`desease_id`);
CREATE INDEX "labs_labs_medecine_id_foreign" ON "labs" (`medecine_id`);
END TRANSACTION;
