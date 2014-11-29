package databaseObjects.beans.PersonMVC;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.concurrent.ConcurrentHashMap;

import BaseMVC.BasicController;

public class PersonController extends BasicController {
	private PersonModel user;
	
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
						user = new DoctorModel(results);
						break;
					case "nurse": 
						statement = NurseModel.getFindStatement(properties);
						results = NurseModel.runQuery(statement);
						user = new NurseModel(results);
						break;
					default: System.out.println("invalid person infos.\n"); user.closeDbConnection(); break;
					}
				}
		} catch (SQLException e) {
			System.out.println("The person with username "+properties.get("username")+" could not be authenticated.");
		}
		return user;
		
	}
}
