package InvoiceMVC;

import BaseMVC.BasicModel;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class InvoiceModel extends BasicModel {
	
	private Integer invoice_id = -1;
	private double total_cost;

	// required properties 
	private static String[] requiredOnInsert = {"total_cost" };
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
	private static String[] fillables = {"total_cost", "id"};
	private static String[] belongstomany = {"desease_method", "desease_medecin"};
	private static String[] belongsto = {"lab", "prescription"};
	
	public InvoiceModel(String query, ResultSet attributes) throws SQLException
	{
		super("invoice", attributes.getInt("id"));
		belongsToManyInstances = belongstomany;
		belongsToInstance = belongsto;
		invoice_id = attributes.getInt("id");
		total_cost = attributes.getDouble("total_cost");
		
	}
	
	
	/**
	 * @return the invoice_id
	 */
	public Integer getInvoice_id() {
		return invoice_id;
	}

	/**
	 * @return the total_cost
	 */
	public double getTotal_cost() {
		return total_cost;
	}


	/**
	 * @param total_cost the total_cost to set
	 */
	public void setTotal_cost(double total_cost) {
		this.total_cost = total_cost;
	}


	public ResultSet lab() throws SQLException
	{
		return belongsTo("lab");
	}
	public ResultSet prescription() throws SQLException
	{
		return belongsTo("prescription");
	}

	public ResultSet comments() throws SQLException
	{
		ConcurrentHashMap<String, String> temp = new ConcurrentHashMap<String, String>();
		temp.put("object_model", "invoice");
		temp.put("object_id", ""+invoice_id);
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


