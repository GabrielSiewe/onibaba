package databaseObjects.beans.PersonMVC;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

import BaseMVC.BasicController;

public class PersonController extends BasicController {
	private NurseModel nurse;
	private DoctorModel doctor;
	private PatientModel patient;
	private PersonModel user;
	private int cursorPosition = 1;
	private int patientCursorPosition = 1;
	
	private ConcurrentHashMap<String, PatientModel> patientList;
	private ConcurrentHashMap<String, NurseModel> nurseList;
	
	public PersonController()
	{
		super();
		nurseList = new ConcurrentHashMap<String, NurseModel>();
		patientList = new ConcurrentHashMap<String, PatientModel>();
	}

	public void authenticate(ConcurrentHashMap<String, String> properties) throws Exception
	{
		try {
				ResultSet personAttributes = PersonModel.runQuery(PersonModel.getFindStatement(properties));
				
				if (personAttributes.isLast() == true) {
					String title = personAttributes.getString("title");
					properties.clear();
					properties.put("person_id", personAttributes.getString("id"));
					
					switch(title) {
					// patients should not be able to log in.
					case "doctor":
						String statement = DoctorModel.getFindStatement(properties);
						ResultSet results = DoctorModel.runQuery(statement);
						doctor = new DoctorModel(results);
						user = (DoctorModel) doctor;
						break;
					case "nurse": 
						statement = NurseModel.getFindStatement(properties);
						results = NurseModel.runQuery(statement);
						nurse = new NurseModel(results);
						user =(NurseModel) nurse;
						break;
					default: System.out.println("invalid person infos.\n"); break;
					}
				}
		} catch (SQLException e) {
			System.out.println("The person with username "+properties.get("username")+" could not be authenticated.");
		}

	}
	
	public PersonModel getUser()
	{
		return user;
	}
	public DoctorModel getDoctor()
	{
		return doctor;
	}
	public NurseModel getNurse()
	{
		return nurse;
	}
	public PatientModel getPatient()
	{
		return patient;
	}
	
	// Deals with the nurse lists.
	public void setDoctorNurses()
	{
		
		if (cursorPosition == 1) {
			nurseList.clear();
		}

		try
		{
			ResultSet nurses = doctor.nurses();
			nurses.absolute(cursorPosition);
			nurse = new NurseModel(nurses);
			nurseList.put(nurse.toString(), nurse);
			cursorPosition++;
			setDoctorNurses();
			nurse = null;
			patient = null;
		} catch (SQLException e) {

			if (nurseList == null || nurseList.size() == 0) {
				System.out.println("No nurses were found");
			}
			cursorPosition = 1;
		}
		
	}

	public ConcurrentHashMap<String, NurseModel> getListOfNurses()
	{
		return nurseList;
	}
	public ConcurrentHashMap<String, PatientModel> getListOfPatients()
	{
		return patientList;
	}
	

	public void setNurse(NurseModel nurseModel) {
		nurse = nurseModel;
	}
	public void setNurse(String nursePatient) {
		patient = patientList.get(nursePatient);
		nurse = nurseList.get(nursePatient.substring(0, nursePatient.indexOf("::")));
	}
	
	public void setDoctorPatients() {
		// On the firstNurse we clear the list of patients
		if (cursorPosition == 1) {
			nurseList.clear();
		}

		try
		{
			ResultSet nurses = doctor.nurses();
			nurses.absolute(cursorPosition);
			nurse = new NurseModel(nurses);
			nurseList.put(nurse.toString(), nurse);
			setNursePatients();
			cursorPosition++;
			setDoctorPatients();
			nurse = null;
		} catch (SQLException e) {

			if (nurseList == null || nurseList.size() == 0) {
				System.out.println("No nurses were found");
			}
			cursorPosition = 1;
		}
		
	}
	// Deals with the nurse patient lists.
	public void setNursePatients()
	{
		// On the firstNurse we clear the list of patients
		if (cursorPosition == 1 && patientCursorPosition == 1) {
			patientList.clear();
		}

		try
		{
			ResultSet patients = nurse.patients();
			patients.absolute(patientCursorPosition);
			patient = new PatientModel(patients);
			patientList.put(nurse.toString()+"::"+patient.toString(), patient);
			patientCursorPosition++;
			setNursePatients();
			patient = null;
		} catch (SQLException e) {

			if (patientList == null || patientList.size() == 0) {
				System.out.println("No patients were found");
			}
			patientCursorPosition = 1;
		}
		
	}
	public void setNurseTasks() {
		// TODO Auto-generated method stub
		
	}

