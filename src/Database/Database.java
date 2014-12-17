package Database;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * This class will serve as the manager of the database. It will be our persistent database.
 */
public class Database {

	// First let's go ahead and set the database information required
	private Statement query = null;

	private Connection connector = null;


	public Database() throws SQLException {
		setConnection();
	}
	
	protected Statement getStatement()
	{
		return query;
	}
	
	public ResultSet runQuery(String statement) throws SQLException
	{
		ResultSet results = null;
		if (statement != null) {
			results = query.executeQuery(statement);
			return results;
		}
		throw new SQLException("Invalid Statement");
	}
	
	public void updateQuery(String statement) throws SQLException
	{
		if (statement != null) {
			query.executeUpdate(statement);
			return;
		}
		throw new SQLException("Invalid Statement");
	}
	

	private void setConnection() throws SQLException
	{
		File database = new File("Onibaba.db");
		if (!database.exists()) {
			try{
				connector = DriverManager.getConnection("jdbc:sqlite:Onibaba.db");
				closeConnection();
				setConnection();

				query.executeUpdate(
						"CREATE TABLE `secret_questions` (`id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT"
						+ ",  `question` varchar(100) DEFAULT NULL);"
				);
				
				query.executeUpdate(
						"CREATE TABLE `persons` (`id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT"
						+ ",  `username` varchar(7) NOT NULL DEFAULT '',  `password` varchar(20) DEFAULT NULL"
						+ ",  `photo_path` varchar(100) DEFAULT NULL,  `last_name` varchar(100) DEFAULT NULL"
						+ ",  `first_name` varchar(100) DEFAULT NULL,  `height` double DEFAULT NULL"
						+ ",  `weight` double DEFAULT NULL,  `gender` varchar(6) DEFAULT NULL,  `salary` double  DEFAULT NULL"
						+ ",  `email` varchar(255) DEFAULT NULL,  `ssn` varchar(11) DEFAULT NULL,  `allergies` varchar(255) DEFAULT NULL"
						+ ",  `birthday` date DEFAULT NULL,  `phone` varchar(13) DEFAULT NULL,  `secret_answer` varchar(255) DEFAULT NULL"
						+ ",  `secret_question_id` integer  DEFAULT NULL,  `title` varchar(7) DEFAULT NULL,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',  UNIQUE (`username`),  UNIQUE (`ssn`),  UNIQUE (`email`)"
						+ ",  CONSTRAINT `persons_secret_question_id_foreign` FOREIGN KEY (`secret_question_id`) REFERENCES `secret_questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ");"
				);
						
				query.executeUpdate(
						"CREATE TABLE `doctors` (`id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT,  `person_id` integer  NOT NULL"
						+ ",  `resume` varchar(255) DEFAULT NULL,  `education` varchar(255) DEFAULT NULL,  `specialization` varchar(255) DEFAULT NULL"
						+ ",  `experience` varchar(255) DEFAULT NULL,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',  UNIQUE (`person_id`)"
						+ ",  CONSTRAINT `doctors_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ");"
				);
						
				query.executeUpdate(
						"CREATE TABLE `nurses` ( `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT"
						+ ",  `person_id` integer  NOT NULL,  `doctor_id` integer  NOT NULL,  `education` varchar(255) DEFAULT NULL"
						+ ",  `specialization` varchar(255) DEFAULT NULL,  `experience` varchar(255) DEFAULT NULL,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',  UNIQUE (`person_id`)"
						+ ",  CONSTRAINT `nurses_doctor_id_foreign` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ",  CONSTRAINT `nurses_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ");"
				);

				query.executeUpdate(
						"CREATE TABLE `patients` ( `id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT"
						+ ",  `person_id` integer  NOT NULL,  `nurse_id` integer  NOT NULL,  `occupation` varchar(100) DEFAULT NULL"
						+ ",  `is_insured` integer DEFAULT NULL,  `marital_status` varchar(20) DEFAULT NULL"
						+ ",  `last_exam_date` timestamp NULL DEFAULT '0000-00-00 00:00:00',  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  UNIQUE (`person_id`)"
						+ ",  CONSTRAINT `patients_nurse_id_foreign` FOREIGN KEY (`nurse_id`) REFERENCES `nurses` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ",  CONSTRAINT `patients_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ");"
				);
					
				query.executeUpdate(
						"CREATE TABLE `medecines` (`id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT,  `name` varchar(255) NOT NULL"
						+ ",  `description` varchar(255) NOT NULL,  `ingredients` varchar(255) NOT NULL,  `cost` double NOT NULL"
						+ ",  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  UNIQUE (`name`));"
				);
				query.executeUpdate(
						"CREATE TABLE `deseases` (`id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT,  `name` varchar(255) NOT NULL"
						+ ",  `description` varchar(255) NOT NULL,  `symptoms` varchar(255) NOT NULL,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',  UNIQUE (`name`));"
				);
					
				query.executeUpdate(
						"CREATE TABLE `methods` (`id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT,  `name` varchar(255) NOT NULL"
						+ ",  `description` varchar(255) DEFAULT NULL,  `cost` double NOT NULL,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',  UNIQUE (`name`));"
				);
				
				query.executeUpdate(
						"CREATE TABLE `desease_methods` (`id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT,  `desease_id` integer  NOT NULL"
						+ ",  `method_id` integer  NOT NULL"
						+ ",  CONSTRAINT `desease_methods_desease_id_foreign` FOREIGN KEY (`desease_id`) REFERENCES `deseases` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ",  CONSTRAINT `desease_methods_method_id_foreign` FOREIGN KEY (`method_id`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ");"
					);
					
				query.executeUpdate(
						"CREATE TABLE `desease_medecins` (`id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT,  `desease_id` integer  NOT NULL"
						+ ",  `medecine_id` integer  NOT NULL"
						+ ",  CONSTRAINT `desease_medecins_desease_id_foreign` FOREIGN KEY (`desease_id`) REFERENCES `deseases` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ",  CONSTRAINT `desease_medecins_medecine_id_foreign` FOREIGN KEY (`medecine_id`) REFERENCES `medecines` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ");"
					);
					
				query.executeUpdate(
						"CREATE TABLE `inventorys` (`id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT,  `medecine_id` integer  NOT NULL"
						+ ",  `quantity` integer DEFAULT '0',  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',  UNIQUE (`medecine_id`)"
						+ ",  CONSTRAINT `inventorys_medecine_id_foreign` FOREIGN KEY (`medecine_id`) REFERENCES `medecines` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ");"
				);

				query.executeUpdate(
						"CREATE TABLE `appointments` (`id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT,  `doctor_id` integer  NOT NULL"
						+ ",  `person_id` integer  NOT NULL,  `appointment_date` date NOT NULL,  `appointment_time` time NOT NULL"
						+ ",  `description` varchar(255) DEFAULT NULL,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',  CONSTRAINT `appointments_doctor_id_foreign` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ",  CONSTRAINT `appointments_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE);"
				);
				
				query.executeUpdate(
						"CREATE TABLE `invoices` (`id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT"
						+ ",  `total_cost` double DEFAULT '0',  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00');"
				);
					
				query.executeUpdate(
						"CREATE TABLE `labs` (`id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT,  `invoice_id` integer  NOT NULL"
						+ ",  `method_id` integer  NOT NULL,  `desease_id` integer  NOT NULL,  `medecine_id` integer  DEFAULT NULL"
						+ ",  `result_explanation` varchar(255) NOT NULL,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  CONSTRAINT `labs_desease_id_foreign` FOREIGN KEY (`desease_id`) REFERENCES `deseases` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ",  CONSTRAINT `labs_invoice_id_foreign` FOREIGN KEY (`invoice_id`) REFERENCES `invoices` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ",  CONSTRAINT `labs_medecine_id_foreign` FOREIGN KEY (`medecine_id`) REFERENCES `medecines` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ",  CONSTRAINT `labs_method_id_foreign` FOREIGN KEY (`method_id`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ");"
				);
					
				query.executeUpdate(
						"CREATE TABLE `prescriptions` (`id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT,  `appointment_id` integer  NOT NULL"
						+ ",  `invoice_id` integer  NOT NULL,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  CONSTRAINT `prescriptions_appointment_id_foreign` FOREIGN KEY (`appointment_id`) REFERENCES `appointments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ",  CONSTRAINT `prescriptions_invoice_id_foreign` FOREIGN KEY (`invoice_id`) REFERENCES `invoices` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ");"
				);

				query.executeUpdate(
						"CREATE TABLE `daily_sales` (`id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT,  `amount` double NOT NULL DEFAULT '0'"
						+ ",  `date` date NOT NULL,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00');"
				);
					
				query.executeUpdate(
						"CREATE TABLE `comments` (`id` integer  NOT NULL PRIMARY KEY AUTOINCREMENT"
						+ ",  `person_id` integer  NOT NULL,  `object_id` integer  DEFAULT NULL,  `object_model` varchar(255) DEFAULT 'self note'"
						+ ",  `message` varchar(255) NOT NULL,  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'"
						+ ",  CONSTRAINT `comments_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
						+ ");"
				);
				
				query.executeUpdate(
						"CREATE INDEX 'nurses_nurses_doctor_id_foreign' ON 'nurses' (`doctor_id`);"
				);
				query.executeUpdate(
						"CREATE INDEX 'prescriptions_prescriptions_invoice_id_foreign' ON 'prescriptions' (`invoice_id`);"
				);
				query.executeUpdate(
						"CREATE INDEX 'prescriptions_prescriptions_appointment_id_foreign' ON 'prescriptions' (`appointment_id`);"
				);
				query.executeUpdate(
						"CREATE INDEX 'appointments_appointments_doctor_id_foreign' ON 'appointments' (`doctor_id`);"
				);
				query.executeUpdate(
						"CREATE INDEX 'appointments_appointments_person_id_foreign' ON 'appointments' (`person_id`);"
				);
				query.executeUpdate(
						"CREATE INDEX 'desease_medecins_desease_medecins_desease_id_foreign' ON 'desease_medecins' (`desease_id`);"
				);
				query.executeUpdate(
						"CREATE INDEX 'desease_medecins_desease_medecins_medecine_id_foreign' ON 'desease_medecins' (`medecine_id`);"
				);
				query.executeUpdate(
						"CREATE INDEX 'comments_comments_person_id_foreign' ON 'comments' (`person_id`);"
				);
				query.executeUpdate(
						"CREATE INDEX 'comments_comments_object_id_index' ON 'comments' (`object_id`);"
				);
				query.executeUpdate(
						"CREATE INDEX 'persons_persons_secret_question_id_foreign' ON 'persons' (`secret_question_id`);"
				);
				query.executeUpdate(
						"CREATE INDEX 'patients_patients_nurse_id_foreign' ON 'patients' (`nurse_id`);"
				);
				query.executeUpdate(
						"CREATE INDEX 'desease_methods_desease_methods_method_id_foreign' ON 'desease_methods' (`method_id`);"
				);
				query.executeUpdate(
						"CREATE INDEX 'desease_methods_desease_methods_desease_id_foreign' ON 'desease_methods' (`desease_id`);"
				);
				query.executeUpdate(
						"CREATE INDEX 'labs_labs_invoice_id_foreign' ON 'labs' (`invoice_id`);"
				);
				query.executeUpdate(
						"CREATE INDEX 'labs_labs_method_id_foreign' ON 'labs' (`method_id`);"
				);
				query.executeUpdate(
						"CREATE INDEX 'labs_labs_desease_id_foreign' ON 'labs' (`desease_id`);"
				);
				query.executeUpdate(
						"CREATE INDEX 'labs_labs_medecine_id_foreign' ON 'labs' (`medecine_id`);"
				);
				
				query.executeUpdate(
						"INSERT INTO `secret_questions` VALUES (1,'What is the first name of the person you first kissed?');"
				);
				query.executeUpdate(
						"INSERT INTO `secret_questions` VALUES (2,'What is the last name of the teacher who gave you your first failing grade?');"
				);
				query.executeUpdate(
						"INSERT INTO `secret_questions` VALUES (3,'What is the name of the place your wedding reception was held?');"
				);
				query.executeUpdate(
						"INSERT INTO `secret_questions` VALUES (4,'In what city or town did you meet your spouse/partner?');"
				);
				query.executeUpdate(
						"INSERT INTO `secret_questions` VALUES (5,'What was the make and model of your first car?');"
				);
				query.executeUpdate(
						"INSERT INTO `persons`(id,username,password,last_name,first_name,gender,title,salary,email,ssn,birthday,phone,secret_answer,secret_question_id,created_at,updated_at  ) VALUES (1,'000000','Onibaba','Bhola','Jaman','Male','doctor',300000,'jbhola@cs.gsu.edu ','666777889','1950-01-01 00:00:00','404-413-5720','Toyota',5,'2014-12-08 20:20:01','2014-12-08 20:20:01');"
				);
				query.executeUpdate(
						"INSERT INTO `doctors` VALUES (1,1,'none','GSU','none','CS Professor','2014-12-08 20:20:02','2014-12-08 20:20:02');"
				);
			} catch (SQLException x) {
				closeConnection();
				throw new SQLException("Unable to create the database and its tables.");
			}
			
		} else {
			try {
				connector = DriverManager.getConnection("jdbc:sqlite:Onibaba.db");
				query = connector.createStatement();
			} catch (SQLException x) {
				closeConnection();
				throw new SQLException("Unable to connect to the database.");
				
			}
		}
	}

	public void closeConnection() throws SQLException
	{
		try{
			if (query != null || connector != null)
			{
				if (query != null) 
				{
					query.close();
				}
				connector.close();
			}
			
		} catch (SQLException e) {
			throw new SQLException("Can not close the database connection.");
		}
		
	}
}
