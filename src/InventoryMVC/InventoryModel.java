package InventoryMVC;
import BaseMVC.BasicModel;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryModel extends BasicModel {
	
	private Integer inventory_id = -1;
	private int quantity;
	private int medecine_id;
	// required properties 
	private static String[] requiredOnInsert = {"quantity", "medecine_id" };
	private static String[][] modelRules = { 
		{"id", "integer"},
		{"id", "positive" },
		{"medecine_id", "integer"},
		{"medecine_id", "positive" },
		{"quantity", "integer"}
	};
	private static final String TABLENAME = "inventorys";
	// fillable from the front end properties
	private static String[] fillables = {"quantity", "medecine_id", "id"};
	private static String[] belongstomany = {};
	private static String[] belongsto = {"medecine"};
	private static String[] specials = {"comment"};
	
	public InventoryModel(String query, ResultSet attributes) throws SQLException
	{
		super("inventory", attributes.getInt("id"));
		belongsToManyInstances = belongstomany;
		belongsToInstance = belongsto;
		inventory_id = attributes.getInt("id");
		
		
	}
	
	
	/**
	 * @return the inventory_id
	 */
	public Integer getInventory_id() {
		return inventory_id;
	}


	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}


	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	/**
	 * @return the medecine_id
	 */
	public int getMedecine_id() {
		return medecine_id;
	}


	/**
	 * @param medecine_id the medecine_id to set
	 */
	public void setMedecine_id(int medecine_id) {
		this.medecine_id = medecine_id;
	}


	public ResultSet medecine() throws SQLException
	{
		return belongsTo("medecine");
	}

	public ResultSet comments() throws SQLException
	{
		ConcurrentHashMap<String, String> temp = new ConcurrentHashMap<String, String>();
		temp.put("object_model", "inventory");
		temp.put("object_id", ""+inventory_id);
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