	public void addNurse(ConcurrentHashMap<String, String> attributes) throws Exception {
		int cur = 1;
		ConcurrentHashMap<String, String> temp = new ConcurrentHashMap<String, String>();
		temp.put("username", attributes.get("first_name").trim().charAt(0)+""+attributes.get("last_name").trim());
		attributes.put("doctor_id", doctor.getDoctor_id()+"");
		while (true) {
			try {
				PersonModel.runQuery(PersonModel.getFindStatement(temp)).getString("id");
				temp.replace("username", attributes.get("first_name").trim().charAt(cur)+""+attributes.get("last_name").trim());
				cur++;
			} catch (SQLException e) {
				break;
			}
			
		}
		
		attributes.put("username", temp.get("username"));
		attributes.put("password", "Onibaba");
		temp.clear();
		temp.put("username", attributes.get("username"));
		temp.put("password", attributes.get("password"));
		temp.put("first_name", attributes.get("first_name"));
		temp.put("gender", attributes.get("gender"));
		temp.put("salary", attributes.get("salary"));
		temp.put("last_name", attributes.get("last_name"));
		temp.put("email", attributes.get("email"));
		temp.put("title", attributes.get("title"));
		temp.put("ssn", attributes.get("ssn"));
		temp.put("allergies", attributes.get("allergies"));
		temp.put("birthday", attributes.get("birthday"));
		temp.put("phone", attributes.get("phone"));
		temp.put("created_at", attributes.get("created_at"));
		temp.put("updated_at", attributes.get("updated_at"));
		PersonModel.updateQuery(PersonModel.getInsertStatement(temp));
		temp.clear();
		temp.put("username", attributes.get("username"));
		ResultSet result = PersonModel.runQuery(PersonModel.getFindStatement(temp));
		temp.clear();
		temp.put("person_id", result.getInt("id")+"");
		temp.put("doctor_id", attributes.get("doctor_id"));
		temp.put("education", attributes.get("education"));
		temp.put("experience", attributes.get("experience"));
		temp.put("created_at", attributes.get("created_at"));
		temp.put("updated_at", attributes.get("updated_at"));
		NurseModel.updateQuery(nurse.getInsertStatement(temp));
		
	}

	public void removeNurse() throws SQLException, Exception
	{
		ConcurrentHashMap<String, String> attributes = new ConcurrentHashMap<String, String>();
		attributes.put("id", nurse.getPerson_id()+"");
		PersonModel.updateQuery(PersonModel.getDeleteStatement(attributes));
		nurse = null;
		return;
	}
	public void removePatient() throws SQLException, Exception {
		ConcurrentHashMap<String, String> attributes = new ConcurrentHashMap<String, String>();
		attributes.put("id", patient.getPerson_id()+"");
		PersonModel.updateQuery(PersonModel.getDeleteStatement(attributes));
		patient = null;
		return;
		
	}
	public void recoverPasswordViaEmail(String text)
	{
		// The person to recover their email.
		PersonModel person = null;
		
	}

	public void updateNurse(ConcurrentHashMap<String, String> attributes) throws SQLException, Exception {
		ConcurrentHashMap<String, String> finders = new ConcurrentHashMap<String, String>();
		finders.put("id", nurse.getPerson_id()+"");
		ConcurrentHashMap<String, String> temp = new ConcurrentHashMap<String, String>();
		temp.put("first_name", attributes.get("first_name"));
		temp.put("gender", attributes.get("gender"));
		temp.put("salary", attributes.get("salary"));
		temp.put("last_name", attributes.get("last_name"));
		temp.put("email", attributes.get("email"));
		temp.put("title", attributes.get("title"));
		temp.put("ssn", attributes.get("ssn"));
		temp.put("allergies", attributes.get("allergies"));
		temp.put("birthday", attributes.get("birthday"));
		temp.put("phone", attributes.get("phone"));
		temp.put("updated_at", attributes.get("updated_at"));
		PersonModel.updateQuery(PersonModel.getUpdateStatement(finders, temp));
		finders.clear();
		temp.clear();
		finders.put("person_id", nurse.getPerson_id()+"");
		temp.put("education", attributes.get("education"));
		temp.put("experience", attributes.get("experience"));
		temp.put("updated_at", attributes.get("updated_at"));
		NurseModel.updateQuery(nurse.getUpdateStatement(finders, temp));
	}

