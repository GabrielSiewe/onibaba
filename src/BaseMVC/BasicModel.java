package BaseMVC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import databaseObjects.beans.*;

public class BasicModel {
	
	protected static DatabaseManipulator queryRunner = null;
	protected static String[][] modelRules; 

	protected String modelName;
	protected String tableName;
	protected int modelId;

	// fillable from the front end properties
	protected static String[] fillables;

	// required properties 
	protected static String[] requiredOnInsert;
	
	protected String[] hasManyInstances;
	protected String[] belongsToManyInstances;
	protected String[] belongsToInstance;
	
	public BasicModel(String modelName, int modelId)
	{
		this.modelName = modelName;
		this.modelId = modelId;
	}
	
	
	// Querying the models returns the records that this model may have one of.
	protected ResultSet belongsTo(String model) throws SQLException
	{
		if (model != null && belongsToInstance.toString().indexOf(model) != -1 && queryRunner != null) {
			return queryRunner.runQuery(load(model, null), false);
		}
		throw new SQLException("this "+modelName+" doesn't belong to a "+model);
	}

	// Returns the records that this model may have many off.
	protected ResultSet hasMany(String model) throws SQLException
	{
		if (model != null && hasManyInstances.toString().indexOf(model) != -1 && queryRunner != null) {
			return queryRunner.runQuery(load(model, null), false);
		}
		throw new SQLException("this "+modelName+" doesn't have many "+model);
	};

	// Returns the records that may have been for this model.
	protected ResultSet belongsToMany(String model, String tableName) throws SQLException
	{
		if (tableName != null && model != null && belongsToManyInstances.toString().indexOf(model) != -1 && queryRunner != null) {
			return queryRunner.runQuery(load(model, tableName), false);
		}
		throw new SQLException("this "+modelName+" doesn't belong to nor does it have many "+model);
		
	}
	
	// runs an SQL query
	protected static ResultSet runQuery(String query) throws SQLException
	{
		if (query != null && query.trim() != null) {
			return queryRunner.runQuery(query, false);
		}
		throw new SQLException("you are trying to run an empty query.");
		
	}

	// Makes sure to load all the models that are part of this array of models.
	protected String load(String model, String manyToManyTable)
	{
		String query = "";

		if (manyToManyTable != null)
			query = "SELECT * FROM "+manyToManyTable+" WHERE "+modelName+"_id = "+modelId;
		else
			query = "SELECT * FROM "+model+"s WHERE "+modelName+"_id = "+modelId;

		return query;
	}
	

	// querying the model.
	protected static String getInsertStatement(ConcurrentHashMap<String,String> attributes, String table_name)
	{
		String toReturn = null;
		attributes = validateData(attributes, "insert");
		if (attributes != null || attributes.size() != 0) {
			toReturn = "INSERT INTO "+table_name+" ( "+printFields(getkeysArray(attributes.keySet())) + " )"
					+ " VALUES ( "+printValues(getkeysArray(attributes.values()))+" );";
		}
		return toReturn;
	}

	protected static String getDeleteStatement(ConcurrentHashMap<String,String> finders, String table_name)
	{
		String toReturn = null;
		finders = finders = validateData(finders,null);
		if (finders != null || finders.size() != 0) {
			toReturn = "DELETE * FROM "+table_name+" WHERE ( "+matchFieldToValue(finders, table_name);
		}
		return toReturn;
	}

	protected static String getFindStatement(ConcurrentHashMap<String,String> finders, String table_name)
	{
		String findStatement = null;
		finders = validateData(finders,null);
		if (finders != null || finders.size() != 0) {
			findStatement = "SELECT * FROM "+table_name+" WHERE ("+matchFieldToValue(finders, table_name);
		}
		return findStatement;
	}

