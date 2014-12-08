package BaseMVC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.text.SimpleDateFormat;

import DatabaseCacheManipulator.*;
/*
 * The class responsible for opening and closing the database connection as well as generating or performing queries,
 * including relationships and joins
 */
public class BasicModel {
	
	protected static DatabaseManipulator queryRunner = null;
	protected static DatabaseManipulator personFinder = null;
	protected static final SimpleDateFormat viewFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	protected static final SimpleDateFormat sqlFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected String modelName;
	protected static String lastRanQuery;
	protected int modelId;
	
	protected String[] hasManyInstances;
	protected String[] belongsToManyInstances;
	protected String[] belongsToInstance;
	
	// sets the current model name and id.
	public BasicModel(String modelName, int modelId)
	{
		if (queryRunner == null) {
			queryRunner = new DatabaseManipulator();
		}
		this.modelName = modelName;
		this.modelId = modelId;
	}
	
	
	// Querying the models returns the records that this model may belong to.
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
	// runs an SQL query with a resultset return AKA Select statements.
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
	
	// runs an SQL query and returns nothing but the number of rows affected AKA Update, Insert, Deletes
	public static void updateQuery(String query) throws SQLException
	{
		if (queryRunner == null) {
			queryRunner = new DatabaseManipulator();
		}
		
		if (query != null && query.trim() != null) {
			queryRunner.updateQuery(query);
			return;
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

		return query;
	}
	

	// returns an 
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
			toReturn = "DELETE FROM "+table_name+" WHERE ("+matchFieldToValue(finders, table_name);
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
				updateStatement += "`"+ keySet[i]+"`" + "=\"" + attributes.get(keySet[i])+"\""+((i == keySet.length - 1)?"":",");
			}
			updateStatement = "UPDATE "+ table_name + " SET " + updateStatement + " WHERE ("+ find;
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
	protected static String evaluateFieldRule(String value, String[] rules) throws Exception
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
	private static String checkStrings(String value, String[] rules) throws Exception
	{
		if (value == null || value.trim() == null) return null;
		value = value.trim();
		Pattern remover = Pattern.compile("[\\s]+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		value = remover.matcher(value).replaceAll(" ");

		if (value.length() >= 256) {
			throw new Exception("no more than 255 character are allowed.");
		}

		for( int i = 0; i< rules.length; i++) {
			String cuRule = rules[i];
			switch(cuRule) {
				case "string": break;
				case "uppercase": value = value.toUpperCase(); break;
				case "nospace": if (value.indexOf(" ") != -1) throw new Exception("no spaces allowed in "+value);; break;
				case "lowercase": value = value.toLowerCase(); break;
				case "email": boolean temp = remover.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL).matcher(value).find(); if (temp == false) throw new Exception("invalid email: "+value); break;
				case "phone": value = value.replaceAll("-", ""); try { value = Integer.parseInt(value)+""; break; } catch(Exception e) { throw new Exception("Invalid phone number: "+value);}
				case "gender": if (value.equalsIgnoreCase("male") || value.equalsIgnoreCase("female")) break; else throw new Exception("Invalid gender chosen: "+value);
				default: System.out.println("didn't do rule: "+cuRule); break;
			}
		}
		return value;
	}

	// rule for date:{ {"appointment_date", {"date", "future", "past"}} }
	private static String checkDates(String value, String[] rules) throws Exception
	{
		if (value == null || value.trim() == null) return null;
		value = value.trim();
		viewFormatter.setLenient(false);
		Date test = null;
		try {
			test = viewFormatter.parse(value);
			value = sqlFormatter.format(test);
		} catch(Exception e) {
			throw new Exception("Enter a valid date in the format: mm/dd/yyyy hh:mm:ss");
		}
		
		for( int i = 0; i< rules.length; i++) {
			String cuRule = rules[i];
			switch(cuRule){
				case "date": try { value = sqlFormatter.format(test);break;} catch (Exception e) { throw new Exception("Invalid date.");}
				case "future": if ( test.before(new Date()) ){throw new Exception(test.toString()+" has already past.");} else { break;}
				case "past": if ( test.after(new Date()) ){ throw new Exception(test.toString()+" has yet to past.");} else {break; }
				default: System.out.println("didn't do rule: "+cuRule); break;
			}
		}
		System.out.println(value);
		return value;
	}

	private static String checkIntegers(String value, String[] rules) throws Exception
	{
		if (value == null || value.trim() == null) return null;
		value = value.trim();
		int test = 0;
		for( int i = 0; i< rules.length; i++) {
			String cuRule = rules[i];
			switch(cuRule){
				case "integer": try { test = Integer.parseInt(value); break; } catch (Exception e) { throw new Exception("Invalid integer: "+ value); }
				case "positive": if ( test <= 0 ) { throw new Exception(value+" is not positive");} else { break; }
				case "negative": if ( test >= 0 ) { throw new Exception(value+" is not negative"); } else { break; }
				default: System.out.println("didn't do rule: "+cuRule); break;
			}
		}
		return ""+test;
	}

	private static String checkDoubles(String value, String[] rules) throws Exception
	{
		if (value == null || value.trim() == null) return null;
		value = value.trim();
		double test = 0;
		for( int i = 0; i< rules.length; i++) {
			String cuRule = rules[i];
			switch(cuRule){
				case "double": try { test = Double.parseDouble(value); break; } catch (Exception e) { throw new Exception("Invalid decimal: "+value); }
				case "positive": if ( test <= 0.0 ) { throw new Exception(value+" is not positive"); } else { break; }
				case "negative": if ( test >= 0 ) { throw new Exception(value+" is not negative");} else { break; }
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
