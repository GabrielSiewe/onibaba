package databaseObjects.beans;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Person {

	protected static DatabaseManipulator querySelector = null;
	private String ssn = null;
	private String first_name = null;
	private String last_name = null;
	private Date birthday = null;
	private int phone = 0;
	private String email = null;
	private Date created_at = null;
	private String title = null;
	private int person_id = -1;

	public Person(int personId) throws SQLException
	{
		person_id = personId;
		
		
		ResultSet results = querySelector.runQuery("SELECT * FROM persons WHERE `id`="+person_id);

		if (results == null) {
			throw new SQLException("The person with id "+person_id+" couldn't be found. Aborting creation of person.");
		}

		results.first();
		ssn = results.getString("ssn");
		first_name = results.getString("first_name");
		last_name = results.getString("last_name");
		birthday = results.getDate("birthday");
		phone = results.getInt("phone");
		email = results.getString("email");
		created_at = results.getDate("created_at");
		title = results.getString("title");
	}

	protected ConcurrentHashMap<String,String> getPersonalInformation()
	{
		ConcurrentHashMap<String, String> toReturn = new ConcurrentHashMap<String,String>();
		toReturn.put("person_id", ""+person_id);
		toReturn.put("ssn", ssn);
		toReturn.put("first_name", first_name);
		toReturn.put("last_name", last_name);
		toReturn.put("birthday", birthday.toString());
		toReturn.put("phone", ""+phone);
		toReturn.put("email", email);
		toReturn.put("title", title);
		return toReturn;
	}

	public int getPerson_id()
	{
		return person_id;
	}

	public String getSsn() {
		return ssn;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public int getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public String getTitle() {
		return title;
	}

	public static void setDBManipulator(DatabaseManipulator other)
	{
		querySelector = other;
	}

	public Person getPerson()
	{
		return this;
	}

	public String toString()
	{
		return title+" "+first_name+" "+last_name;
	}

	protected static String getStatement(String method, String[][] fieldsToIds, ConcurrentHashMap<String, String> attributes, String tableName, String[] loadables, String[] toload) throws Exception
	{
		if (querySelector == null ){
			throw new Exception("A database was not set for this model");
		}

		querySelector.setTable(tableName);

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
