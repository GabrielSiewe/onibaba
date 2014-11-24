package databaseObjects.beans.CommentMVC;

import BaseMVC.BasicModel;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class CommentModel extends BasicModel {
	
	private Integer comment_id = -1;
	private Integer person_id = -1;
	private Integer object_id = -1;
	private String object_model;
	private String message;

	// required properties 
	private static String[] requiredOnInsert = {"person_id", "object_id", "object_model", "message" };
	private static String[][] modelRules = { 
		{"object_model", "string"},
		{"message", "string"},
		{"message", "uppercase"},
		{"id", "integer"},
		{"id", "positive" },
		{"person_id", "integer"},
		{"person_id", "positive" },
		{"object_id", "integer"},
		{"object_id", "positive" }
	};
	private static final String TABLENAME = "deseases";
	// fillable from the front end properties
	private static String[] fillables = {"person_id", "object_id", "object_model", "message", "id"};
	private static String[] belongsto = {"person"};
	private static String[] specials = {"comment"};
	
	public CommentModel(String query, ResultSet attributes) throws SQLException
	{
		super("comment", attributes.getInt("id"));
		belongsToInstance = belongsto;
		comment_id = attributes.getInt("id");
		person_id = attributes.getInt("person_id");
		object_id = attributes.getInt("object_id");
		object_model = attributes.getString("object_model");
		message = attributes.getString("message");

	}
	
	/**
	 * @return the comment_id
	 */
	public Integer getComment_id() {
		return comment_id;
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
	 * @return the object_id
	 */
	public Integer getObject_id() {
		return object_id;
	}

	/**
	 * @param object_id the object_id to set
	 */
	public void setObject_id(Integer object_id) {
		this.object_id = object_id;
	}

	/**
	 * @return the object_model
	 */
	public String getObject_model() {
		return object_model;
	}

	/**
	 * @param object_model the object_model to set
	 */
	public void setObject_model(String object_model) {
		this.object_model = object_model;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public ResultSet person() throws SQLException
	{
		return belongsTo("lab");
	}
	
	public ResultSet comments() throws SQLException
	{
		ConcurrentHashMap<String, String> temp = new ConcurrentHashMap<String, String>();
		temp.put("object_model", "comment");
		temp.put("object_id", ""+comment_id);
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

	// Tester
	public static void main(String[] args)
	{
		// First let's test that 
		String findStatement;
		ConcurrentHashMap<String, String> attributes = new ConcurrentHashMap<String, String>();
		attributes.put("description", "All for one and one for all.");
		attributes.put("id", "1");
		findStatement = CommentModel.getDeleteStatement(attributes);
		System.out.println(findStatement);
		CommentModel.closeDbConnection();
	}
}

