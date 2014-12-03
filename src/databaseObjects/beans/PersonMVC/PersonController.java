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
	private static int cursorPosition = 1;
	
	private ConcurrentHashMap<String, PatientModel> patientList;
	private ConcurrentHashMap<String, NurseModel> nurseList;
	
	public PersonController()
	{
		super();
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
		
		nurseList = new ConcurrentHashMap<String,NurseModel>();
		try
		{
			ResultSet nurses = doctor.nurses();
			nurses.beforeFirst();
			while(nurses.next()) {
				NurseModel nurse = new NurseModel(nurses);
				nurseList.put(nurse.toString(), nurse);
			}
		} catch (SQLException e) {
			System.out.println("No nurses were found");
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
	
}
