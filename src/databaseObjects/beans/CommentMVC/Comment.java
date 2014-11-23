package databaseObjects.beans.CommentMVC;
import BaseMVC.BasicModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class Comment extends BasicModel {

	// Data validation.
	protected ConcurrentHashMap<String, String[]> rules;
	private final static String TABLENAME = "comments";
	private int comment_id, person_id = -1, object_id = -1;
	private String object_model, message = null, query = null;

	public Comment(String query, ResultSet attributes) throws SQLException 
	{
		super("comment", attributes.getInt("id"));
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
	
	// model queries.
	public static String getInsertStatement(ConcurrentHashMap<String,String> attributes)
	{
		return getInsertStatement(attributes, TABLENAME);
	}

	public static String getDeleteStatement(ConcurrentHashMap<String,String> finders)
	{
		return getDeleteStatement(finders, TABLENAME);
	}

	public static String getFindStatement(ConcurrentHashMap<String,String> finders)
	{
			return getFindStatement(finders, TABLENAME);
	}

	public static String getUpdateStatement(ConcurrentHashMap<String, String> finders, ConcurrentHashMap<String, String> attributes)
	{
		return getUpdateStatement(finders, attributes, TABLENAME);
	}


}
