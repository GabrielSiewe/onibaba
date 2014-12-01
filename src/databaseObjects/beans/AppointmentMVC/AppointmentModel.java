package databaseObjects.beans.AppointmentMVC;

import BaseMVC.BasicModel;

import java.util.ArrayList;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import databaseObjects.beans.PersonMVC.PersonModel;

public class AppointmentModel extends BasicModel {
	
	private Integer appointment_id = -1;
	private Integer doctor_id = -1;
	private Integer person_id = -1;
	private Date appointment_date = null;
	private String description;

	// required properties 
	private static String[] requiredOnInsert = {"doctor_id", "description", "person_id", "appointment_date" };
	private static String[][] modelRules = { 
		{"doctor_id", "integer"},
		{"doctor_id", "positive"},
		{"person_id", "integer"},
		{"person_id", "positive"},
		{"description", "string"},
		{"appointment_date", "date"},
		{"appointment_date", "future"},
		{"id", "integer"},
		{"id", "positive" } 
	};
	private static final String TABLENAME = "appointments";
	// fillable from the front end properties
	private static String[] fillables = {"doctor_id", "description", "person_id", "appointment_date", "id"};
	private static String[] hasMany = {"prescription"};
	private static String[] belongsto = {"person", "doctor"};
	private static String[] specials = {"comment", "patient"};
	
	public AppointmentModel(String query, ResultSet attributes) throws SQLException
	{
		super("appointment", attributes.getInt("id"));

		appointment_id = attributes.getInt("id");
		doctor_id = attributes.getInt("doctor_id");
		person_id = attributes.getInt("person_id");
		description = attributes.getString("description");
		appointment_date = attributes.getDate("appointment_date");

		belongsToInstance = belongsto;
		hasManyInstances = hasMany;
		
		
	}
	
	/**
	 * @return the appointment_id
	 */
	public Integer getAppointment_id() {
		return appointment_id;
	}

	/**
	 * @return the doctor_id
	 */
	public Integer getDoctor_id() {
		return doctor_id;
	}

	/**
	 * @param doctor_id the doctor_id to set
	 */
	public void setDoctor_id(Integer doctor_id) {
		this.doctor_id = doctor_id;
	}

	/**
	 * @return the person_id
	 */
	public Integer getPerson_id() {
		return person_id;
	}

	/**
	 * @param person_id the person_id to set
	 */
	public void setPerson_id(Integer person_id) {
		this.person_id = person_id;
	}

	/**
	 * @return the appointment_date
	 */
	public Date getAppointment_date() {
		return appointment_date;
	}

	/**
	 * @param appointment_date the appointment_date to set
	 */
	public void setAppointment_date(Date appointment_date) {
		this.appointment_date = appointment_date;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public ResultSet person() throws SQLException
	{
		return belongsTo("person");
	}
	
	public ResultSet doctor() throws SQLException
	{
		return belongsTo("doctor");
	}
	
	public ResultSet prescriptions() throws SQLException
	{
		return hasMany("prescriptions");
	}

	public ResultSet comments() throws SQLException
	{
		ConcurrentHashMap<String, String> temp = new ConcurrentHashMap<String, String>();
		temp.put("object_model", "appointment");
		temp.put("object_id", ""+appointment_id);
		return specials(temp, "comments");
	}
	
	// model queries.
	public static String getInsertStatement(ConcurrentHashMap<String,String> attributes)
	{
		attributes = validateData(attributes, "insert");
		if ( attributes == null) {
			System.out.println("Your input are missing some of the required fields for inserts.");
			return null;
		}
		return getInsertStatement(attributes, TABLENAME);
	}

	public static String getDeleteStatement(ConcurrentHashMap<String,String> finders)
	{
		finders = validateData(finders, null);
		if (finders == null) {
			System.out.println("None of the inputed data were valid. Please try again.");
			return null;
		}
		return getDeleteStatement(finders, TABLENAME);
	}

	public static String getFindStatement(ConcurrentHashMap<String,String> finders)
	{
		finders = validateData(finders, null);
		if (finders == null) {
			System.out.println("None of the inputed data were valid. Please try again.");
			return null;
		}
		return getFindStatement(finders, TABLENAME);
	}

	public static String getUpdateStatement(ConcurrentHashMap<String, String> finders, ConcurrentHashMap<String, String> attributes)
	{
		attributes = validateData(attributes, null);
		if (attributes == null) {
			System.out.println("None of the inputed data for your attributes were valid. Please try again.");
			return null;
		}
		finders = validateData(finders, null);
		if (finders == null) {
			System.out.println("None of the inputed data for your finders were valid. Please try again.");
			return null;
		}
		return getUpdateStatement(finders, attributes, TABLENAME);
	}
	
	
	// Validating the data.
	// removes unfillable fields and watches out for sql data.
	protected static ConcurrentHashMap<String, String> validateData(ConcurrentHashMap<String, String> attributes, String methodName)
	{
		// attributes.keySet().toArray();
		String[] keys = attributes.keySet().toArray(new String[attributes.size()]);
		ConcurrentHashMap<String, ArrayList<String>> fieldRules = new ConcurrentHashMap<String, ArrayList<String>>(); 
		
		// then we it could be anything except an insert statement. In which case all we have to do is make sure the attributes are fillable
		if (methodName == null) {

			for( int i = 0; i< keys.length; i++)
			{		
				// if it contains and the value isn't null we keep it
				if (Arrays.asList(fillables).contains(keys[i]))  {

					if (fieldRules.get(keys[i]) == null) {
						fieldRules.put(keys[i], new ArrayList<String>());
					}

					// if it is part of the fillable arrays then it has a set of rules that apply to it.
					for( int k = 0; k < modelRules.length; k++) {

						if (modelRules[k][0].equals(keys[i])) {
							fieldRules.get(keys[i]).add( modelRules[k][1]);
						}
					}
					
					String value = evaluateFieldRule(attributes.get(keys[i]), fieldRules.get(keys[i]).toArray(new String[fieldRules.get(keys[i]).size()]));
					
					if ( value == null) {
						System.out.println("The field "+keys[i]+" has a null value. Therefore it is being discarder.");
						attributes.remove(keys[i]);
						// If we are inserting and the field is required, we cancel and tell them to retry.
						if (methodName == "insert" && Arrays.asList(requiredOnInsert).contains(keys[i])) {
							System.out.println("However the field "+keys[i]+ " is required. Please try again.");
							return null;
						}

					} else {
						attributes.replace(keys[i], value);
					}
					continue;
				}
				// else we have an ambiguous field name, and we must remove them.
				attributes.remove(keys[i]);
			}
		}
		return attributes;
	}
	
	public String toString()
	{
		try{
			return "Appointment on: "+appointment_date.toString()+" for "+(new PersonModel(person_id));
		} catch (SQLException e) {
			return null;
		}
	}

	// Tester
	public static void main(String[] args)
	{
		// First let's test that 
		String findStatement;
		ConcurrentHashMap<String, String> attributes = new ConcurrentHashMap<String, String>();
		attributes.put("description", "All for one and one for all.");
		attributes.put("id", "1");
		findStatement = AppointmentModel.getDeleteStatement(attributes);
		System.out.println(findStatement);
		AppointmentModel.closeDbConnection();
	}
}

