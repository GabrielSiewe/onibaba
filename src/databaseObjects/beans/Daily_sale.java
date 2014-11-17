package databaseObjects.beans;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Daily_sale {
	private String query;
	private int daily_sale_id = -1;
	private double amount = 0.0;
	
	public Daily_sale(String query, ResultSet results) throws SQLException
	{
		this.query = query;
		daily_sale_id = results.getInt("id");
		amount = results.getDouble("amount");
	}

	protected String getQuery() {
		return query;
	}

	protected int getDaily_sale_id() {
		return daily_sale_id;
	}

	protected double getAmount() {
		return amount;
	}

	protected void setAmount(double amount) {
		this.amount = amount;
	}
}
