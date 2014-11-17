

import java.sql.ResultSet;

import databaseObjects.beans.Patient;
import databaseObjects.beans.Person;
import databaseObjects.beans.Nurse;
import databaseObjects.beans.Doctor;
import databaseObjects.beans.DatabaseManipulator;
import java.util.concurrent.ConcurrentHashMap;


public class Tester {
	public static void main(String[] args) throws Exception
	{
		DatabaseManipulator database =  new DatabaseManipulator("Onibaba", "DearDarling");
		ConcurrentHashMap<String,String> attributes = new ConcurrentHashMap<String, String>();
		attributes.put("name", "joanna");
		attributes.put("age", "17");
		attributes.put("city", "Paris");
		Person.setDBManipulator(database);
		String[][] indicator = new String[1][2];
		indicator[0][0] = "id";
		indicator[0][1] = "1";
		String findNurse = Nurse.getStatement("find", indicator, attributes, new String[]{});

//		String findDoctor = Doctor.getStatement("delete",indicator, attributes);
//		System.out.println(findDoctor);
//		String findPatient = Patient.getStatement("delete",indicator, attributes);
//		System.out.println(findPatient);
//
//		Doctor doctor = new Doctor(database.runQuery(findDoctor));
//		System.out.println(doctor.getFirst_name());
//		
//		 Patient patient = new Patient(database.runQuery(findPatient));
//		 System.out.println(patient.getFirst_name());
//		 System.out.println(doctor.getFirst_name());
//		 Nurse nurse = new Nurse();
//		 System.out.println(nurse.getExperience());

		 System.out.println(findNurse);
		 database.close();
	}

}
