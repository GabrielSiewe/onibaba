package DatabaseCacheManipulator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * This class will serve as the manager of the database. It will be our persistent database.
 */
public class Database {

	// First let's go ahead and set the database information required
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private PreparedStatement query = null;

	private static final String jdbcDriver = "jdbc:mysql://localhost/SoftwareEngineeringDB";
	private Connection connector = null;
	

	protected Database() {
		setConnection();
	}
	
	protected PreparedStatement getPreparedStatement()
	{
		return query;
	}
	
	protected ResultSet runQuery(String statement) throws SQLException
	{
		ResultSet results = null;
		query.clearParameters();
		if (statement != null) {
			results = query.executeQuery(statement);
			results.first();
		}
		return results;
	}
	
	protected void updateQuery(String statement) throws SQLException
	{
		ResultSet results = null;
		query.clearParameters();
		if (statement != null) {
			query.executeUpdate(statement);
		}
		return;
	}
	

	private void setConnection()
	{
		try{
			connector = DriverManager.getConnection(jdbcDriver, USERNAME, PASSWORD);
			query = connector.prepareStatement("?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
		} catch (SQLException e) {
			
			try{
				
				connector = DriverManager.getConnection("jdbc:mysql://localhost", "root", "");
				query = connector.prepareStatement("?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				
				query.executeUpdate("CREATE DATABASE IF NOT EXISTS SoftwareEngineeringDB;");
				
				query.executeUpdate("CREATE TABLE SoftwareEngineeringDB.`persons` ("+
						  "`id` int(10) unsigned NOT NULL AUTO_INCREMENT,"+
						  "`first_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"+
						  "`last_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"+
						  "`username` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"+
						  "`email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"+
						  "`title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"+
						  "`gender` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"+
						  "`salary` double NOT NULL,"+
						  "`ssn` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"+
						  "`allergies` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"+
						  "`birthday` datetime DEFAULT NULL,"+
						  "`phone` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,"+
						  "`secret_answer` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"+
						  "`secret_question_id` int(10) unsigned DEFAULT NULL,"+
						  "`created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"+
						  "`updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"+
						  "PRIMARY KEY (`id`),"+
						  "UNIQUE KEY `persons_ssn_unique` (`ssn`)"+
						  ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;"
					);
						
				query.executeUpdate("CREATE TABLE SoftwareEngineeringDB.`doctors` ("+
							  "`id` int(10) unsigned NOT NULL AUTO_INCREMENT,"+
							  "`person_id` int(10) unsigned NOT NULL,"+
							  "`education` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"+
							  "`experience` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"+
							  "`created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"+
							  "`updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"+
							  "PRIMARY KEY (`id`),"+
							  "UNIQUE KEY `doctors_person_id_unique` (`person_id`),"+
							  "CONSTRAINT `doctors_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES SoftwareEngineeringDB.`persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"+
							") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;");
						
				query.executeUpdate("CREATE TABLE SoftwareEngineeringDB.`nurses` ("+
						  "`id` int(10) unsigned NOT NULL AUTO_INCREMENT,"+
						  "`person_id` int(10) unsigned NOT NULL,"+
						  "`doctor_id` int(10) unsigned NOT NULL,"+
						  "`education` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"+
						  "`experience` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"+
						  "`created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"+
						  "`updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"+
						  "PRIMARY KEY (`id`),"+
						  "UNIQUE KEY `nurses_person_id_unique` (`person_id`),"+
						  "KEY `nurses_doctor_id_foreign` (`doctor_id`),"+
						  "CONSTRAINT `nurses_doctor_id_foreign` FOREIGN KEY (`doctor_id`) REFERENCES SoftwareEngineeringDB.`doctors` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,"+
						  "CONSTRAINT `nurses_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES SoftwareEngineeringDB.`persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"+
						  ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;");
				query.executeUpdate(
							"CREATE TABLE SoftwareEngineeringDB.`patients` ( `id` int(10) unsigned NOT NULL AUTO_INCREMENT, `person_id` int(10) unsigned NOT NULL,"
							+ "`nurse_id` int(10) unsigned NOT NULL,"
							+ "`education` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, `experience` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"
							+ "`created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',`updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"
							+ " PRIMARY KEY (`id`), UNIQUE KEY `patients_person_id_unique` (`person_id`),KEY `patients_nurse_id_foreign` (`nurse_id`),"
							+ " CONSTRAINT `patients_nurse_id_foreign` FOREIGN KEY (`nurse_id`) REFERENCES SoftwareEngineeringDB.`nurses` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,"
							+ "CONSTRAINT `patients_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES SoftwareEngineeringDB.`persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
							+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;"
					);
					
				query.executeUpdate(
							"CREATE TABLE SoftwareEngineeringDB.`appointments` ("
							+ "`id` int(10) unsigned NOT NULL AUTO_INCREMENT,"
							+ "`doctor_id` int(10) unsigned NOT NULL, `person_id` int(10) unsigned NOT NULL,"
							+ "`description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, "
							+ "`created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',`updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"
							+ "PRIMARY KEY (`id`),KEY `appointments_doctor_id_foreign` (`doctor_id`),KEY `appointments_person_id_foreign` (`person_id`),"
							+ "CONSTRAINT `appointments_doctor_id_foreign` FOREIGN KEY (`doctor_id`) REFERENCES SoftwareEngineeringDB.`doctors` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,"
							+ "CONSTRAINT `appointments_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES SoftwareEngineeringDB.`persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
							+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;"
					);
					
				query.executeUpdate(
							"CREATE TABLE SoftwareEngineeringDB.`medecines` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,`name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,"
							+ "`description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,`ingredients` varchar(255) COLLATE utf8_unicode_ci NOT NULL,"
							+ "`cost` double NOT NULL,`created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"
							+ "`updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',PRIMARY KEY (`id`), UNIQUE KEY `medecines_name_unique` (`name`)"
							+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;"
					);
				query.executeUpdate(
							"CREATE TABLE SoftwareEngineeringDB.`deseases` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,`name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,"
							+ "`description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,`symptoms` varchar(255) COLLATE utf8_unicode_ci NOT NULL,"
							+ "`created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',`updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"
							+ "PRIMARY KEY (`id`),UNIQUE KEY `deseases_name_unique` (`name`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;"
					);
				query.executeUpdate(
							"CREATE TABLE SoftwareEngineeringDB.`inventorys` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,"
							+ "`medecine_id` int(10) unsigned NOT NULL,`quantity` int(11) DEFAULT '0',"
							+ "`created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"
							+ "`updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',PRIMARY KEY (`id`),"
							+ "UNIQUE KEY `inventorys_medecine_id_unique` (`medecine_id`),"
							+ "CONSTRAINT `inventorys_medecine_id_foreign` FOREIGN KEY (`medecine_id`) REFERENCES SoftwareEngineeringDB.`medecines` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
							+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;"
					);
					
				query.executeUpdate(
							"CREATE TABLE SoftwareEngineeringDB.`methods` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,"
							+ "`name` varchar(255) COLLATE utf8_unicode_ci NOT NULL, `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"
							+ "`cost` double NOT NULL,`created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"
							+ "`updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',PRIMARY KEY (`id`),"
							+ "UNIQUE KEY `methods_name_unique` (`name`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;"
					);
					
				query.executeUpdate(
							"CREATE TABLE SoftwareEngineeringDB.`invoices` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,`total_cost` double DEFAULT '0',"
							+ "`created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',`updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"
							+ "PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;"
					);
					
				query.executeUpdate(
							"CREATE TABLE SoftwareEngineeringDB.`labs` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT, `invoice_id` int(10) unsigned NOT NULL,"
							+ "`method_id` int(10) unsigned NOT NULL,`desease_id` int(10) unsigned NOT NULL,`medecine_id` int(10) unsigned DEFAULT NULL,"
							+ "`result_explanation` varchar(255) COLLATE utf8_unicode_ci NOT NULL,`created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"
							+ "`updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',PRIMARY KEY (`id`),KEY `labs_invoice_id_foreign` (`invoice_id`),"
							+ "KEY `labs_method_id_foreign` (`method_id`), KEY `labs_desease_id_foreign` (`desease_id`),KEY `labs_medecine_id_foreign` (`medecine_id`),"
							+ "CONSTRAINT `labs_desease_id_foreign` FOREIGN KEY (`desease_id`) REFERENCES SoftwareEngineeringDB.`deseases` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,"
							+ "CONSTRAINT `labs_invoice_id_foreign` FOREIGN KEY (`invoice_id`) REFERENCES SoftwareEngineeringDB.`invoices` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,"
							+ "CONSTRAINT `labs_medecine_id_foreign` FOREIGN KEY (`medecine_id`) REFERENCES SoftwareEngineeringDB.`medecines` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,"
							+ "CONSTRAINT `labs_method_id_foreign` FOREIGN KEY (`method_id`) REFERENCES SoftwareEngineeringDB.`methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
							+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;"
					);
					
				query.executeUpdate(
							"CREATE TABLE SoftwareEngineeringDB.`prescriptions` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,"
							+ "`appointment_id` int(10) unsigned NOT NULL,`invoice_id` int(10) unsigned NOT NULL,"
							+ "`created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',`updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"
							+ "PRIMARY KEY (`id`),KEY `prescriptions_invoice_id_foreign` (`invoice_id`),KEY `prescriptions_appointment_id_foreign` (`appointment_id`),"
							+ "CONSTRAINT `prescriptions_appointment_id_foreign` FOREIGN KEY (`appointment_id`) REFERENCES SoftwareEngineeringDB.`appointments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,"
							+ " CONSTRAINT `prescriptions_invoice_id_foreign` FOREIGN KEY (`invoice_id`) REFERENCES SoftwareEngineeringDB.`invoices` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
							+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;"
					);
				query.executeUpdate(
							"CREATE TABLE SoftwareEngineeringDB.`desease_methods` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,"
							+ "`desease_id` int(10) unsigned NOT NULL,`method_id` int(10) unsigned NOT NULL,"
							+ "PRIMARY KEY (`id`),KEY `desease_methods_method_id_foreign` (`method_id`),KEY `desease_methods_desease_id_foreign` (`desease_id`),"
							+ "CONSTRAINT `desease_methods_desease_id_foreign` FOREIGN KEY (`desease_id`) REFERENCES SoftwareEngineeringDB.`deseases` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,"
							+ "CONSTRAINT `desease_methods_method_id_foreign` FOREIGN KEY (`method_id`) REFERENCES SoftwareEngineeringDB.`methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
							+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;"
					);
					
				query.executeUpdate(
							"CREATE TABLE SoftwareEngineeringDB.`desease_medecins` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,"
							+ "`desease_id` int(10) unsigned NOT NULL,`medecine_id` int(10) unsigned NOT NULL,"
							+ "PRIMARY KEY (`id`),KEY `desease_medecins_desease_id_foreign` (`desease_id`),"
							+ "KEY `desease_medecins_medecine_id_foreign` (`medecine_id`),"
							+ "CONSTRAINT `desease_medecins_desease_id_foreign` FOREIGN KEY (`desease_id`) REFERENCES SoftwareEngineeringDB.`deseases` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,"
							+ "CONSTRAINT `desease_medecins_medecine_id_foreign` FOREIGN KEY (`medecine_id`) REFERENCES SoftwareEngineeringDB.`medecines` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
							+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;"
					);
					
				query.executeUpdate(
							"CREATE TABLE SoftwareEngineeringDB.`daily_sales` ("
							+ "`id` int(10) unsigned NOT NULL AUTO_INCREMENT,`amount` double DEFAULT '0',"
							+ "`created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',`updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"
							+ " PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;"
					);
					
				query.executeUpdate(
							"CREATE TABLE SoftwareEngineeringDB.`comments` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,"
							+ "`person_id` int(10) unsigned NOT NULL,`object_id` int(10) unsigned DEFAULT NULL,"
							+ "`object_model` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'self note',`message` varchar(255) COLLATE utf8_unicode_ci NOT NULL,"
							+ "`created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',`updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',"
							+ "PRIMARY KEY (`id`),KEY `comments_person_id_foreign` (`person_id`),KEY `comments_object_id_index` (`object_id`),"
							+ "CONSTRAINT `comments_person_id_foreign` FOREIGN KEY (`person_id`) REFERENCES SoftwareEngineeringDB.`persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE"
							+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;"
					);
				query.executeUpdate(
						"CREATE TABLE SoftwareEngineeringDB.`secret_questions` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,"
						 +"`question` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"
						+ "PRIMARY KEY (`id`)"
						+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;"
				);
				query.executeUpdate(
							"ALTER TABLE SoftwareEngineeringDB.`appointments` ADD `appointment_date` DATETIME  NULL  DEFAULT '0000-00-00 00:00:00'  AFTER `person_id`;"
					);
				
				query.executeUpdate(
							 "ALTER TABLE SoftwareEngineeringDB.`persons` ADD UNIQUE INDEX `persons_username_unique` (`username`);"
				);
				query.executeUpdate(
							 "ALTER TABLE SoftwareEngineeringDB.`persons` ADD `password` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL AFTER `username`;"
				);
				query.executeUpdate(
						 "INSERT INTO SoftwareEngineeringDB.`persons` (`username`, `password`, `first_name`, `last_name`, `gender`,`salary`,`title`, `ssn`)"
						 + "VALUES ('JBhola','Onibaba','Jaman','Bhola','Male', '200000','doctor', '789-09-0987');"
				);
				query.executeUpdate(
						 "INSERT INTO SoftwareEngineeringDB.`doctors` (`person_id`, `education`, `experience`)"
						 + "VALUES ('1','CS GSU','Professor');"
				);

				closeConnection();
				setConnection();
			} catch (SQLException x) {
				System.out.println("The database connection failed.");
				closeConnection();
			}
		}
	}

	protected void closeConnection()
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
			System.out.println("Cannot connect to network");
			return;
		}
		
	}
}
