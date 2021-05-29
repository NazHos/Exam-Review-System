package test.main;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Exam;
import main.User;
import main.databaseManager;
import main.debug;
import main.User.role;

class testDatabaseManager {

	databaseManager db;
	@BeforeEach
	void setUp() throws Exception {
		db = new databaseManager();
		assertTrue(db.connect(), "Database connection failed");
	}

	@AfterEach
	void tearDown() throws Exception {
		assertTrue(db.close(), "Database failed to close");
		db = null;
	}

	@Test
	void testSQLUpdate()
	{
		String randText = UUID.randomUUID().toString();
		assertTrue(db.update("INSERT INTO unit_tests (some_text) VALUES ('" + randText + "');"), "data entry to database failed");
		assertTrue(db.update("DELETE FROM unit_tests WHERE some_text = '"+ randText +"';"), "data deletion from database failed");
	}
	
	@Test
	void testSQLUpdateDuplicateEntry()	//Second insert should fail and return false
	{
		String randText = UUID.randomUUID().toString();
		assertTrue(db.update("INSERT INTO unit_tests (some_text) VALUES ('" + randText + "');"), "data entry to database failed");
		assertFalse(db.update("INSERT INTO unit_tests (some_text) VALUES ('" + randText + "');"), "update method did not fail on duplicate unique data entry");
		assertTrue(db.update("DELETE FROM unit_tests WHERE some_text = '"+ randText +"';"), "data entry to database failed");
	}
	
	@Test
	void testSQLQuery()
	{
		assertNotNull(db.query("SELECT * FROM unit_tests"), "null resultset received from query");
		ResultSet rs = db.query("SELECT COUNT(*) FROM unit_tests;");
		try {
			rs.next();
			assertTrue(rs.getInt(1) >= 2, "unit_tests resultset count lower than expected");
		} catch (SQLException e) {
			fail("SQL error not handled by query");
		}
	}
	
	@Test
	void testSQLQueryBadData()
	{
		assertNull(db.query("SELECT * FROM table_that_doesnt_exist"), "Received non-null object from bad data query");
	}
	
	@Test
	void testAddRemoveStaff()
	{
		int id = 999999269;
		int id2 = 999999279;
		String email = "tester@unitTest.xyz";
		String email2 = "tester@unitTest.xyz";
		assertTrue(db.update("DELETE FROM staff_account WHERE email = '"+ email +"'"), "pre-cleanup update failed");
		assertTrue(db.update("DELETE FROM staff_account WHERE email = '"+ email2 +"'"), "pre-cleanup update failed");
		
		assertTrue(db.addStaff(id, email, "test", "unit", "test", "Office_Staff"), "creation of new staff member returned false");
		assertFalse(db.addStaff(id, email2, "test", "unit", "test", "Office_Staff"), "duplicate id entry returned true");
		assertFalse(db.addStaff(id2, email, "test", "unit", "test", "Office_Staff"), "duplicate email entry returned true");
		
		assertTrue(db.removeStaff(id), "removal of staff id returned false");
		assertFalse(db.removeStaff(id), "removal of previously removed staff still returning true");
		
		assertTrue(db.addStaff(id2, email2, "test", "unit", "test", "Office_Staff"), "creation of secondary staff member returned false");
		assertTrue(db.removeStaff(email2), "removal of staff email returned false");
		
		assertFalse(db.removeStaff("invalid@email.err.or"), "removal of non-existant staff returned true");

	}
	
	@Test
	void testAuthentication()
	{
		int id = 999799999;
		String email = "tester@unitTest.xyz";
		
		assertTrue(db.update("DELETE FROM staff_account WHERE email = '"+ email +"'"), "pre-cleanup update failed");
		assertTrue(db.addStaff(id, email, "test", "unit", "test", "Office_Staff"), "creation of new staff member returned false");
		
		assertTrue(db.authenticate(id, "test"), "Authentication on known positive returned false");
		
		
		assertTrue(db.update("DELETE FROM staff_account WHERE email = '"+ email +"'"), "pre-cleanup 2 update failed");
		assertFalse(db.authenticate(id, "test"), "Authentication returned true after account removal");
		
		assertFalse(db.authenticate(59382, "password1"), "Authentication on non-existant account returned true");
	}
	
	
	@Test
	void testGetRole()
	{
		int id = 999799999;
		String email = "tester@unitTest.xyz";
		
		assertTrue(db.update("DELETE FROM staff_account WHERE email = '"+ email +"'"), "pre-cleanup update failed");
		assertTrue(db.addStaff(id, email, "test", "unit", "test", "Office_Staff"), "creation of new staff member returned false");
		
		assertTrue(db.getRole(id).equalsIgnoreCase("Office_Staff"), "Role retrieval expected Office Staff");
		assertTrue(db.getRole(999999999) == null, "Non-existent role did not return null");
		
		assertTrue(db.update("DELETE FROM staff_account WHERE email = '"+ email +"'"), "post-cleanup update failed");

		
	}
	
