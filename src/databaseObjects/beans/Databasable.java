package databaseObjects.beans;
import java.sql.ResultSet;

public interface Databasable {

	public void updateCache(String key, ResultSet newResult );
	public void removeCache(String key);
	
	
}
