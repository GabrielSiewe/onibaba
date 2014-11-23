package databaseObjects.beans.Person;
import BaseMVC.BasicModel;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Person extends BasicModel {

	private static ConcurrentHashMap<Integer, Person> existingPersons;
	private final static String[] LOADABLES = {"comment"};
	private final static String[] FILLABLE = {"ssn", "first_name", "last_name", "phone", "email", "title"};
	// ON CREATION WHAT IS REQUIRED
	private final static String[] INSERT = {"ssn", "first_name", "last_name", "title"};
	// ON UPDATE and FIND WHAT IS REQUIRED
	private final static String[] UPDATE = {"ssn"};
	// ON DELETE WHAT IS REQUIRED
	private final static String[] DELETE = {"ssn", "first_name", "last_name", "title"};
	
	private static String insertStatement = null;
	private static String deleteStatement = null;
	private static String updateStatement = null;
	private static String findStatement = null;
	

	private String ssn = null;
	private String first_name = null;
	private String last_name = null;
	private Date birthday = null;
	private Integer phone = 0;
	private String email = null;
	private Date created_at = null;
	private String title = null;
	private Integer person_id = -1;

	public Person(int personId) throws SQLException
	{
		if(existingPersons == null) {
			existingPersons = new ConcurrentHashMap<Integer, Person>();
		}

		person_id = personId;
		
		// alias person information such that updating name on one updates on other.
		if(existingPersons.containsKey(person_id)) {
			setPerson(existingPersons.get(person_id));
			return;
		}
		ResultSet results = null;
		try {
			results = querySelector.runQuery("SELECT * FROM persons WHERE `id`="+person_id, false);
		} catch(NullPointerException e) {
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

		existingPersons.put(person_id, this);
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

	public int getPerson_id() {
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
	
	public static void setDBManipulator(DatabaseManipulator other) {
		querySelector = other;
	}

	public Person getPerson() {
		return this;
	}
	
	public void setPerson(Person other) {
		ssn = other.getSsn();
		first_name = other.getFirst_name();
		last_name = other.getLast_name();
		birthday = other.getBirthday();
		phone = other.getPhone();
		email = other.getEmail();
		created_at = other.getCreated_at();
		title = other.getTitle();
		
	}
	// creating the statements to communicate with the Database.
	private boolean hasTable()
	{
		boolean hasTable = true;
		if (tableName == null)
		{
			insertStatement = deleteStatement = updateStatement = findStatement = "no table name";
			hasTable = false;
		}
		return hasTable;
	}

	protected String getInsertStatement(ConcurrentHashMap<String,String> attributes)
	{
		if (hasTable() && hasValidData(attributes, "insert"))
		{
			insertStatement = "INSERT INTO "+tableName+" ( "+printFields(getkeysArray(attributes.keySet())) + " )"
					+ " VALUES ( "+printValues(getkeysArray(attributes.values()))+" );";
		}
		return insertStatement;
	}

	protected String getDeleteStatement(String[][] idsToFieldsfinder) throws Exception
	{
		if (hasTable() && deleteStatement == null)
		{
			deleteStatement = "DELETE FROM "+ tableName+" WHERE "+matchFieldToValue(idsToFieldsfinder, null, null);
		}
		return deleteStatement;
	}

	protected String getFindStatement(String[][] idsToFieldsfinder, String[] loadables, String[] toload) throws Exception
	{
		if (hasTable() && findStatement == null)
		{
			findStatement = "SELECT * FROM "+tableName+" "+matchFieldToValue(idsToFieldsfinder, loadables, toload);
		}
		return findStatement;
	}

	protected String getUpdateStatement(String[][] idsToFieldsfinder, ConcurrentHashMap<String, String> attributes) throws Exception
	{
		String valuesToField = "";
		if (hasTable() && updateStatement == null)
		{
			String[] keySet = getkeysArray(attributes.keySet());
			String find = matchFieldToValue(idsToFieldsfinder, null, null);
			for(int i = 0; i < keySet.length; i++)
			{
				valuesToField += "`"+ keySet[i]+"`" + "=\"" + attributes.get(keySet[i])+"\",";
			}
			valuesToField = "UPDATE "+ tableName + " SET " + valuesToField + " WHERE "+ find + ";";
		}
		return valuesToField;
	}

	private String matchFieldToValue(String[][] all, String[] loadables, String[] loads) throws Exception
	{
		if (!contains(loadables, loads) )
		{
			String error = "The loadables that were requested are not part of the list of loadables for this model.\n"+
					"Only "+loadables.toString()+" may be loaded from the following model.";
			throw new Exception(error);
		}
		
		String toReturn = loadData(loads);
		for(int i=0; i<all.length; i++)
		{
			toReturn +="WHERE "+ tableName+".`"+ all[i][0]+"`" + "=\"" + all[i][1]+"\""+ ((i == all.length - 1)?"":", ");
		}
		return toReturn;
	}

	private boolean contains(String[] container, String[] set)
	{
		boolean containsAll =  false;

		if ((container == null && set == null) || (container != null && set == null) ) {
			containsAll = true;

		} else if (container == null || (container.length < set.length)) {
			containsAll = false;
	
		} else {
			containsAll = Arrays.asList(container).containsAll(Arrays.asList(set));
		}

		return containsAll;
	}

	private String loadData(String[] loadables)
	{
		String toReturn = "";
		if( loadables != null)
		{ 
			for(int i=0; i<loadables.length; i++)
			{
				String loadable = loadables[i];
				toReturn += " INNER JOIN "+ loadable+"s ON "+tableName+".`"+loadable+"_id`="+loadable+"s.`id` ";
			}
		}
		return toReturn;
	}

	private String printValues(String[] name)
	{
		String toString = "";
		for ( int i=0; i<name.length; i++)
		{
			toString +="\""+ name[i]+"\""+ ((i == name.length - 1)?"":", ");
		}
		return toString;
	}
	
	private String printFields(String[] name)
	{
		String toString = "";
		for ( int i=0; i<name.length; i++)
		{
			toString += name[i]+ ((i == name.length - 1)?"":", ");
		}
		return toString;
	}

	private String[] getkeysArray(Set<String> values)
	{
		return values.toArray(new String[values.size()]);
	}

	private String[] getkeysArray(Collection<String> values)
	{
		return values.toArray(new String[values.size()]);
	}
	public String toString() {
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
