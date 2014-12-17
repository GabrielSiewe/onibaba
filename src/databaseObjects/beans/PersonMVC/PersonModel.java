package databaseObjects.beans.PersonMVC;
import BaseMVC.BasicModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class PersonModel extends BasicModel {
	
	private Integer person_id = -1;
	private String username = null;
	private String password = null;
	private String photo_path = null;
	private String first_name = null;
	private String last_name = null;
	private double height = 0.0;
	private double weight = 0.0;
	private String gender = null;
	private String ssn = null;
	private String allergies = null;
	private double salary;
	private String birthday = null;
	private String phone = null;
	private String email = null;
	private String title = null;
	private String secret_question = null;
	private String secret_answer = null;
	private Date date_joined = null;
	private Date last_updated = null;
	private String created_at = null;
	private String updated_at = null;
	protected String education;
	protected String experience;
	protected int doctor_id = -1;
	protected int nurse_id = -1;
	protected int patient_id = -1, secret_question_id = -1;
	
	// Specific to nurses and doctors
	protected String specialization = null;

	// Specific to a doctor
	protected String resume = null;
	
	// Specific to a patient
	protected String occupation = null;
	protected Boolean is_insured = null;
	protected String marital_status = null;
	protected String last_exam_date = null;


		
	// required properties 
	private static String[] requiredOnInsert = {"ssn", "first_name", "last_name", "email", "title", "phone", "birthday", "username", "password", "nurse_id", "doctor_id",
			"specialization", "resume", "occupation", "is_insured", "marital_status", "last_exam_date", "photo_path", "height", "weight",
			"secret_question", "secret_answer", "date_joined", "last_updated", "allergies"};
	protected static String[][] modelRules = { 
		{"ssn", "string"},
		{"first_name", "string"},
		{"first_name", "uppercase"},
		{"allergies", "string"},
		{"last_name", "string"},
		{"last_name", "uppercase"},
		{"experience", "string"},
		{"education", "string"},
		{"last_name", "uppercase"},
		{"email", "string"},
		{"email", "email"},
		{"gender", "string"},
		{"gender", "gender"},
		{"title", "string"},
		{"phone", "string"},
		{"phone", "phone" },
		{"phone", "positive" },
		{"birthday", "date"},
		{"birthday", "past" },
		{"updated_at", "date"},
		{"updated_at", "past" },
		{"created_at", "date"},
		{"created_at", "past" },
		{"id", "integer"},
		{"id", "positive" },
		{"salary", "double"},
		{"salary", "positive" },
		{"nurse_id", "integer"},
		{"nurse_id", "positive" },
		{"doctor_id", "integer"},
		{"doctor_id", "positive" },
		{"username", "string"},
		{"username", "nospace"},
		{"password", "string"},
		{"person_id", "integer"},
		{"person_id", "positive"},
		{"nurse_id", "integer"},
		{"nurse_id", "positive"},
		{"doctor_id", "integer"},
		{"doctor_id", "positive"},
		{"specialization", "string"},
		{"resume", "string"},
		{"is_insured", "string"},
		{"marital_status", "string"},
		{"last_exam_date", "date"},
		{"occupation", "string"}
	};
	private static final String TABLENAME = "persons";
	// fillable from the front end properties
	protected static String[] fillables = {"ssn", "first_name", "last_name", "email", "title", "phone", "birthday", "id", "username", "password",
			"person_id", "experience", "education", "doctor_id", "allergies", "gender", "salary","specialization", "marital_status",
			"is_insured","resume","last_exam_date","height","weight","created_at", "updated_at"};
	private static String[] hasMany = {"appointment","comment"};
	
	public PersonModel(int person_id) throws SQLException, Exception
	{
		super("person", person_id);
		ResultSet attributes = queryRunner.runQuery("SELECT * FROM persons WHERE `id`=\""+person_id+"\";");
		hasManyInstances = hasMany;
		person_id = attributes.getInt("id");
		ssn = attributes.getString("ssn");
		first_name = attributes.getString("first_name");
		last_name = attributes.getString("last_name");
		birthday = viewFormatter.format(new Date(new java.sql.Date(attributes.getDate("birthday").getTime()).getTime()));
		phone = attributes.getString("phone");
		email = attributes.getString("email");
		title = attributes.getString("title");
		username =  attributes.getString("username");
		password =  attributes.getString("password");
		gender = attributes.getString("gender");
		salary = attributes.getDouble("salary");
	}
	
	public PersonModel(ResultSet attributes, String model) throws SQLException, ParseException
	{
		super(model, attributes.getInt(1));
		if (model.equals("doctor") || model.equals("nurse") || model.equals("patient")) {
			
			if (model.equals("doctor")) {
				
				doctor_id = attributes.getInt(1);
				person_id = attributes.getInt("person_id");
				resume = attributes.getString("resume");
				education = attributes.getString("education");
				specialization = attributes.getString("specialization");
				experience = attributes.getString("experience");
			} else if (model.equals("nurse")) {
				nurse_id =  attributes.getInt(1);
				person_id = attributes.getInt("person_id");
				doctor_id =  attributes.getInt("doctor_id");
				education = attributes.getString("education");
				specialization = attributes.getString("specialization");
				experience = attributes.getString("experience");
			} else {
				patient_id =  attributes.getInt(1);
				person_id = attributes.getInt("person_id");
				nurse_id =  attributes.getInt("nurse_id");
				occupation = attributes.getString("occupation");
				is_insured = attributes.getBoolean("is_insured");
				marital_status = attributes.getString("marital_status");
				last_exam_date = viewFormatter.format(new Date(new java.sql.Date(attributes.getDate("last_exam_date").getTime()).getTime()));
			}

		} else {
			throw new SQLException("Invalid model name");
		}

		hasManyInstances = hasMany;
		username =  attributes.getString("username");
		password =  attributes.getString("password");
		photo_path =  attributes.getString("photo_path");
		last_name = attributes.getString("last_name");
		first_name = attributes.getString("first_name");
		height = attributes.getDouble("height");
		weight = attributes.getDouble("weight");
		gender = attributes.getString("gender");
		salary = attributes.getDouble("salary");
		email = attributes.getString("email");
		ssn = attributes.getString("ssn");
		allergies = attributes.getString("allergies");
		System.out.println(attributes.getString("birthday"));
		birthday = dateFormatter.format(sqlFormatter.parse(attributes.getString("birthday")));
		phone = attributes.getString("phone");
		secret_question_id = attributes.getInt("secret_question_id");
		title = attributes.getString("title");
		System.out.println("here id: "+modelId);
//		created_at = viewFormatter.format(new Date(new java.sql.Date(attributes.getDate("created_at").getTime()).getTime()));
//		updated_at = viewFormatter.format(new Date(new java.sql.Date(attributes.getDate("updated_at").getTime()).getTime()));
	}
	
	


	/**
	 * @return the person_id
	 */
	public Integer getPerson_id() {
		return person_id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the photo_path
	 */
	public String getPhoto_path() {
		return photo_path;
	}

	/**
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}

	/**
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @return the allergies
	 */
	public String getAllergies() {
		return allergies;
	}

	/**
	 * @return the salary
	 */
	public double getSalary() {
		return salary;
	}

	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the secret_question
	 */
	public String getSecret_question() {
		return secret_question;
	}

	/**
	 * @return the secret_answer
	 */
	public String getSecret_answer() {
		return secret_answer;
	}

	/**
	 * @return the date_joined
	 */
	public Date getDate_joined() {
		return date_joined;
	}

	/**
	 * @return the last_updated
	 */
	public Date getLast_updated() {
		return last_updated;
	}

	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}

	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
		return updated_at;
	}

	/**
	 * @return the education
	 */
	public String getEducation() {
		return education;
	}

	/**
	 * @return the experience
	 */
	public String getExperience() {
		return experience;
	}

	/**
	 * @return the doctor_id
	 */
	public int getDoctor_id() {
		return doctor_id;
	}

	/**
	 * @return the nurse_id
	 */
	public int getNurse_id() {
		return nurse_id;
	}

	/**
	 * @return the patient_id
	 */
	public int getPatient_id() {
		return patient_id;
	}

	/**
	 * @return the secret_question_id
	 */
	public int getSecret_question_id() {
		return secret_question_id;
	}

	/**
	 * @return the specialization
	 */
	public String getSpecialization() {
		return specialization;
	}

	/**
	 * @return the resume
	 */
	public String getResume() {
		return resume;
	}

	/**
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * @return the is_insured
	 */
	public Boolean getIs_insured() {
		return is_insured;
	}

	/**
	 * @return the marital_status
	 */
	public String getMarital_status() {
		return marital_status;
	}

	/**
	 * @return the last_exam_date
	 */
	public String getLast_exam_date() {
		return last_exam_date;
	}

	/**
	 * @return the requiredOnInsert
	 */
	public static String[] getRequiredOnInsert() {
		return requiredOnInsert;
	}

	/**
	 * @return the modelRules
	 */
	public static String[][] getModelRules() {
		return modelRules;
	}

	/**
	 * @return the tablename
	 */
	public static String getTablename() {
		return TABLENAME;
	}

	/**
	 * @return the fillables
	 */
	public static String[] getFillables() {
		return fillables;
	}

	/**
	 * @return the hasMany
	 */
	public static String[] getHasMany() {
		return hasMany;
	}

	public ResultSet appointments() throws SQLException
	{
		return queryRunner.runQuery("SELECT * FROM appointments WHERE `person_id`="+person_id);
	}
	public ResultSet comments() throws SQLException
	{
		ConcurrentHashMap<String, String> temp = new ConcurrentHashMap<String, String>();
		temp.put("person_id", ""+person_id);
		return specials(temp, "comments");
	}
	public ResultSet reminders() throws SQLException
	{
		ConcurrentHashMap<String, String> temp = new ConcurrentHashMap<String, String>();
		temp.put("person_id", ""+person_id);
		temp.put("object_model", "person");
		temp.put("object_id", ""+person_id);
		return specials(temp, "comments");
	}
	
	// model queries.
	public static String getInsertStatement(ConcurrentHashMap<String,String> attributes) throws Exception
	{
		attributes = validateData(attributes, "insert");
		if ( attributes == null) {
			System.out.println("Your input are missing some of the required fields for inserts.");
			return null;
		}
		return getInsertStatement(attributes, TABLENAME);
	}

	public static String getDeleteStatement(ConcurrentHashMap<String,String> finders) throws Exception
	{
		finders = validateData(finders, null);
		if (finders == null) {
			System.out.println("None of the inputed data were valid. Please try again.");
			return null;
		}
		return getDeleteStatement(finders, TABLENAME);
	}

	public static String getFindStatement(ConcurrentHashMap<String,String> finders) throws Exception
	{
		finders = validateData(finders, null);
		if (finders == null) {
			System.out.println("None of the inputed data were valid. Please try again.");
			return null;
		}
		return getFindStatement(finders, TABLENAME);
	}

	public static String getUpdateStatement(ConcurrentHashMap<String, String> finders, ConcurrentHashMap<String, String> attributes) throws Exception
	{
		attributes = validateData(attributes, null);

		if (attributes == null) {
			System.out.println("None of the inputed data for your attributes were valid. Please try again.");
			return null;
		}
		finders = validateData(finders, null);
		if (finders == null) {
			System.out.println("None of the inputed data for your finders were valid. Please try again.");
			return null;
		}
		return getUpdateStatement(finders, attributes, TABLENAME);
	}
	
	
	// Validating the data.
	// removes unfillable fields and watches out for sql data.
	protected static ConcurrentHashMap<String, String> validateData(ConcurrentHashMap<String, String> attributes, String methodName) throws Exception
	{
		String[] keys = attributes.keySet().toArray(new String[attributes.size()]);
		ConcurrentHashMap<String, ArrayList<String>> fieldRules = new ConcurrentHashMap<String, ArrayList<String>>(); 
		
		// then we it could be anything except an insert statement. In which case all we have to do is make sure the attributes are fillable
		if (methodName == null) {

			for( int i = 0; i< keys.length; i++)
			{		
				// if it contains and the value isn't null we keep it
				if (Arrays.asList(fillables).contains(keys[i]))  {

					if (fieldRules.get(keys[i]) == null) {
						fieldRules.put(keys[i], new ArrayList<String>());
					}
					// if it is part of the fillable arrays then it has a set of rules that apply to it.
					for( int k = 0; k < modelRules.length; k++) {

						if (modelRules[k][0].equals(keys[i])) {
							fieldRules.get(keys[i]).add( modelRules[k][1]);
						}
					}

					String value = evaluateFieldRule(attributes.get(keys[i]), fieldRules.get(keys[i]).toArray(new String[fieldRules.get(keys[i]).size()]));
					if ( value == null && Arrays.asList(requiredOnInsert).contains(keys[i])) {
							attributes.remove(keys[i]);
							throw new Exception(keys[i]+ " is required. Please enter a value.");
						} else {
						attributes.replace(keys[i], value);
					}
					continue;
				}
				// else we have ambiguous field names, and we must remove them.
				attributes.remove(keys[i]);
			}
		}
		return attributes;
	}

	public String toString()
	{
		if (title == "patient")
		{
			return first_name+" "+last_name;
		}
		return username;
	}
}

