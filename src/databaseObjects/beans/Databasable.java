package databaseObjects.beans;
import java.util.concurrent.ConcurrentHashMap;

public interface Databasable {

	public void setTable(String name);
	public String getTable();

	public String getInsertStatement(ConcurrentHashMap<String, String> attributes);
	public String getUpdateStatement(String[][] idsToFieldsfinder, ConcurrentHashMap<String, String> attributes) throws Exception;
	public String getDeleteStatement(String[][] idsToFieldsfinder) throws Exception;
	public String getFindStatement(String[][] idsToFieldsfinder, String[] loadables, String[] toload) throws Exception;
	
}
