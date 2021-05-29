package main.ui.administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mysql.cj.util.StringUtils;

import main.Exam;
import main.User;
import main.User.role;
import main.workflow.WorkflowExamSelection;
import main.databaseManager;
import main.debug;
import main.Exam.Stage;

public class administratorTools {

	administratorWindow adminWindow;
	databaseManager db = new databaseManager();
	
	private ArrayList<User> users;
	ArrayList<Exam> exams;
	
	public administratorTools(administratorWindow window)
	{
		db.connect();
		adminWindow = window;
		updateStaffList();
		adminWindow.txtMngExamDeadline.setText(db.getSetting("deadline"));
	}
	
	public void updateStaffList()
	{
		setUsers(db.getAllUsers());
		adminWindow.lstStaffModel.clear();
		for(User u : getUsers())
		{
			adminWindow.lstStaffModel.addElement("ID: " + u.getId() + "     <>     " + u.getName());
		}
		
		populateExamStaffDetails();
	}
	
	public boolean showPanel(JPanel pnl)
	{
		adminWindow.pnlManagStaff.setVisible(false);
		adminWindow.pnlAddStaff.setVisible(false);
		adminWindow.pnlManageExams.setVisible(false);
		adminWindow.pnlAddExam.setVisible(false);
		adminWindow.pnlEditStaff.setVisible(false);
		pnl.setVisible(true);
		return true;
	}
	
	public boolean submitExam()
	{
		if(adminWindow.txtExamAddModuleCode.getText().equals("")) {
			adminWindow.lblAddExamStatus.setText("Status: Module Code can not be null");
			return false;
		}
		
		User staff = db.getUser(Integer.parseInt(adminWindow.cmbExamAddModuleSetter.getSelectedItem().toString().substring(0, adminWindow.cmbExamAddModuleSetter.getSelectedItem().toString().indexOf("  "))));
		User intMod = db.getUser(Integer.parseInt(adminWindow.cmbExamAddInternalMod.getSelectedItem().toString().substring(0, adminWindow.cmbExamAddInternalMod.getSelectedItem().toString().indexOf("  "))));
		User extMod = db.getUser(Integer.parseInt(adminWindow.cmbExamAddExternalMod.getSelectedItem().toString().substring(0, adminWindow.cmbExamAddExternalMod.getSelectedItem().toString().indexOf("  "))));

		Exam exam = new Exam(adminWindow.txtExamAddModuleCode.getText(), Integer.parseInt(adminWindow.cmbExamAddYear.getSelectedItem().toString()),
				Integer.parseInt(adminWindow.cmbExamAddSemester.getSelectedItem().toString()), staff, intMod, extMod, Stage.NEW, null, -1);
		
		if(db.addNewExam(exam))
		{
			adminWindow.txtExamAddModuleCode.setText("");
			adminWindow.lblAddExamStatus.setText("Status: Exam Added Successfully");
			return true;
		}
		adminWindow.lblAddExamStatus.setText("Status: An Error Occoured during database interaction, exam not added");
		return false;		
	}
	
