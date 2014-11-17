package databaseObjects.beans;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Comment {
	private int comment_id = -1;
	private String name, description, query;
	private double cost = 0.0;        

	public Comment(String query, ResultSet attributes) throws SQLException 
	{
		this.query = query;
		name = attributes.getString("name");
		description = attributes.getString("description");
		cost = attributes.getDouble("cost");
	}
	
	public String getQuery()
	{
		return query;
	}
	protected int getComment_id() {
		return comment_id;
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected String getDescription() {
		return description;
	}

	protected void setDescription(String description) {
		this.description = description;
	}

	protected double getCost() {
		return cost;
	}

	protected void setCost(double cost) {
		this.cost = cost;
	}
}
