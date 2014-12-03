package DatabaseCacheManipulator;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.util.LRUCache;

public class DatabaseManipulator extends Database {
	// Communicates with the database  and keeps updating the cache so that we don;t have to alo
	private static LRUCache cache;

	public  DatabaseManipulator()
	{
		super();
		if (cache == null) {
			cache = new LRUCache(100);
		}
	}
	
	public synchronized ResultSet runQuery(String statement) throws SQLException
	{
		ResultSet results = null;
		if (statement == null) {
			System.out.println("Invalid null statement");
			return null;
		}
		

//		if (cache.containsKey(statement)) {
//			results = (ResultSet) cache.get(statement);
//
//		} else {
			results = super.runQuery(statement);

			synchronized(cache) {
				if (!cache.containsKey(statement)) {
					cache.put(statement, results);
				} else {
					cache.replace(statement, results);
				}
//			}
		}

		return results;
	}
	
	public synchronized void updateQuery(String statement) throws SQLException
	{
		
		if (statement == null) {
			System.out.println("Invalid null statement");
			return;
		}
		super.updateQuery(statement);

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
