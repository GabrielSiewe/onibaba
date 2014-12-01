package BaseMVC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import DatabaseCacheManipulator.*;

public class BasicModel {
	
	protected static DatabaseManipulator queryRunner = null;

	protected String modelName;
	protected static String lastRanQuery;
	protected int modelId;
	
	protected String[] hasManyInstances;
	protected String[] belongsToManyInstances;
	protected String[] belongsToInstance;
	
	public BasicModel(String modelName, int modelId)
	{
		if (queryRunner == null) {
			queryRunner = new DatabaseManipulator();
		}
		this.modelName = modelName;
		this.modelId = modelId;
	}
	
	
	// Querying the models returns the records that this model may have one of.
	protected ResultSet belongsTo(String model) throws SQLException
	{
		if (model != null && belongsToInstance.toString().indexOf(model) != -1 && queryRunner != null) {
			return queryRunner.runQuery(load(model, null));
		}
		throw new SQLException("this "+modelName+" doesn't belong to a "+model);
	}

	// Returns the records that this model may have many off.
	protected ResultSet hasMany(String model) throws SQLException
	{
		if (model != null && Arrays.asList(hasManyInstances).contains(model) && queryRunner != null) {
			
			return queryRunner.runQuery(load(model, null));
		}
		throw new SQLException("this "+modelName+" doesn't have many "+model);
	};

	// Returns the records that may have been for this model.
	protected ResultSet belongsToMany(String model, String tableName) throws SQLException
	{
		if (tableName != null && model != null && Arrays.asList(belongsToManyInstances).contains(model) && queryRunner != null) {
			return queryRunner.runQuery(load(model, tableName));
		}
		throw new SQLException("this "+modelName+" doesn't belong to nor does it have many "+model);
		
	}
	
	// runs an SQL query
	public static ResultSet runQuery(String query) throws SQLException
	{
		if (queryRunner == null) {
			queryRunner = new DatabaseManipulator();
		}

		if (query != null && query.trim() != null) {
			return queryRunner.runQuery(query);
		}
		throw new SQLException("you are trying to run an empty query.");
		
	}
	
	// Returns the records that may have been for this model.
	protected ResultSet specials(ConcurrentHashMap<String,String> finders, String table_name ) throws SQLException
	{
		if (table_name != null && finders != null && queryRunner != null) {
			return queryRunner.runQuery(getFindStatement(finders, table_name));
		}
		throw new SQLException("this "+modelName+" doesn't belong to nor does it have many "+table_name);
		
	}

	// Makes sure to load all the models that are part of this array of models.
	protected String load(String model, String manyToManyTable)
	{
		String query = "";

		if (manyToManyTable != null)
			query = "SELECT * FROM "+manyToManyTable+" WHERE "+modelName+"_id = "+modelId;
		else
			query = "SELECT * FROM "+model+"s WHERE "+modelName+"_id = "+modelId;

		lastRanQuery = query;
		return query;
	}
	

	// querying the model.
	protected static String getInsertStatement(ConcurrentHashMap<String,String> attributes, String table_name)
	{
		String toReturn = null;
		if (attributes != null && attributes.size() != 0) {
			toReturn = "INSERT INTO "+table_name+" ( "+printFields(getkeysArray(attributes.keySet())) + " )"
					+ " VALUES ( "+printValues(getkeysArray(attributes.values()))+" );";
		}
		return toReturn;
	}

	protected static String getDeleteStatement(ConcurrentHashMap<String,String> finders, String table_name)
	{
		String toReturn = null;
		if (finders != null && finders.size() != 0) {
			toReturn = "DELETE * FROM "+table_name+" WHERE ( "+matchFieldToValue(finders, table_name);
		}
		return toReturn;
	}

	protected static String getFindStatement(ConcurrentHashMap<String,String> finders, String table_name)
	{
		String findStatement = null;
		if (finders != null && finders.size() != 0) {
			findStatement = "SELECT * FROM "+table_name+" WHERE ("+matchFieldToValue(finders, table_name);
		}
		return findStatement;
	}

	protected static String getUpdateStatement(ConcurrentHashMap<String, String> finders, ConcurrentHashMap<String, String> attributes, String table_name)
	{
		String updateStatement = "";
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
			
			toReturn += "`"+ key+"`" + "=\"" +attributes.get(key)+"\""+ ((i == keySet.length - 1)?");":" AND ");
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
	
	// rule for string:{ {"name", {"string", "uppercase", "max:255"}} }
	protected static String evaluateFieldRule(String value, String[] rules)
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
		Pattern remover = Pattern.compile("[\\s]+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
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
		Pattern remover = Pattern.compile("[\\s]+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
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
		Pattern remover = Pattern.compile("[\\s]+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
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
		Pattern remover = Pattern.compile("[\\s]+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
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
	
	public static void closeDbConnection()
	{
		if (queryRunner != null)
			queryRunner.close();
	}

}
