package databaseObjects.beans;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.util.LRUCache;

public class DatabaseManipulator extends Database implements Databasable {
	// Communicates with the database  and keeps updating the cache so that we don;t have to alo
	private static LRUCache cache;

	public  DatabaseManipulator(String username, String password)
	{
		super(username, password);
		if (cache == null) {
			cache = new LRUCache(100);
		}
	}
	
	public synchronized ResultSet runQuery(String statement, Boolean ignoreCache) throws SQLException
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

	public void close()
	{
		super.closeConnection();
	}

}
