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

	public PersonController()
	{
		super();
	}
	public PersonModel authenticate(ConcurrentHashMap<String, String> properties) {
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
						break;
					case "nurse": 
						statement = NurseModel.getFindStatement(properties);
						results = NurseModel.runQuery(statement);
						nurse = new NurseModel(results);
						break;
					default: System.out.println("invalid person infos.\n"); break;
					}
				}
		} catch (SQLException e) {
			System.out.println("The person with username "+properties.get("username")+" could not be authenticated.");
		}
		return nurse == null ? doctor : nurse;
		
	}
	
	public ArrayList<NurseModel> getDoctorNurses(DoctorModel doctor)
	{
		ArrayList<NurseModel> temp = new ArrayList<NurseModel>();
		try {
			ResultSet nurses = doctor.nurses();
		
			while(nurses.next()) {
				temp.add(new NurseModel(nurses));
				System.out.println(nurses);
			}
		} catch (SQLException e) {
			System.out.println("no nurses found.");
		}
		
		return temp;
	}
}