	protected static String getUpdateStatement(ConcurrentHashMap<String, String> finders, ConcurrentHashMap<String, String> attributes, String table_name)
	{
		String updateStatement = "";
		finders = validateData(finders,null);
		attributes = validateData(attributes, null);
		if( finders != null && attributes != null && finders.size() != 0 && attributes.size() != 0) {
			String[] keySet = getkeysArray(attributes.keySet());
			String find = matchFieldToValue(finders, table_name);
			for(int i = 0; i < keySet.length; i++)
			{
				updateStatement += "`"+ keySet[i]+"`" + "=\"" + attributes.get(keySet[i])+"\",";
			}
			updateStatement = "UPDATE "+ table_name + " SET (" + updateStatement + ") WHERE ("+ find + ");";
		}
		return updateStatement;
	}

	private static String matchFieldToValue(ConcurrentHashMap<String, String> attributes, String table_name)
	{	
		String toReturn = "";
		String[] keySet = getkeysArray(attributes.keySet());
		for(int i=0; i<keySet.length; i++)
		{
			String key = keySet[i];
			
			toReturn += "`"+ key+"`" + "=\"" +attributes.get(key)+"\""+ ((i == keySet.length - 1)?");":", AND ");
		}
		return toReturn;
	}

	private static String printValues(String[] name)
	{
		String toString = "";
		for ( int i=0; i<name.length; i++)
		{
			toString +="\""+ name[i]+"\""+ ((i == name.length - 1)?"":", ");
		}
		return toString;
	}
	
	private static String printFields(String[] name)
	{
		String toString = "";
		for ( int i=0; i<name.length; i++)
		{
			toString += name[i]+ ((i == name.length - 1)?"":", ");
		}
		return toString;
	}

	private static String[] getkeysArray(Set<String> values)
	{
		return values.toArray(new String[values.size()]);
	}

	private static String[] getkeysArray(Collection<String> values)
	{
		return values.toArray(new String[values.size()]);
	} 
	
	
	// Validating the data.
	// removes unfillable fields and watches out for sql data.
	protected static ConcurrentHashMap<String, String> validateData(ConcurrentHashMap<String, String> attributes, String methodName)
	{
		String[] keys = (String[]) attributes.keySet().toArray();
		
		// then we it could be anything except an insert statement. In which case all we have to do is make sure the attributes are fillable
		if (methodName == null) {
			for( int i=0; i< keys.length; i++)
			{	
				
				// if it contains and the value isn't null we keep it
				if (Arrays.asList(fillables).contains(keys[i]))  {
					// if it is part of the fillable arrays then it has a set of rules that apply to it.
					String[] rules = null;
					for( int k = 0; k < modelRules.length; k++) {
						if (modelRules[k].equals(keys[i])) {
							rules = modelRules[k];
						}
					}
					attributes.replace(keys[i], evaluateFieldRule(attributes.get(keys[i]), rules));
					if ( attributes.get(keys[i]) == null) {
						System.out.println("The field "+keys[i]+" has a null value. Therefore it is being discarder.");
						attributes.remove(keys[i]);
					}
					continue;
				}
				// else we have an ambiguous field name, and we must remove them.
				attributes.remove(keys[i]);
			}
		}
		// else we need to make sure that all fields are fillable and that the required fields are already added.
		for( int i=0; i< keys.length; i++)
		{	
			// if it contains and the value isn't null we keep it
			if (Arrays.asList(fillables).contains(keys[i]))  {
				
				// if the value is null we discard it. and if the value was required, we tell them to replace it.
				if ( attributes.get(keys[i]) == null) {
					System.out.println("The field "+keys[i]+" has a null value. Therefore it is being discarder.");
					attributes.remove(keys[i]);
					if (Arrays.asList(requiredOnInsert).contains(keys[i])) {
						System.out.println("However the field "+keys[i]+ " is required. Please try again.");
						return null;
					}
					
				}
				continue;
			}
			// else we have an ambiguous field name, and we must remove them.
			attributes.remove(keys[i]);
		}
		return attributes;
	}
	
