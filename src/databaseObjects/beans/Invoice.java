package databaseObjects.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Invoice {
	private String query;
	private int invoice_id;
	private double total_cost;

	public Invoice(String query, ResultSet results) throws SQLException
	{
		this.query = query;
		invoice_id = results.getInt("id");
		total_cost = results.getDouble("total_cost");
	}

	public static void setDBManipulator(DatabaseManipulator querySelector) {
		// TODO Auto-generated method stub
		
	}

	public static String getStatement(String string, String[][] identifier,
			Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getQuery() {
		return query;
	}

	protected int getInvoice_id() {
		return invoice_id;
	}

	protected double getTotal_cost() {
		return total_cost;
	}

	protected void setTotal_cost(double total_cost) {
		this.total_cost = total_cost;
	}

}
