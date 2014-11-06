package databaseObjects.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class Nurse extends Person{
	
	private final static String TABLENAME = "nurses";
	private int nurse_id = -1;
	private Doctor doctor = null;
	private String education = null;
	private String experience = null;
	private final static String[] loadables = { "patient", "comment" }; 

	public Nurse(ResultSet results) throws SQLException
	{
		super(results.getInt("person_id"));
		doctor = new Doctor(results);
		nurse_id = results.getInt("id");
		education = results.getString("education");
		experience = results.getString("experience");
	}

	public Doctor getDoctor() {
		return doctor;
	}
	public int getNurse_id() {
		return nurse_id;
	}
	public String getEduction() {
		return education;
	}

	public String getExperience() {
		return experience;
	}

	public static String getStatement(String method, String[][] indentifiers, ConcurrentHashMap<String, String> attributes, String[] toload) throws Exception
	{
		return Person.getStatement(method, indentifiers, attributes, TABLENAME, loadables, toload);
	}

	public String toString()
	{
		return super.toString()+" has been in the field for "+experience+".\nShe received her degree from "+education;
	}
}
