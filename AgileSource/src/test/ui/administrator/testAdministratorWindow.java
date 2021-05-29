package test.ui.administrator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.User;
import main.databaseManager;
import main.debug;
import main.User.role;
import main.ui.administrator.administratorTools;
import main.ui.administrator.administratorWindow;

class testAdministratorWindow extends administratorWindow {
	
	administratorTools adminTools = new administratorTools(this);

	
	@Test
	void testUpdateStaffList() {
		adminTools.updateStaffList();
		assertFalse(this.lstStaffModel.isEmpty(), "List empty after updateStaffList call");
	}
	
	@Test
	void testShowPanel()
	{
		initialize();
		adminTools.showPanel(pnlAddExam);
		assertFalse(this.pnlManagStaff.isVisible(), "Manage staff exam should not be visible after showPanel call to other panel");
		assertTrue(pnlAddExam.isVisible(), "Add exam panel should be visible after call to showPanel");
		
		adminTools.showPanel(pnlManagStaff);
		assertTrue(this.pnlManagStaff.isVisible(), "Manage staff exam should be visible after showPanel call");
	
	}
	
	@Test
	void testloadEditStaffDetails()
	{
		databaseManager db = new databaseManager();
		assertTrue(db.connect(), "Database connection failed");	
		
		adminTools.loadEditStaffDetails();
		User selectedUser = db.getUser(999999);
		
		assertFalse(selectedUser == null);
		assertTrue(selectedUser != null);
		
		assertTrue(this.txtEditStaffID != null);
		assertTrue(this.txtEditStaffFName != null);
		assertTrue(this.txtEditStaffSName != null);
		assertTrue(this.txtEditStaffEmail != null);
		assertTrue(this.txtEditStaffPassword != null);		
	}
	
	@Test
	void testUpdateDeadline()
	{
		databaseManager db = new databaseManager();
		db.connect();
		int deadline = Integer.parseInt(db.getSetting("deadline"));
		assertTrue(this.txtMngExamDeadline.getText().equals(Integer.toString(deadline)), "Default text not equal to database value");

		assertTrue(adminTools.updateDeadline(), "updateDeadline method returned false before any modification");
		this.txtMngExamDeadline.setText(Integer.toString((deadline + 10)));
		
		assertTrue(adminTools.updateDeadline(), "updateDeadline method returned false after text modification");

		this.txtMngExamDeadline.setText("should fail!");

		assertFalse(adminTools.updateDeadline(), "updateDeadline returned true on non-numeric string");
	}

	@Test
	void testEditUser()
	{
		databaseManager db = new databaseManager();
		db.connect(); 
		
		adminTools.loadEditStaffDetails();
		User selectedUser = db.getUser(999999);
		
		assertTrue(selectedUser != null);
		assertTrue(this.txtEditStaffID != null);
		assertTrue(this.txtEditStaffFName != null);
		assertTrue(this.txtEditStaffSName != null);
		assertTrue(this.txtEditStaffEmail != null);
		assertTrue(this.txtEditStaffPassword != null);	
	}
	
	@Test
	void testPopulateExamStaffDetails()
	{
		assertTrue(this.lstStaffMembers.getModel().getSize() > 0, "list model is empty");
	}
	
	@Test
	void testShowFilteredExamList()
	{
		this.chkMngExamYear.setSelected(true);
		this.cmbMngExamFilterYear.setSelectedItem("2030");
		assertTrue(adminTools.showFilteredExamList(), "update function failed");
		int count = this.MngExamsDisplayPnl.getComponents().length;
		
		this.chkMngExamSemester.setSelected(true);
		this.cmbMngExamSemester.setSelectedItem("2");
		assertTrue(adminTools.showFilteredExamList(), "update function failed");
		assertTrue(this.MngExamsDisplayPnl.getComponents().length != count, "component count should have changed");	
	}
	
	@Test
	void testNewUser()
	{
		databaseManager db = new databaseManager();
		db.connect();
		assertTrue(db.update("DELETE FROM staff_account WHERE staff_id = 999990"), "pre-cleanup failed");
		adminTools.updateStaffList();
		
		txtID.setText("999990");
		txtEmail.setText("Test@UnitTest.xyz");
		txtFName.setText("Unit");
		txtSName.setText("Test");
		txtPassword.setText("utest");
		
		assertTrue(adminTools.newUser(), "new user returned false");
		assertTrue(db.update("DELETE FROM staff_account WHERE staff_id = 999990"), "post-cleanup failed");

	}
	
	@Test
	void testStaffListClicked()
	{
		databaseManager db = new databaseManager();
		db.connect(); 
		
		adminTools.loadEditStaffDetails();
		User selectedUser = db.getUser(999999);
		assertTrue(selectedUser != null);
	}
	
}