	// rule for string:{ {"name", {"string", "uppercase", "max:255"}} }
	private static String evaluateFieldRule(String value, String[] rules)
	{
		String datatype = rules[0];
		switch(datatype) {
			case "string": return checkStrings(value, rules);
			case "integer": return checkIntegers(value, rules);
			case "date" : return checkDates(value, rules);
			case "double": return checkDoubles(value, rules);
			default: System.out.println("invalid data type: "+datatype+" for value: "+ value); return null;
		}
	}

	// rule for string:{ {"name", {"string", "uppercase", "max:255"}} }
	private static String checkStrings(String value, String[] rules)
	{
		if (value == null || value.trim() == null) return null;
		value = value.trim();
		Pattern remover = Pattern.compile("\\[\\s_]\\", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		value = remover.matcher(value).replaceAll(" ");

		if (value.length() >= 256) {
			System.out.println(value + " is too long.");
			return null;
		}

		for( int i = 0; i< rules.length; i++) {
			String cuRule = rules[i];
			switch(cuRule) {
				case "string": break;
				case "uppercase": value = value.toUpperCase(); break;
				case "lowercase": value = value.toLowerCase(); break;
				default: System.out.println("didn't do rule: "+cuRule); break;
			}
		}
		return value;
	}

	// rule for date:{ {"appointment_date", {"date", "future", "past"}} }
	private static String checkDates(String value, String[] rules)
	{
		if (value == null || value.trim() == null) return null;
		value = value.trim();
		Date test = null;
		Pattern remover = Pattern.compile("\\[\\s_]\\", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		value = remover.matcher(value).replaceAll(" ");
		
		for( int i = 0; i< rules.length; i++) {
			String cuRule = rules[i];
			switch(cuRule){
				case "date": try { test = new Date(Date.parse(value)); break;} catch (Exception e) { System.out.println("this is an invalid date"); return null;}
				case "future": if ( test.before(new Date()) ){ System.out.println("This date has already past.");return null;} else { break;}
				case "past": if ( test.after(new Date()) ){ System.out.println("This date has yet to past.");return null;} else {break; }
				default: System.out.println("didn't do rule: "+cuRule); break;
			}
		}
		return value;
	}

	private static String checkIntegers(String value, String[] rules)
	{
		if (value == null || value.trim() == null) return null;
		value = value.trim();
		Pattern remover = Pattern.compile("\\[\\s_]\\", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		value = remover.matcher(value).replaceAll("");
		int test = 0;
		for( int i = 0; i< rules.length; i++) {
			String cuRule = rules[i];
			switch(cuRule){
				case "integer": try { test = Integer.parseInt(value); break; } catch (Exception e) { System.out.println("Invalid integer"); return null; }
				case "positive": if ( test <= 0 ) { System.out.println(value+" is not positive"); return null; } else { break; }
				case "negative": if ( test >= 0 ) { System.out.println(value+" is not negative"); return null; } else { break; }
				default: System.out.println("didn't do rule: "+cuRule); break;
			}
		}
		return ""+test;
	}

	private static String checkDoubles(String value, String[] rules)
	{
		if (value == null || value.trim() == null) return null;
		value = value.trim();
		Pattern remover = Pattern.compile("\\[\\s_]\\", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		value = remover.matcher(value).replaceAll("");
		double test = 0;
		for( int i = 0; i< rules.length; i++) {
			String cuRule = rules[i];
			switch(cuRule){
				case "double": try { test = Double.parseDouble(value); break; } catch (Exception e) { System.out.println("Invalid double"); return null; }
				case "positive": if ( test <= 0.0 ) { System.out.println(value+" is not positive"); return null; } else { break; }
				case "negative": if ( test >= 0 ) { System.out.println(value+" is not negative"); return null; } else { break; }
				default: System.out.println("didn't do rule: "+cuRule); break;
			}
		}
		return ""+test;
	}

}
