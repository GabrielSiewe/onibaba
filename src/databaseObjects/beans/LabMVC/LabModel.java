package databaseObjects.beans.LabMVC;
import BaseMVC.BasicModel;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class LabModel extends BasicModel {
	
	private Integer lab_id = -1;
	private Integer invoice_id = -1;
	private Integer method_id = -1;
	private Integer desease_id = -1;
	private Integer medecine_id = -1;
	private String result_explanation;
	
	// required properties 
	private static String[] requiredOnInsert = {"result_explanation", "invoice_id", "method_id", "desease_id", "medecine_id" };
	private static String[][] modelRules = { 
		{"result_explanation", "string"},
		{"id", "integer"},
		{"id", "positive" },
		{"invoice_id", "integer"},
		{"invoice_id", "positive" },
		{"method_id", "integer"},
		{"method_id", "positive" },
		{"desease_id", "integer"},
		{"desease_id", "positive" },
		{"medecine_id", "integer"},
		{"medecine_id", "positive" }
	};
	private static final String TABLENAME = "deseases";
	// fillable from the front end properties
	private static String[] fillables = {"result_explanation", "invoice_id", "method_id", "desease_id", "medecine_id", "id"};
	private static String[] belongsto = {"invoice", "method", "desease", "medecine"};
	private static String[] specials = {"comment"};
	
	public LabModel(String query, ResultSet attributes) throws SQLException
	{
		super("lab", attributes.getInt("id"));
		belongsToInstance = belongsto;
		lab_id = attributes.getInt("id");
		invoice_id = attributes.getInt("invoice_id");
		method_id = attributes.getInt("method_id");
		desease_id = attributes.getInt("desease_id");
		medecine_id = attributes.getInt("medecine_id");
		result_explanation = attributes.getString("result_explanation");
	}
	
	/**
	 * @return the lab_id
	 */
	public Integer getLab_id() {
		return lab_id;
	}

	/**
	 * @return the invoice_id
	 */
	public Integer getInvoice_id() {
		return invoice_id;
	}

	/**
	 * @param invoice_id the invoice_id to set
	 */
	public void setInvoice_id(Integer invoice_id) {
		this.invoice_id = invoice_id;
	}

	/**
	 * @return the method_id
	 */
	public Integer getMethod_id() {
		return method_id;
	}

	/**
	 * @param method_id the method_id to set
	 */
	public void setMethod_id(Integer method_id) {
		this.method_id = method_id;
	}

	/**
	 * @return the desease_id
	 */
	public Integer getDesease_id() {
		return desease_id;
	}

	/**
	 * @param desease_id the desease_id to set
	 */
	public void setDesease_id(Integer desease_id) {
		this.desease_id = desease_id;
	}

	/**
	 * @return the medecine_id
	 */
	public Integer getMedecine_id() {
		return medecine_id;
	}

	/**
	 * @param medecine_id the medecine_id to set
	 */
	public void setMedecine_id(Integer medecine_id) {
		this.medecine_id = medecine_id;
	}

	/**
	 * @return the result_explanation
	 */
	public String getResult_explanation() {
		return result_explanation;
	}

	/**
	 * @param result_explanation the result_explanation to set
	 */
	public void setResult_explanation(String result_explanation) {
		this.result_explanation = result_explanation;
	}

	public ResultSet invoice() throws SQLException
	{
		return belongsTo("invoice");
	}
	public ResultSet method() throws SQLException
	{
		return belongsTo("method");
	}
	public ResultSet desease() throws SQLException
	{
		return belongsTo("desease");
	}
	public ResultSet medecine() throws SQLException
	{
		return belongsTo("medecine");
	}
	public ResultSet comments() throws SQLException
	{
		ConcurrentHashMap<String, String> temp = new ConcurrentHashMap<String, String>();
		temp.put("object_model", "disease");
		temp.put("object_id", ""+lab_id);
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
		return result_explanation;
	}
}

