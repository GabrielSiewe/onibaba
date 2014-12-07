package databaseObjects.beans.MethodMVC;
import BaseMVC.BasicModel;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class MethodModel extends BasicModel {
	
	private Integer method_id = -1;
	private String name;
	private String description;
	private double cost;
	// required properties 
	private static String[] requiredOnInsert = {"name", "description", "symptoms" };
	private static String[][] modelRules = { 
		{"name", "string"},
		{"name", "uppercase"},
		{"description", "string"},
		{"symptons", "string"},
		{"id", "integer"},
		{"id", "positive" } 
	};
	private static final String TABLENAME = "deseases";
	// fillable from the front end properties
	private static String[] fillables = {"name", "description", "symptoms", "id"};
	private static String[] belongstomany = {"desease_method"};
	private static String[] belongsto = {"lab"};
	private static String[] specials = {"comment"};
	
	public MethodModel(String query, ResultSet attributes) throws SQLException
	{
		super("disease", attributes.getInt("id"));
		belongsToManyInstances = belongstomany;
		belongsToInstance = belongsto;
		method_id = attributes.getInt("id");
		name = attributes.getString("name");
		description = attributes.getString("description");
		cost = attributes.getDouble("cost");
		
	}
	
	
	/**
	 * @return the method_id
	 */
	public Integer getMethod_id() {
		return method_id;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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


	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}


	/**
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}


	public ResultSet labs() throws SQLException
	{
		return belongsTo("lab");
	}
	
	public ResultSet methodDeseases() throws SQLException
	{
		return belongsToMany("desease_method", "desease_methods");
	}

	public ResultSet comments() throws SQLException
	{
		ConcurrentHashMap<String, String> temp = new ConcurrentHashMap<String, String>();
		temp.put("object_model", "method");
		temp.put("object_id", ""+method_id);
		return specials(temp, "comments");
	}
	
	// model queries.
	public static String getInsertStatement(ConcurrentHashMap<String,String> attributes) throws Exception
	{
		attributes = validateData(attributes, "insert");
		if ( attributes == null) {
			System.out.println("Your input are missing some of the required fields for inserts.");
			return null;
		}
		return getInsertStatement(attributes, TABLENAME);
	}

	public static String getDeleteStatement(ConcurrentHashMap<String,String> finders) throws Exception
	{
		finders = validateData(finders, null);
		if (finders == null) {
			System.out.println("None of the inputed data were valid. Please try again.");
			return null;
		}
		return getDeleteStatement(finders, TABLENAME);
	}

	public static String getFindStatement(ConcurrentHashMap<String,String> finders) throws Exception
	{
		finders = validateData(finders, null);
		if (finders == null) {
			System.out.println("None of the inputed data were valid. Please try again.");
			return null;
		}
		return getFindStatement(finders, TABLENAME);
	}

	public static String getUpdateStatement(ConcurrentHashMap<String, String> finders, ConcurrentHashMap<String, String> attributes) throws Exception
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
	protected static ConcurrentHashMap<String, String> validateData(ConcurrentHashMap<String, String> attributes, String methodName) throws Exception
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
					
					if ( value == null && Arrays.asList(requiredOnInsert).contains(keys[i])) {
						attributes.remove(keys[i]);
						throw new Exception(keys[i]+ " is required. Please enter a value.");

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
}

