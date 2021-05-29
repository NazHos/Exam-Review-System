package main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.function.BooleanSupplier;

public class databaseManager {

	private final String SERVER = "jdbc:mysql://silva.computing.dundee.ac.uk:3306/18agileteam9db";
	private final String USER = "18agileteam9";
	private final String PASS = "6578.at9.8756";
	private final String DATABASE = "18agileteam9db";
	
	private Connection conn = null;
	private Connection con;
	
	public boolean connect()
	{
		debug.log("Attempting DB Connection");
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(SERVER, USER, PASS);
			
		} catch (ClassNotFoundException | SQLException e) {
			debug.error("DB connection failed");
			e.getMessage();
			return false;
		}
		
		debug.log("DB Connected");
		
		return true;
	}
	
	public boolean close()
	{
		debug.log("Closing DB connection");
		try {
			if(!conn.isClosed())
				conn.close();
			debug.log("Connection closed");
			return true;
		} catch (SQLException e) {
			debug.error("Closing DB failed");
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean update(String sql)
	{
		try {
			if(conn.isClosed())
				connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		} catch (SQLException e) {
			debug.error("Update failed: " + sql);
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public ResultSet query(String sql)
	{
		try {
			if(conn.isClosed())
				connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		debug.log("executing query: " + sql);
		ResultSet rs = null;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			debug.error("Query failed: " + sql);
			System.out.println(e.getMessage());
			return rs;
		}
	}
	
	public boolean addStaff(int id, String email, String password, String firstName, String surname, String role)
	{
		debug.log("DB: Adding new staff member");
		
		ResultSet rs = query("SELECT COUNT(*) FROM staff_account WHERE staff_id = " + id + ";");
		if(rs == null){
			debug.error("failed to add staff, SQL error retrieving staff id's");
			return false;
		}

		try {
			rs.first();
			int found = rs.getInt(1);
			if(found >= 1){
				debug.error("failed to add staff, id already exists");
				return false;
			}
			rs.close();
			rs = query("SELECT COUNT(*) FROM role WHERE role = '" + role + "';");
			if(rs == null){
				debug.error("failed to add staff, SQL error retrieving roles");
				return false;
			}
			
			rs.first();
			if(rs.getInt(1) <= 0){
				debug.error("failed to add staff, role does not exist");
				return false;
			}
			rs.close();
			
			String sqlStmt = "INSERT INTO staff_account (staff_id, email, first_name, last_name, password, role_id) "
					+ "VALUES ("+ id +", '"+ email +"', '"+ firstName +"', '"+ surname +"', MD5('"+ password +"') ,(SELECT role_id FROM role WHERE role = '"+ role +"'))";
			
			if(update(sqlStmt)) {
				debug.log("Staff member added");
				return true;
			}
			
			debug.error("failed to add staff using final stmt");
			return false;
			
		} catch (SQLException e) {
			debug.error("failed to add staff, SQLException");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean editStaff(int id, String email, String password, String firstName, String surname)
	{
		debug.log("DB: Editing staff member");
		
		ResultSet rs = query("SELECT COUNT(*) FROM staff_account WHERE staff_id = " + id + ";");
		if(rs == null){
			debug.error("failed to edit staff, SQL error retrieving staff id's");
			return false;
		}

		try {
			/*
			 * rs.first();
			 
			int found = rs.getInt(1);
			if(found >= 1){
				debug.error("failed to edit staff, id already exists");
				return false;
			}
			
			rs.close();
			rs = query("SELECT COUNT(*) FROM role WHERE role = '" + role + "';");
			if(rs == null){
				debug.error("failed to edit staff, SQL error retrieving roles");
				return false;
			}
			*/
			rs.first();
			if(rs.getInt(1) <= 0){
				debug.error("failed to edit staff, role does not exist");
				return false;
			}
			rs.close();
			/*
			String sqlStmt = "INSERT INTO staff_account (staff_id, email, first_name, last_name, password, role_id) "
					+ "VALUES ("+ id +", '"+ email +"', '"+ firstName +"', '"+ surname +"', MD5('"+ password +"') ,(SELECT role_id FROM role WHERE role = '"+ role +"'))";
			
			UPDATE staff_account
			SET staff_id=staff_id, first_name='Donald', last_name='Trump', email='lol',  role_id=1
			WHERE staff_id = 948302
			
			*/
			
			String sqlStmt = "UPDATE staff_account SET staff_id='" + id + "', first_name='" + firstName + "', last_name='" + surname + "', email='" + email + "' " + "WHERE staff_id ='" + id + "';";  
			if(update(sqlStmt)) {
				debug.log("Staff member updated");
				return true;
			}
			
			debug.error("failed to add staff using final stmt");
			return false;
			
		} catch (SQLException e) {
			debug.error("failed to add staff, SQLException");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removeStaff(String staff_email)
	{
		debug.log("Removing staff member; email: " + staff_email);
		ResultSet rs = query("SELECT staff_id FROM staff_account WHERE email = '"+ staff_email +"'");
		try {
		if(!rs.next())
		{
			debug.log("failed to remove staff, email not found");
			return false;
		}
		
		return removeStaff(rs.getInt(1));
		
		}catch(SQLException e)
		{
			debug.error("failed to remove staff, SQLException");
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean removeStaff(int staff_id)
	{
		debug.log("Removing staff member; id: " + staff_id);
		
		ResultSet rs = query("SELECT COUNT(*) FROM staff_account WHERE staff_id = " + staff_id + ";");
		if(rs == null){
			debug.error("failed to remove staff, SQL error retrieving staff id's");
			return false;
		}
		try {
			rs.first();
			if(rs.getInt(1) <= 0 ){
				debug.error("failed to remove staff, id not found");
				return false;
			}
			rs.close();
			
			String sqlStmt = "DELETE FROM staff_account WHERE staff_id = '"+ staff_id +"';";
			if(update(sqlStmt))
			{
				debug.log("Removed staff successfully");
				return true;
			}
			
			debug.error("failed to remove staff member during final update");
			return false;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	
	public boolean authenticate(int staff_id, String password)
	{
		debug.log("Authenticating id" + staff_id);
		ResultSet rs = query("SELECT staff_id FROM staff_account WHERE staff_id = '"+ staff_id + "' AND password = MD5('" + password + "')");
		try {
			if(!rs.next())
			{
				debug.log("Authentication failed");
				return false;
			}
			
			debug.log("User authenticated");
			return true;
			
		} catch (SQLException e) {
			debug.error("Staff authentication throwed SQLError");
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	public User getUser(int staff_id)
	{
		debug.log("Retrieving user details for id: " + staff_id);
		ResultSet rs = query("SELECT * FROM staff_account, role WHERE staff_id = " + staff_id + " AND role.role_id = staff_account.role_id");
		
		try {
			if(!rs.next())
			{
				debug.error("No user found by ID :" + staff_id);
				return null;
			}
			
			String srole = rs.getString("role");
			User.role role;
			switch(srole.toLowerCase())
			{
				case "teaching_staff":
					role = User.role.Teaching_Staff;
					break;
				case "external_moderator":
					role = User.role.External_Moderator;
					break;
				case "administrator":
					role = User.role.Administrator;
					break;
				case "vetting_committee":
					role = User.role.Vetting_Committee;
					break;
				default:
					role = User.role.Office_Staff;
					break;
					
			}
			
			User user = new User(staff_id, rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), role);
			return user;
			
		} catch (SQLException e) {
			debug.error("SQLException retrieving user info");
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
	public ArrayList<User> getAllUsers()
	{
		debug.log("Retrieving all users");
		ResultSet rs = query("SELECT staff_id FROM staff_account;");
		
		ArrayList<User> users = new ArrayList<User>();
		
		try {
			while(rs.next())
			{
				int id = rs.getInt("staff_id");
				users.add(getUser(id));
			}
		} catch (SQLException e) {
			debug.log("SQLError getAllUsers()");
			e.printStackTrace();
		}
		
		return users;
	}
	

	
	public String getRole(int staff_id)
	{
		debug.log("Retrieving user role for id " + staff_id);
		
		ResultSet rs = query("SELECT role FROM role WHERE role_id = (SELECT role_id FROM staff_account WHERE staff_id = '" + staff_id + "');");
		try {
			if(!rs.next())
			{
				debug.error("Staff ID not found");
				return null;
			}
			
			String role = rs.getString(1);
			debug.log("Received role " + role);
			return role;
			
		}catch(SQLException e)
		{
			debug.error("SQLException receiving staff role");
			e.printStackTrace();
			return null;
		}
		
	}	
	
	
	public ArrayList<Exam> getAllExams()
	{
		return getExams(-1, -1, -1, null);
	}
	
	public ArrayList<Exam> getExams(int year, int semester, int stage) {
		return getExams(year, semester, stage, null);
	}
	
	public ArrayList<Exam> getExams(int year, int semester, int stage, User user) {
		debug.log("Retrieving exams, building SQL string");
		
		String sql = "SELECT * FROM exam";
		int paramCount = 4;
		if(year == -1)
			paramCount--;
		if(semester == -1)
			paramCount--;
		if(stage == -1)
			paramCount--;
		if(user == null)
			paramCount--;
		
		if(paramCount > 0)
			sql += " WHERE ";
			
		if(year != -1) {
			sql += "academic_year = " + year;
			paramCount--;
			if(paramCount > 0)
				sql += " AND ";
		}
		if(semester != -1) {
			sql += "semester = " + semester;
			paramCount--;
			if(paramCount > 0)
				sql += " AND ";
		}
		if(stage != -1) {
			sql += "review_stage = " + stage;
			paramCount--;
			if(paramCount > 0)
				sql += " AND ";
		}
		if(user != null) {
			switch(user.getRole())
			{
			case Administrator:
				break;
			case Teaching_Staff:
				sql+= "( staff_id = " + user.getId() + " OR internal_moderator = " + user.getId() + ")";
				break;
			case Vetting_Committee:
				sql += "review_stage = " + Exam.Stage.VETTING_COMMITTEE.toString();
				break;
			case External_Moderator:
				sql += "external_moderator = " + user.getId();
				break;
			case Office_Staff:
				sql += "review_stage = " + Exam.stageToInt(Exam.Stage.COMPLETE);
				break;
			}
			paramCount--;
			if(paramCount > 0)
				sql += " AND ";
		}
			
		debug.log("SQL string built: " + sql);
		
		ArrayList<Exam> exams = new ArrayList<Exam>();
		
	    try {
        	ResultSet rs = query(sql);
        	int count = 0;
            while (rs.next()){
            	Exam e = new Exam();
            	e.setModuleName(rs.getString("module_name"));
            	e.setYear(rs.getInt("academic_year"));
            	e.setSemester(rs.getInt("semester"));
            	e.setStageInt(rs.getInt("review_stage"));
            	int exam_id = rs.getInt("exam_id");
            	e.setStaff(this.getUser(rs.getInt("staff_id")));
            	e.setInternalMod(this.getUser(rs.getInt("internal_moderator")));
            	e.setInternalMod(this.getUser(rs.getInt("external_moderator")));
            	e.setLastUpdate(rs.getString("last_stage_update"));
            	e.setExamID(rs.getInt("exam_id"));
            	exams.add(e);
            	count++;
//            	Date date_created = rs.getDate("date_created");
//          	exam = Integer.toString(exam_id)+module_name+date_created;
            }
            debug.log("Retrieved " + count + " exams" );
            return exams;
        }
        catch (SQLException s){
        	debug.error("SQL Error executing sql statement getExams()");
        	return exams;
        }
    }



	public boolean addNewExam(Exam exam) {
		
		debug.log("DB: Adding new exam");
		
			
		String sqlStmt = "INSERT INTO exam (date_created, module_name, academic_year, semester, review_stage, staff_id, internal_moderator, external_moderator, last_stage_update) "
				+ "VALUES (NOW(), '"+ exam.getModuleName() +"', '"+ exam.getYear() +"', '"+ exam.getSemester() +"', '"+ Exam.stageToInt(exam.getStage()) +"', '"+ exam.getStaff().getId() +"', '"+ exam.getInternalMod().getId() +"', '"+ exam.getExternalMod().getId() +"', CURDATE())";
		
		
		if(update(sqlStmt)) {
			debug.log("New Exam Entry Added");
			return true;
		}

		debug.error("failed to add new exam to DB using final stmt");
		return false;
	}
	
	/**
	 * Add/Update setting entry in database
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setSetting(String key, String value)
	{
		debug.log("DB: Adding setting to database");
		if(update("INSERT INTO settings (key_string, value) VALUES ('"+key+"', '"+value+"');"))
		{
			debug.log("DB: Settings entry added to database");
			return true;
		}
		
		debug.log("Setting already in database, attempting update");
		if(update("UPDATE settings SET value = '"+value+"' WHERE key_string = '"+key+"';"))
		{
			debug.log("DB: Settings entry updated in database successfully");
			return true;
		}
		debug.error("DB: An error occoured adding/updating setting entry in database");
		return false;
	}
	
	/**
	 * Retrieve setting from database
	 * @param key
	 * @return
	 */
	public String getSetting(String key)
	{
		debug.log("Retrieving key " + key + " from database");
		
		ResultSet rs = query("SELECT value FROM settings WHERE key_string = '"+key+"'");
		
		try {
			if(!rs.next())
			{
				debug.error("DB: No entry for settings key found in database");
				return null;	
			}
			debug.log("Retrieved value for key from database");
			return rs.getString(1);
		} catch (SQLException e) {
			debug.error("SQLException occoured during value retrieval from settings table");
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public boolean uploadComment(String text, Exam exam, User sender)
	{
		String sql = "INSERT INTO messages (exam_id, sender_id, recipient_id, message, date) "
				+ "VALUES ( "+exam.getExamID()+", "+sender.getId()+", 999999, '"+text+"', + NOW())";
		return update(sql);
	}
	
	public ArrayList<String> getComments(int examID)
	{
		String sql = "SELECT message, sender_id FROM messages WHERE exam_id = " + examID + " ORDER BY date";
		
		ResultSet rs = query(sql);
		ArrayList<String> comments = new ArrayList<String>();
		try {
			while(rs.next())
			{
				User usr = getUser(rs.getInt("sender_id"));
				String comment = usr.getName() + "> " + rs.getString("message") + "\n";
				comments.add(comment);
			}
		} catch (SQLException e) {
			debug.error("DB: Failed to retrieve comments");
			e.printStackTrace();
			return null;
		}
		return comments;
	}
	public boolean uploadExam(String path, String name, int resit, int solution)
	{
		String sql = "INSERT INTO exam_document (document_id, exam_id, staff_id, document, document_name, date, resit, solution) "
	    + "VALUES (0, 13, 2, ?, ?, NOW(), ?, ?)";
	    
		try {
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, path);
			stmt.setString(2, name);
			stmt.setInt(3, resit);
			stmt.setInt(4, solution);
			
		    File file = new File(path);
		    FileInputStream input = new FileInputStream(file);
	 		stmt.setBinaryStream(1, input);
			stmt.executeUpdate();
			return true;
			
		} catch (SQLException | FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
}