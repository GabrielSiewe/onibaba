package databaseObjects.beans;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Inventory {
	private String query;
	private int inventory_id = -1; 
	private int medecine_id = -1;
	private int quantity = 0;
	
	public Inventory(String query, ResultSet results) throws SQLException
	{
		this.query = query;
		inventory_id = results.getInt("id");
		quantity = results.getInt("amount");
	}

	protected String getQuery() {
		return query;
	}

	protected int getInventory_id() {
		return inventory_id;
	}

	protected int getMedecine_id() {
		return medecine_id;
	}

	protected void setMedecine_id(int medecine_id) {
		this.medecine_id = medecine_id;
	}

	protected int getQuantity() {
		return quantity;
	}

	protected void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
