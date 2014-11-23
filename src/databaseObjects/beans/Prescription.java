package databaseObjects.beans;
import java.sql.ResultSet;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.sql.SQLException;

import databaseObjects.beans.CommentMVC.Comment;
import databaseObjects.beans.Person.Children.Doctor;
import databaseObjects.beans.Person.Children.Patient;

public class Prescription {
	private static DatabaseManipulator querySelector = null;
	private static final String TABLENAME = "prescriptions";
	private Doctor doctor = null;
	private Patient patient = null;
	private Invoice invoice = null;
	private Comment[] comments = null;
	private String disease = null;
	private Date created = null;
	private static final String[] loadables = {"doctor", "patient", "invoice", "comments"};
	


	public Prescription (ResultSet results) throws Exception
	{
		if (querySelector == null)
		{
			throw new Exception("before creating a prescription, you need to set its access to the database.");
		}
		if (results != null) {
			results.first();
			
			setLoadables("id", results.getString("doctor_id"), "doctor");
			setLoadables("id", results.getString("patient_id"), "patient");
			setLoadables("id", results.getString("invoice_id"), "invoice");
			created = results.getDate("created_at");
			disease = results.getString("details");
		}
	}
	public void setLoadables(String field, String id, String model) throws SQLException, Exception
	{
		String[][] identifier = new String[1][1];
		identifier[0][0] = field;
		identifier[0][1] = id;
		switch(model)
		{
			case "patient":
				Patient.setDBManipulator(querySelector);
				patient = new Patient(querySelector.runQuery(Patient.getStatement("find", identifier, null, null)));
				break;
			case "doctor":
				Doctor.setDBManipulator(querySelector);
				doctor = new Doctor(querySelector.runQuery(Doctor.getStatement("find", identifier, null, null)));
				break;
			case "invoice":
				Invoice.setDBManipulator(querySelector);
				invoice = new Invoice(Invoice.getStatement("find", identifier, null), querySelector.runQuery(Invoice.getStatement("find", identifier, null)));
			default:
				return;
		}
	}
	public Doctor getDoctor()
	{
		return doctor;
	}
	public Patient getPatient()
	{
		return patient;
	}
	public Invoice getInvoice() 
	{
		return invoice;
	}
	
	public Comment[] getComments()
	{
		return comments;
	}
	public static void setDBManipulator(DatabaseManipulator other)
	{
		querySelector = other;
	}

	public String toString()
	{
		String toReturn = "This prescription was created on "+created+" by Dr. "+doctor.getFirst_name()+" "+doctor.getLast_name()+" in order to heal"
				+" "+patient.getFirst_name()+" "+patient.getLast_name()+" from "+ disease+"\n.For more infos, please read the comments below.";
		return toReturn;
	}
	public static String getStatement(String method, String[][] fieldsToIds, ConcurrentHashMap<String, String> attributes, String[] loads) throws Exception
	{
		if (querySelector == null ) {
			throw new Exception("A database was not set for this model");
		}
		

		querySelector.setTable(TABLENAME);

		switch(method)
		{
			case "update":
				return querySelector.getUpdateStatement(fieldsToIds, attributes);
			case "add":
				return querySelector.getInsertStatement(attributes);
			case "remove":
				return querySelector.getDeleteStatement(fieldsToIds);
			default:
				return querySelector.getFindStatement(fieldsToIds, loadables, loads);
		}
	}
}
