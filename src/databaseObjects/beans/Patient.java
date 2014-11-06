package databaseObjects.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
public class Patient extends Person {
	
	private static final String TABLENAME = "patients";
	private final static String[] loadables = { "appointment", "prescription"};
	private int patient_id = -1;
	private int nurse_id = -1;
	private Nurse nurse = null;

	public Patient(ResultSet results) throws SQLException
	{
		super(results.getInt("person_id"));
		nurse_id = results.getInt("nurse_id");
		patient_id = results.getInt("id");
	}

	public int getPatient_id() {
		return patient_id;
	}

	public void setNurse(Nurse patientsNurse) throws SQLException 
	{
		if (patientsNurse.getNurse_id() != nurse_id)
			throw new SQLException("This nurse is not the appropriate Nurse for the following patient.");

		nurse = patientsNurse;
	}

	public Nurse getNurse() {
		return nurse;
	}
	public Doctor getDoctor() throws SQLException 
	{
		if( nurse == null)
			throw new SQLException("you need to set the patient's nurse before getting his doctor.");
		
		return nurse.getDoctor();
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
		return super.toString()+" will be visiting again ";
	}
}