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
	private static final String USERNAME = "Onibaba";
	private static final String PASSWORD = "DearDarling";
	private PreparedStatement query = null;

	private static final String jdbcDriver = "jdbc:mysql://131.96.144.242/SoftwareEngineeringDB";
	private Connection connector = null;
	

	protected Database() {
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
			connector = DriverManager.getConnection(jdbcDriver, USERNAME, PASSWORD);
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
