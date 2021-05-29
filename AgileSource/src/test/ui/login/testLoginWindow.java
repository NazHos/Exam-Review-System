package test.ui.login;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.ui.login.loginWindow;

class testLoginWindow {

	
	public loginWindow window;
	
	@BeforeEach
	void setUp()
	{
		window = new loginWindow();
	}
	
	@AfterEach
	void teardown()
	{
		window = null;
	}
	
	@Test
	void testDatabaseConnection() {
		assertTrue(window.databaseLogin(), "Database login failed");
	}
	
	@Test
	void testSignInAdmin() {
		window.txtUsername.setText("2");
		window.txtPass.setText("craig");
		assertTrue(window.signIn(), "Sign in returned false");
		
		window.txtPass.setText("wrongPassword");
		assertFalse(window.signIn(), "Sign in returned true despite wrong password");
	}

	@Test
	void testSignInTeachingStaff() {
		window.txtUsername.setText("947561");
		window.txtPass.setText("amunro");
		assertTrue(window.signIn(), "Sign in returned false");
		
		window.txtPass.setText("wrongPassword");
		assertFalse(window.signIn(), "Sign in returned true despite wrong password");
	}
	
	@Test
	void testSignInExternalModerator() {
		window.txtUsername.setText("546378");
		window.txtPass.setText("mreid");
		assertTrue(window.signIn(), "Sign in returned false");
		
		window.txtPass.setText("wrongPassword");
		assertFalse(window.signIn(), "Sign in returned true despite wrong password");
	}
	
	@Test
	void testSignInOfficeStaff() {
		window.txtUsername.setText("957164");
		window.txtPass.setText("pzbaker");
		assertTrue(window.signIn(), "Sign in returned false");
		
		window.txtPass.setText("wrongPassword");
		assertFalse(window.signIn(), "Sign in returned true despite wrong password");
	}
	
	@Test
	void testSignInVettingCommittee() {
		window.txtUsername.setText("948302");
		window.txtPass.setText("nhossain");
		assertTrue(window.signIn(), "Sign in returned false");
		
		window.txtPass.setText("wrongPassword");
		assertFalse(window.signIn(), "Sign in returned true despite wrong password");
	}
}