	@Test
	void testAddNewExam()
	{
		Exam e = new Exam();
		db.getUser(999999);
		User u = db.getUser(999999);
		e.setStaff(u);
		e.setInternalMod(u);
		e.setExternalMod(u);
		e.setModuleName("Agile Unit Tests");
		e.setStageInt(2);
		
		assertTrue(db.update("DELETE FROM exam WHERE staff_id = '"+ 9999999 +"'"), "pre-cleanup update failed");
		
		assertTrue(db.addNewExam(e), "New exam insersion returned false");
		
		assertTrue(db.update("DELETE FROM exam WHERE staff_id = '"+ 9999999 +"'"), "post-cleanup update failed");

	}
	
	@Test
	void testGetAllUsers()
	{
		ArrayList<User> allUsers = db.getAllUsers();
		
		assertTrue(allUsers != null, "getExams returned null arraylist");
		assertTrue(allUsers.size() > 0, "getExams returned empty arraylist");
		
		assertTrue(allUsers.get(0).getName() != "", "User name data empty");
		assertTrue(allUsers.get(0).getEmail() != "", "User email data empty");
		
	}
	
	@Test 
	void testGetUser()
	{
		User u = db.getUser(999999);
		
		assertTrue(u != null, "getUser returned null user");
		assertTrue(u.getName() != "", "User name data empty");
		assertTrue(u.getEmail() != "", "User email data empty");
		
	}
	
	@Test
	void testGetAllExams()
	{
		ArrayList<Exam> allExams = db.getExams(-1, -1, -1) ;
		
		assertTrue(allExams != null, "getExams returned null arraylist");
		assertTrue(allExams.size() > 0, "getExams returned empty arraylist");
		
		ArrayList<Exam> filteredExams = db.getExams(2019, 1, -1) ;
	
		assertTrue(filteredExams != null, "filtered getExams returned null arraylist");
		assertTrue(filteredExams.size() > 0, "filtered getExams returned empty arraylist");
		
		assertTrue(filteredExams.size() < allExams.size(), "filtered exam count should be less than all exam count");
	}

	@Test
	void testUploadExam()
	{
		assertFalse(db.uploadExam("FAKEPATH", "Test Upload", 0, 0));
		File file = new File("test.jpg");
		String fileName = file.getAbsolutePath().toString();
		fileName = fileName.replace("\\", "/");
		System.out.println(fileName);
		assertTrue(db.uploadExam(fileName, "Test Upload", 0, 0));
		//assertFalse(db.uploadExam("FAKEPATH", "Test Upload", 0, 0));
		//assertFalse(db.uploadExam("FAKEPATH", "Test Upload", 0, 0));
	}
		
	
	@Test
	void testSettings()
	{
		assertTrue(db.update("DELETE FROM settings WHERE key_string = \"UnitTest\""), "Pre-test cleanup failed");
		
		assertTrue(db.setSetting("UnitTest", "TestValue"), "Setting of UnitTest key failed");
		assertTrue(db.getSetting("UnitTest").equals("TestValue"), "Key did not match correct value");
		assertTrue(db.setSetting("UnitTest", "NewTestValue"), "Updating of UnitTest key failed");
		assertTrue(db.getSetting("UnitTest").equals("NewTestValue"), "Value of key did not update after previous test");
		
		assertTrue(db.update("DELETE FROM settings WHERE key_string = \"UnitTest\""), "Post-test cleanup failed");

	}
	@Test
	void testEditStaff()
	{
		int id = 999999299;
		String email = "tester8@unitTest.xyz";
		assertTrue(db.update("DELETE FROM staff_account WHERE email = '"+ email +"'"), "pre-cleanup update failed");
		assertTrue(db.addStaff(id, email, "test", "unit", "test", "Office_Staff"), "creation of new staff member returned false");
		assertTrue(db.editStaff(id, "testereditsemail", "testerpassword", "editfirstname", "editlastname"), "editing staff details failed");
		assertTrue(db.removeStaff(id), "removal of staff id returned false");
		assertFalse(db.removeStaff(id), "removal of previously removed staff still returning true");	
	}
	

	@Test
	void getComments() {
		ArrayList<Exam> e = db.getExams(2030, -1, -1);
		ArrayList<String> comments = db.getComments(e.get(0).getExamID());
		assertTrue(db.update("DELETE FROM messages WHERE sender_id = 999999"), "pre-cleanup failed");
		assertTrue(comments != null, "comments list is null");
		db.uploadComment("test", e.get(0), db.getUser(999999));
		comments = db.getComments(e.get(0).getExamID());
		assertTrue(comments.size() > 0, "comments list is empty after insersion");
		assertTrue(db.update("DELETE FROM messages WHERE sender_id = 999999"), "post-cleanup failed");
	}


	@Test
	void uploadComment() {
		ArrayList<Exam> e = db.getExams(2030, -1, -1);
		Exam exam = e.get(0);
		assertTrue(db.update("DELETE FROM messages WHERE sender_id = 999999"), "pre-cleanup failed");
		assertTrue(db.uploadComment("This is a comment", exam, db.getUser(999999)), "comment upload failed");
		assertTrue(db.update("DELETE FROM messages WHERE sender_id = 999999"), "post-cleanup failed");
	}

	
}
