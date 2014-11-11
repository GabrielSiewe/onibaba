package databaseObjects.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class Doctor extends Person {

	private final static String TABLENAME = "doctors";
	private int doctor_id = -1;
	private int person_id = -1;
	private String education = null;
	private String experience = null;
	private final static String[] loadables = { }; 
	
	public Doctor(ResultSet results) throws SQLException
	{
		super(results.getInt("person_id"));

		doctor_id = results.getInt("id");
		person_id = results.getInt("person_id");
		
		education = results.getString("education");
		experience = results.getString("experience");
	}
	public int getDoctor_id() {
		return doctor_id;
	}

	public int getPerson_id()
	{
		return person_id;
	}

	public String getEduction() {
		return education;
	}
	
	public String getExperience() {
		return experience;
	}
	
	public String getTable()
	{
		return TABLENAME;
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
