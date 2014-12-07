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
	
	private ConcurrentHashMap<String, PatientModel> patientList;
	private ConcurrentHashMap<String, NurseModel> nurseList;
	
	public PersonController()
	{
		super();
		nurseList = new ConcurrentHashMap<String, NurseModel>();
	}

	public void authenticate(ConcurrentHashMap<String, String> properties)
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
			NurseModel nurse = new NurseModel(nurses);
			nurseList.put(nurse.toString(), nurse);
			cursorPosition++;
			setDoctorNurses();
			
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
	
	public boolean currentlyLoggedAsDoctor()
	{
		return doctor != null;
	}
	public boolean currentlyLoggedAsNurse()
	{
		return doctor == null && nurse != null;
	}

	public void setDoctorPatients() {
		// TODO Auto-generated method stub
		
	}

	public void setNurse(NurseModel nurseModel) {
		nurse = nurseModel;
	}

	public void setNursePatients() {
		// TODO Auto-generated method stub
		
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
		System.out.println("after");
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

	public void removeNurse() throws SQLException
	{
		ConcurrentHashMap<String, String> attributes = new ConcurrentHashMap<String, String>();
		attributes.put("id", nurse.getPerson_id()+"");
		PersonModel.updateQuery(PersonModel.getDeleteStatement(attributes));
		nurse = null;
		return;
	}

	public void recoverPasswordViaEmail(String text)
	{
		// The person to recover their email.
		PersonModel person = null;
		
	}

	public void updateNurse(ConcurrentHashMap<String, String> attributes) throws SQLException {
		ConcurrentHashMap<String, String> finders = new ConcurrentHashMap<String, String>();
		finders.put("person_id", nurse.getPerson_id()+"");
		System.out.println(PersonModel.getUpdateStatement(finders, attributes));
		System.out.println("after");

		PersonModel.updateQuery(PersonModel.getUpdateStatement(finders, attributes));
		
	}
	
}
