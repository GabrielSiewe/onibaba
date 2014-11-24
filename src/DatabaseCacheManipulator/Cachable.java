package DatabaseCacheManipulator;
import java.sql.ResultSet;

public interface Cachable {

	public void updateCache(String key, ResultSet newResult );
	public void removeCache(String key);
	
	
}