	/**
	 * Populates combo boxes of new exam page
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "incomplete-switch" })
	protected boolean populateExamStaffDetails()
	{

		for(User u : getUsers())
		{
			switch(u.getRole())
			{
			case Teaching_Staff:
				adminWindow.cmbExamAddModuleSetter.addItem(u.getId() + "          <>         " + u.getName());
				adminWindow.cmbExamAddInternalMod.addItem(u.getId() + "          <>         " + u.getName());
				break;
			case External_Moderator:
				adminWindow.cmbExamAddExternalMod.addItem(u.getId() + "          <>         " + u.getName());
				break;
			}
		}
		return false;
	}
	
	public boolean newUser()
	{
		int id = Integer.parseInt(adminWindow.txtID.getText());
		String email = adminWindow.txtEmail.getText();
		String fname = adminWindow.txtFName.getText();
		String sname = adminWindow.txtSName.getText();
		role urole = (role)adminWindow.cmbRole.getModel().getSelectedItem();
		String pass = adminWindow.txtPassword.getText();
		
		if(db.addStaff(id, email, pass, fname, sname, urole.toString()))
		{
			this.showPanel(adminWindow.pnlManagStaff);
			updateStaffList();
			return true;
		}else {
			return false;
		}
	}
	
	public boolean editUser()
	{
		int id = Integer.parseInt(adminWindow.txtEditStaffID.getText());
		String email = adminWindow.txtEditStaffEmail.getText();
		String fname = adminWindow.txtEditStaffFName.getText();
		String sname = adminWindow.txtEditStaffSName.getText();
		role urole = (role)adminWindow.cmbEditStaffRole.getModel().getSelectedItem();
		String pass = adminWindow.txtEditStaffPassword.getText();
		
		if(db.editStaff(id, email, pass, fname, sname))
		{
			this.showPanel(adminWindow.pnlManagStaff);
			updateStaffList();
			return true;
		}else {
			return false;
		}
	}
	
	public void viewExam(int index)
	{
		debug.log("Clicked exam " + index);
	}
	
	public boolean showFilteredExamList()
	{
		int year, semester, status;
		if(adminWindow.chkMngExamYear.isSelected())
		{
			year = Integer.parseInt(adminWindow.cmbMngExamFilterYear.getSelectedItem().toString());
		}else {
			year = -1;
		}
		
		if(adminWindow.chkMngExamSemester.isSelected())
		{
			semester = Integer.parseInt(adminWindow.cmbMngExamSemester.getSelectedItem().toString());
		}else {
			semester = -1;
		}

		if(adminWindow.chkMngExamStatus.isSelected())
		{
			status = Exam.stageToInt((Stage)adminWindow.cmbMngExamStatus.getSelectedItem());
		}else {
			status = -1;
		}
		
		exams = db.getExams(year, semester, status);
		if(exams == null)
			return false;
		adminWindow.MngExamsDisplayPnl.removeAll();
		adminWindow.MngExamsDisplayPnl.validate();
		adminWindow.MngExamsDisplayPnl.repaint();
		adminWindow.pnlManageExams.validate();
		adminWindow.pnlManageExams.repaint();
		
		for(int i=0; i < exams.size(); i++)
		{
			adminWindow.MngExamsDisplayPnl.add(new JLabel(exams.get(i).getModuleName()), "flowx,cell 1 "+(i+1)+",growx");
			adminWindow.MngExamsDisplayPnl.add(new JLabel("   -   " + exams.get(i).getStaff().getName() + "   -   "), "flowx,cell 2 "+(i+1)+",growx");
			JButton btnDetails = new JButton("View Details");
			final int index = i;
			btnDetails.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					viewExam(index);
				}
			});
			adminWindow.MngExamsDisplayPnl.add(btnDetails, "flowx,cell 3 "+(i+1)+",growx");
			adminWindow.MngExamsDisplayPnl.validate();
			adminWindow.MngExamsDisplayPnl.repaint();
			adminWindow.pnlManageExams.validate();
			adminWindow.pnlManageExams.repaint();
		}
		return true;
		//adminWindow.MngExamsScrollPane
	}

	public void staffListClicked(int index) {
		if(index < 0 || index > getUsers().size())
			return;
		User selectedUser = getUsers().get(index);
		adminWindow.lblName.setText(selectedUser.getName());
		adminWindow.lblEmail.setText(selectedUser.getEmail());
		adminWindow.lblDetailsRole.setText(selectedUser.getRole().toString());
		adminWindow.lblStaffID.setText(Integer.toString(selectedUser.getId()));
	}
	
	public void loadEditStaffDetails() {
		int index = adminWindow.lstStaffMembers.getSelectedIndex();
		if(index < 0 || index > getUsers().size())
		{
			debug.error("EMPTY LIST");
			return;
		}
		debug.log("RETRIEVING USER");
		User selectedUser = getUsers().get(index);
		debug.log("GOT USER " + selectedUser.getName());
		adminWindow.txtEditStaffID.setText(Integer.toString(selectedUser.getId()));
		adminWindow.txtEditStaffEmail.setText(selectedUser.getEmail());
		adminWindow.txtEditStaffFName.setText(selectedUser.getfName());
		adminWindow.txtEditStaffSName.setText(selectedUser.getSName());
		
		/*adminWindow.cmbRole.getToolTipText(selectedUser.getRole().toString());*/
		adminWindow.txtEditStaffPassword.setText(selectedUser.getName());		
	}
	
	/**
	 * Updates deadline data in settings table
	 * @return
	 */
	public boolean updateDeadline()
	{
		if(!StringUtils.isStrictlyNumeric(adminWindow.txtMngExamDeadline.getText()))
		{
			debug.error("User entered non-numeric string to deadline update field");
			return false;
		}
		if(db.setSetting("deadline", adminWindow.txtMngExamDeadline.getText()))
		{
			debug.log("Deadline update successfull");
			return true;
		}
		debug.error("Error during update of deadline");
		return false;
	}

	public void viewStaffExams(int index) {
		if(index < 0 || index > getUsers().size())
			return;
		User selectedUser = getUsers().get(index);
		new WorkflowExamSelection(adminWindow.frame.getLocation(), selectedUser);
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
}
