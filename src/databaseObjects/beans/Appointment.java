package databaseObjects.beans;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Appointment {

	protected static DatabaseManipulator querySelector = null;
	private final static String  TABLENAME = "appointments";
	private Date appointment_date = null;
	private Person issuer = null;
	private String subject = null;
	private String title = null;
	private final static String[] loadables = {"person"};


	public Appointment(ResultSet results) throws SQLException
	{

		if (results != null) {
			results.first();
			title = results.getString("title");
		}
	}
	protected ConcurrentHashMap<String,String> getPersonalInformation()
	{
		ConcurrentHashMap<String, String> toReturn = new ConcurrentHashMap<String,String>();
		toReturn.put("title", title);
		return toReturn;
	}
	public Person getIssuer()
	{
		return issuer;
	}
	public Date getAppointment_date() {
		return appointment_date;
	}
	public String getSubject() {
		return subject;
	}
	public String getTitle() {
		return title;
	}
	public static void setDBManipulator(DatabaseManipulator other)
	{
		querySelector = other;
	}
	public String toString()
	{
		return issuer.getFirst_name()+" prescribed ";
	}
	protected static String getStatement(String method, String[][] fieldsToIds, ConcurrentHashMap<String, String> attributes, String[] toload) throws Exception
	{
		if (querySelector == null ){
			throw new Exception("A database was not set for this model");
		}
				
		querySelector.setTable(TABLENAME);

		switch(method)
		{
			case "update":
				return querySelector.getUpdateStatement(fieldsToIds, attributes);
			case "add":
				return querySelector.getInsertStatement(attributes);
			case "remove":
				return querySelector.getDeleteStatement(fieldsToIds);
			default:
				return querySelector.getFindStatement(fieldsToIds, loadables, toload);
		}
	}
}
