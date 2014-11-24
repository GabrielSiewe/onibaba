package DatabaseCacheManipulator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * This class will serve as the manager of the database. It will be our persistent database.
 */
public class Database {

	// First let's go ahead and set the database information required
	private String username = null;
	private String password = null;
	private PreparedStatement query = null;

	private static final String jdbcDriver = "jdbc:mysql://10.250.4.174/SoftwareEngineeringDB";
	private Connection connector = null;
	

	protected Database(String username, String password) {
		this.username = username;
		this.password = password;
		setConnection();
	}
	
	protected PreparedStatement getPreparedStatement()
	{
		return query;
	}
	
	protected ResultSet runQuery(String statement) throws SQLException
	{
		ResultSet results = null;
		query.clearParameters();
		if (statement != null) {
			results = query.executeQuery(statement);
			results.first();
		}
		return results;
	}

	private void setConnection()
	{
		try{
			connector = DriverManager.getConnection(jdbcDriver, username, password);
			query = connector.prepareStatement("?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			System.out.println("The database connection failed.");
			closeConnection();
		}
	}

	protected void closeConnection()
	{
		try{
			if (query != null || connector != null)
			{
				if (query != null) 
				{
					query.close();
				}
				connector.close();
			}
			
		} catch (SQLException e) {
			System.out.println("Cannot connect to network");
			return;
		}
		
	}
}
