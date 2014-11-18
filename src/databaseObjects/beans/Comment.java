package databaseObjects.beans;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Comment {
	private final String TABLENAME = "comments";
	private int comment_id, person_id = -1, object_id = -1;
	private String object_model, message = null, query = null;

	public Comment(String query, ResultSet attributes) throws SQLException 
	{
		this.query = query;
		comment_id = attributes.getInt("id");
		person_id = attributes.getInt("person_id");
		object_id = attributes.getInt("object_id");
		object_model =  attributes.getString("object_model");
		message =  attributes.getString("message");
	}

	protected int getComment_id() {
		return comment_id;
	}

	protected int getPerson_id() {
		return person_id;
	}

	protected int getObject_id() {
		return object_id;
	}

	protected void setObject_id(int object_id) {
		this.object_id = object_id;
	}

	protected String getObject_model() {
		return object_model;
	}

	protected void setObject_model(String object_model) {
		this.object_model = object_model;
	}

	protected String getMessage() {
		return message;
	}

	protected void setMessage(String message) {
		this.message = message;
	}

	protected String getQuery() {
		return query;
	}

}
