package databaseObjects.beans;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.util.LRUCache;

public class DatabaseManipulator extends Database implements Databasable {
	
	private String table_name = null;
	private String insertStatement = null;
	private String deleteStatement = null;
	private String updateStatement = null;
	private String findStatement = null;
	private static LRUCache cache;
	private final static String[] requiredOnUpdateAndDelete = {"id"};

	public  DatabaseManipulator(String table_name, String username, String password)
	{
		super(username, password);
		this.table_name = table_name;
		if (cache == null) {
			cache = new LRUCache(100);
		}
	}

	public DatabaseManipulator(String username, String password)
	{
		super(username, password);
	}

	public void setTable(String name)
	{
		table_name = name;
		insertStatement = null;
		deleteStatement = null;
		updateStatement = null;
		findStatement 	= null;
	}

	public String getTable()
	{
		String name = null;
		if(hasTable())
		{
			name = table_name;
		}
		return name;
	}
	
	public ResultSet runQuery(String statement, Boolean ignoreCache) throws SQLException
	{
		ResultSet results = null;

		if (cache.containsKey(statement) && ignoreCache == false) {
			results = (ResultSet) cache.get(statement);

		} else {
			results = super.runQuery(statement);

			synchronized(cache) {
				if (!cache.containsKey(statement)) {
					cache.put(statement, results);
				} else {
					cache.replace(statement, results);
				}
			}
		}

		return results;
	}
	
	public synchronized void updateCache(String key, ResultSet newResult )
	{
		synchronized(cache) {
			if (cache.containsKey(key)) cache.replace(key, cache.get(key), newResult);
		}
	}
	
	public synchronized void removeCache(String key)
	{
		synchronized(cache) {
			if (cache.containsKey(key)) cache.remove(key);
		}
	}

	private boolean hasTable()
	{
		boolean hasTable = true;
		if (table_name == null)
		{
			insertStatement = deleteStatement = updateStatement = findStatement = "no table name";
			hasTable = false;
		}
		return hasTable;
	}

	public String getInsertStatement(ConcurrentHashMap<String,String> attributes)
	{
		if (hasTable() && insertStatement == null)
		{
			insertStatement = "INSERT INTO "+table_name+" ( "+printFields(getkeysArray(attributes.keySet())) + " )"
					+ " VALUES ( "+printValues(getkeysArray(attributes.values()))+" );";
		}
		return insertStatement;
	}

	public String getDeleteStatement(String[][] idsToFieldsfinder) throws Exception
	{
		if (hasTable() && deleteStatement == null)
		{
			deleteStatement = "DELETE FROM "+ table_name+" WHERE "+matchFieldToValue(idsToFieldsfinder, null, null);
		}
		return deleteStatement;
	}

	public String getFindStatement(String[][] idsToFieldsfinder, String[] loadables, String[] toload) throws Exception
	{
		if (hasTable() && findStatement == null)
		{
			findStatement = "SELECT * FROM "+table_name+" "+matchFieldToValue(idsToFieldsfinder, loadables, toload);
		}
		return findStatement;
	}

	public String getUpdateStatement(String[][] idsToFieldsfinder, ConcurrentHashMap<String, String> attributes) throws Exception
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
			valuesToField = "UPDATE "+ table_name + " SET " + valuesToField + " WHERE "+ find + ";";
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
			toReturn +="WHERE "+ table_name+".`"+ all[i][0]+"`" + "=\"" + all[i][1]+"\""+ ((i == all.length - 1)?"":", ");
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
				toReturn += " INNER JOIN "+ loadable+"s ON "+table_name+".`"+loadable+"_id`="+loadable+"s.`id` ";
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

	public void close()
	{
		super.closeConnection();
	}

}