	public void updateDoctor(ConcurrentHashMap<String, String> attributes) throws SQLException, Exception {
		ConcurrentHashMap<String, String> finders = new ConcurrentHashMap<String, String>();
		finders.put("id", doctor.getPerson_id()+"");
		ConcurrentHashMap<String, String> temp = new ConcurrentHashMap<String, String>();
		temp.put("first_name", attributes.get("first_name"));
		temp.put("gender", attributes.get("gender"));
		temp.put("salary", attributes.get("salary"));
		temp.put("last_name", attributes.get("last_name"));
		temp.put("email", attributes.get("email"));
		temp.put("title", attributes.get("title"));
		temp.put("ssn", attributes.get("ssn"));
		temp.put("allergies", attributes.get("allergies"));
		temp.put("birthday", attributes.get("birthday"));
		temp.put("phone", attributes.get("phone"));
		temp.put("updated_at", attributes.get("updated_at"));
		PersonModel.updateQuery(PersonModel.getUpdateStatement(finders, temp));
		finders.clear();
		temp.clear();
		finders.put("person_id", doctor.getPerson_id()+"");
		temp.put("education", attributes.get("education"));
		temp.put("experience", attributes.get("experience"));
		temp.put("updated_at", attributes.get("updated_at"));
		DoctorModel.updateQuery(doctor.getUpdateStatement(finders, temp));
		temp.clear();
		temp.put("person_id", doctor.getPerson_id()+"");
		doctor = new DoctorModel(DoctorModel.runQuery(DoctorModel.getFindStatement(temp)));
	}

	public void addPatient(ConcurrentHashMap<String, String> attributes) throws SQLException, Exception {
		int cur = 1;
		ConcurrentHashMap<String, String> temp = new ConcurrentHashMap<String, String>();
		temp.put("username", attributes.get("first_name").trim().charAt(0)+""+attributes.get("last_name").trim());
		attributes.put("doctor_id", doctor.getDoctor_id()+"");
		while (true) {
			try {
				PersonModel.runQuery(PersonModel.getFindStatement(temp)).getString("id");
				temp.replace("username", attributes.get("first_name").trim().charAt(cur)+""+attributes.get("last_name").trim());
				cur++;
			} catch (SQLException e) {
				break;
			}
			
		}
		
		attributes.put("username", temp.get("username"));
		attributes.put("password", "Onibaba");
		temp.clear();
		temp.put("username", attributes.get("username"));
		temp.put("password", attributes.get("password"));
		temp.put("first_name", attributes.get("first_name"));
		temp.put("gender", attributes.get("gender"));
		temp.put("salary", attributes.get("salary"));
		temp.put("last_name", attributes.get("last_name"));
		temp.put("email", attributes.get("email"));
		temp.put("title", attributes.get("title"));
		temp.put("ssn", attributes.get("ssn"));
		temp.put("allergies", attributes.get("allergies"));
		temp.put("birthday", attributes.get("birthday"));
		temp.put("phone", attributes.get("phone"));
		temp.put("created_at", attributes.get("created_at"));
		temp.put("updated_at", attributes.get("updated_at"));
		PersonModel.updateQuery(PersonModel.getInsertStatement(temp));
		temp.clear();
		temp.put("username", attributes.get("username"));
		ResultSet result = PersonModel.runQuery(PersonModel.getFindStatement(temp));
		temp.clear();
		temp.put("person_id", result.getInt("id")+"");
		temp.put("nurse_id", nurse.getNurse_id()+"");
		temp.put("education", attributes.get("education"));
		temp.put("experience", attributes.get("experience"));
		temp.put("created_at", attributes.get("created_at"));
		temp.put("updated_at", attributes.get("updated_at"));
		PatientModel.updateQuery(PatientModel.getInsertStatement(temp));
		
	}

	public void updatePatient(ConcurrentHashMap<String, String> attributes) throws SQLException, Exception {
		ConcurrentHashMap<String, String> finders = new ConcurrentHashMap<String, String>();
		finders.put("id", patient.getPerson_id()+"");
		ConcurrentHashMap<String, String> temp = new ConcurrentHashMap<String, String>();
		temp.put("first_name", attributes.get("first_name"));
		temp.put("gender", attributes.get("gender"));
		temp.put("salary", attributes.get("salary"));
		temp.put("last_name", attributes.get("last_name"));
		temp.put("email", attributes.get("email"));
		temp.put("title", attributes.get("title"));
		temp.put("ssn", attributes.get("ssn"));
		temp.put("allergies", attributes.get("allergies"));
		temp.put("birthday", attributes.get("birthday"));
		temp.put("phone", attributes.get("phone"));
		temp.put("updated_at", attributes.get("updated_at"));
		PersonModel.updateQuery(PersonModel.getUpdateStatement(finders, temp));
		finders.clear();
		temp.clear();
		finders.put("person_id", patient.getPerson_id()+"");
		temp.put("education", attributes.get("education"));
		temp.put("experience", attributes.get("experience"));
		temp.put("updated_at", attributes.get("updated_at"));
		NurseModel.updateQuery(patient.getUpdateStatement(finders, temp));
		
	}
	
}
