package databaseObjects.beans;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class Desease {
	private Integer disease_id = -1;
	private String name;
	private String description;
	private String symptoms;
	public Desease(String query, ResultSet attributes) throws SQLException
	{
		disease_id = attributes.getInt("id");
		name = attributes.getString("name");
		description = attributes.getString("description");
		symptoms = attributes.getString("symptoms");
		
	}
	protected Integer getDisease_id() {
		return disease_id;
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
	protected String getSymptoms() {
		return symptoms;
	}
	protected void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

}
