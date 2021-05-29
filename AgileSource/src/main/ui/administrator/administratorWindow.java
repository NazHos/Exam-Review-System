package main.ui.administrator;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Point;

import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import main.User.role;
import main.ui.login.loginWindow;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import main.Exam.Stage;
import javax.swing.JCheckBox;

public class administratorWindow {

	
	//All panels
	protected JPanel pnlHome;
	protected JPanel pnlManagStaff;
	protected JPanel pnlAddStaff;
	protected JPanel pnlManageExams;
	protected JPanel pnlAddExam;
	protected JPanel pnlEditStaff;
	
	//New Staff Panel Fields
	JFrame frame;
	private JTextField txtSearch;
	protected JTextField txtID;
	protected JTextField txtEmail;
	protected JTextField txtFName;
	protected JTextField txtSName;
	protected JPasswordField txtPassword;
	protected JComboBox cmbRole;
	
	//edit Staff Panel Fields
	JFrame EditStaffFrame;
	protected JTextField txtEditStaffID;
	protected JTextField txtEditStaffEmail;
	protected JTextField txtEditStaffFName;
	protected JTextField txtEditStaffSName;
	protected JPasswordField txtEditStaffPassword;
	protected JComboBox cmbEditStaffRole;
	
	//Manage Staff elements for displaying details
	protected DefaultListModel lstStaffModel = new DefaultListModel();
	protected JLabel lblEmail;
	protected JLabel lblName;
	protected JLabel lblStaffID;
	protected JLabel lblDetailsRole;
	protected JList lstStaffMembers;
	
	//JLabel Edit Staff elements 
	protected JLabel lblesStaffID;
	protected JLabel lblesEmail;
	protected JLabel lblesForename;
	protected JLabel lblesSurname;
	protected JLabel lblesRole;
	protected JLabel lblesPassword;	
	
	//New Exam panel fields
	protected JTextField txtExamAddModuleCode;
	protected JComboBox cmbExamAddExternalMod;
	protected JComboBox cmbExamAddInternalMod;
	protected JComboBox cmbExamAddModuleSetter;
	protected JComboBox cmbExamAddSemester;
	protected JComboBox cmbExamAddYear;
	protected JLabel lblAddExamStatus;
	
	
	//Manage Exam Panel feields
	protected JButton btnMngExamsBack;
	protected JScrollPane MngExamsScrollPane;
	protected JComboBox cmbMngExamFilterYear;
	protected JComboBox cmbMngExamSemester;
	protected JComboBox cmbMngExamStatus;
	protected JCheckBox chkMngExamStatus;
	protected JCheckBox chkMngExamSemester;
	protected JCheckBox chkMngExamYear;
	protected JPanel MngExamsDisplayPnl;
	protected JTextField txtMngExamDeadline;

	
	private administratorTools adminTools;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					administratorWindow window = new administratorWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public administratorWindow() {this(null); }
	public administratorWindow(Point point) {
		initialize();
		if(point != null)
			frame.setLocation(point);
		adminTools = new administratorTools(this);
		adminTools.showPanel(pnlManagStaff);
		
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 898, 557);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	
	
	pnlManageExams = new JPanel();
	pnlManageExams.setBounds(0, 0, 892, 451);
	frame.getContentPane().add(pnlManageExams);
	pnlManageExams.setLayout(new MigLayout("", "[][][][][grow][][][][][][][][][][grow][][][][][][]", "[][][][][][][][][][][][][][][][]"));
	//Manage Exams
				
				btnMngExamsBack = new JButton("Back");
				btnMngExamsBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						adminTools.showPanel(pnlHome);
					}
				});
				pnlManageExams.add(btnMngExamsBack, "cell 0 0");
				

				
				MngExamsScrollPane = new JScrollPane();
				MngExamsScrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				pnlManageExams.add(MngExamsScrollPane, "cell 1 2 11 12,grow");
				
				Component horizontalStrut_1 = Box.createHorizontalStrut(23);
				MngExamsScrollPane.setColumnHeaderView(horizontalStrut_1);
				
				MngExamsDisplayPnl = new JPanel();
				MngExamsScrollPane.setViewportView(MngExamsDisplayPnl);
				MngExamsDisplayPnl.setLayout(new MigLayout("", "[]", "[]"));
				
				JLabel lblMngExamFilterYear = new JLabel("Year");
				lblMngExamFilterYear.setEnabled(false);
				pnlManageExams.add(lblMngExamFilterYear, "flowx,cell 13 2");
				
				cmbMngExamFilterYear = new JComboBox();
				cmbMngExamFilterYear.setModel(new DefaultComboBoxModel(new String[] {"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"}));
				pnlManageExams.add(cmbMngExamFilterYear, "flowx,cell 14 2,growx");
				
				JButton btnNewExam = new JButton("New Exam");
				btnNewExam.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adminTools.showPanel(pnlAddExam);
					}
				});
				
				JLabel lblSemester_1 = new JLabel("Semester");
				lblSemester_1.setEnabled(false);
				pnlManageExams.add(lblSemester_1, "cell 13 3,alignx trailing");
				
				cmbMngExamSemester = new JComboBox();
				cmbMngExamSemester.setModel(new DefaultComboBoxModel(new String[] {"1", "2"}));
				pnlManageExams.add(cmbMngExamSemester, "flowx,cell 14 3,growx");
				
				JLabel lblStatus_1 = new JLabel("Status");
				lblStatus_1.setEnabled(false);
				pnlManageExams.add(lblStatus_1, "cell 13 4,alignx trailing");
				
				cmbMngExamStatus = new JComboBox();
				cmbMngExamStatus.setModel(new DefaultComboBoxModel(Stage.values()));
				pnlManageExams.add(cmbMngExamStatus, "flowx,cell 14 4,growx");
				
				JButton btnMngExamSearch = new JButton("Search");
				btnMngExamSearch.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adminTools.showFilteredExamList();
					}
				});
				pnlManageExams.add(btnMngExamSearch, "cell 14 5");
				pnlManageExams.add(btnNewExam, "cell 1 15,aligny center");
				

				chkMngExamStatus = new JCheckBox("");
				pnlManageExams.add(chkMngExamStatus, "cell 14 4");
				
				chkMngExamSemester = new JCheckBox("");
				pnlManageExams.add(chkMngExamSemester, "cell 14 3");
				
				chkMngExamYear = new JCheckBox("");
				pnlManageExams.add(chkMngExamYear, "cell 14 2");
				
				JLabel lblAdjustDeadlinedays = new JLabel("Adjust Deadline (days):");
				pnlManageExams.add(lblAdjustDeadlinedays, "cell 3 15,alignx trailing");
				
				txtMngExamDeadline = new JTextField();
				txtMngExamDeadline.setText("0");
				pnlManageExams.add(txtMngExamDeadline, "flowx,cell 4 15,growx");
				txtMngExamDeadline.setColumns(10);
				
				JButton btnMngExamUpdateDeadline = new JButton("Update");
				btnMngExamUpdateDeadline.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						adminTools.updateDeadline();
					}
				});
				pnlManageExams.add(btnMngExamUpdateDeadline, "cell 4 15");
	

	
	
	pnlManagStaff = new JPanel();
	pnlManagStaff.setBounds(6, 6, 886, 446);
	frame.getContentPane().add(pnlManagStaff);
	pnlManagStaff.setLayout(new MigLayout("", "[][grow][][][]", "[][][][][][][grow][][]"));
	//Manage Staff
		
				lblStaffID = new JLabel("__________");
				pnlManagStaff.add(lblStaffID, "cell 2 2");
				
				JLabel lblDetails4 = new JLabel("Role:  ");
				pnlManagStaff.add(lblDetails4, "flowx,cell 3 2");
				
				lblDetailsRole = new JLabel("__________");
				pnlManagStaff.add(lblDetailsRole, "cell 3 2");
				
				JButton btnMngBack = new JButton("Back");
				btnMngBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new loginWindow();
						frame.dispose();
					}
				});
				pnlManagStaff.add(btnMngBack, "cell 0 0");
				
				JLabel lblSearch = new JLabel("Search: ");
				pnlManagStaff.add(lblSearch, "flowx,cell 1 1");
				
				lstStaffMembers = new JList(lstStaffModel);
				lstStaffMembers.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
						adminTools.staffListClicked(lstStaffMembers.getSelectedIndex());
					}
				});
				
				JLabel lblDetails3 = new JLabel("Staff ID: ");
				pnlManagStaff.add(lblDetails3, "flowx,cell 2 2");
				
				JButton btnViewExams = new JButton("View Staff Members Exams");
				btnViewExams.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						adminTools.viewStaffExams(lstStaffMembers.getSelectedIndex());
					}
				});
				pnlManagStaff.add(btnViewExams, "cell 2 3,growx");
				pnlManagStaff.add(lstStaffMembers, "cell 1 4,grow");
				
				JLabel lblDetails1 = new JLabel("Name: ");
				pnlManagStaff.add(lblDetails1, "flowx,cell 2 1");
				
				lblName = new JLabel("__________");
				pnlManagStaff.add(lblName, "cell 2 1");
				
				JLabel lblDetails2 = new JLabel("Email: ");
				pnlManagStaff.add(lblDetails2, "flowx,cell 3 1");
				
				lblEmail = new JLabel("__________");
				pnlManagStaff.add(lblEmail, "cell 3 1");
				
				
				JButton btnEditStaff = new JButton("Edit Staff Member");
				btnEditStaff.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adminTools.loadEditStaffDetails();
						adminTools.showPanel(pnlEditStaff);
						//debug.log("Loading details for edit");
						pnlManagStaff.validate();
						pnlManagStaff.repaint();
					}
				});
				pnlManagStaff.add(btnEditStaff, "cell 2 7");
				
				
				
				txtSearch = new JTextField();
				pnlManagStaff.add(txtSearch, "cell 1 1");
				txtSearch.setColumns(10);
				
				JButton btnNewStaff = new JButton("Add New Staff");
				btnNewStaff.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adminTools.showPanel(pnlAddStaff);
					}
				});
				pnlManagStaff.add(btnNewStaff, "cell 2 7");
				
			Component horizontalStrut = Box.createHorizontalStrut(50);
			pnlManagStaff.add(horizontalStrut, "cell 4 8");
			
			pnlManagStaff.setVisible(false);
				
	pnlEditStaff = new JPanel();
	pnlEditStaff.setBounds(6, 6, 886, 446);
	frame.getContentPane().add(pnlEditStaff);
	pnlEditStaff.setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][][][]"));
	
	JButton button = new JButton("Back");
	button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			adminTools.showPanel(pnlManagStaff);
		}
	});
	pnlEditStaff.add(button, "cell 0 0");
	
	lblesStaffID = new JLabel("Staff ID:");
	pnlEditStaff.add(lblesStaffID, "cell 1 3,alignx trailing");
	
	txtEditStaffID = new JTextField();
	pnlEditStaff.add(txtEditStaffID, "cell 2 3,growx");
	txtEditStaffID.setColumns(1);
	
	lblesEmail = new JLabel("Email: ");
	pnlEditStaff.add(lblesEmail, "cell 1 4,alignx trailing");
	
	txtEditStaffEmail = new JTextField();
	pnlEditStaff.add(txtEditStaffEmail, "cell 2 4,growx");
	txtEditStaffEmail.setColumns(10);
	
	lblesForename = new JLabel("First Name: ");
	pnlEditStaff.add(lblesForename, "cell 1 5,alignx trailing");
	
	txtEditStaffFName = new JTextField();
	pnlEditStaff.add(txtEditStaffFName, "cell 2 5,growx");
	txtEditStaffFName.setColumns(10);
	
	lblesSurname = new JLabel("Surname: ");
	pnlEditStaff.add(lblesSurname, "cell 1 6,alignx trailing");
	
	txtEditStaffSName = new JTextField();
	pnlEditStaff.add(txtEditStaffSName, "cell 2 6,growx");
	txtEditStaffSName.setColumns(10);
	
	lblesRole = new JLabel("Role: ");
	pnlEditStaff.add(lblesRole, "cell 1 7,alignx trailing");
	
	cmbEditStaffRole = new JComboBox();
	cmbEditStaffRole.setModel(new DefaultComboBoxModel(role.values()));
	pnlEditStaff.add(cmbEditStaffRole, "cell 2 7,growx");
	
	lblesPassword = new JLabel("Password:");
	pnlEditStaff.add(lblesPassword, "cell 1 8,alignx trailing");
	
	txtEditStaffPassword = new JPasswordField();
	pnlEditStaff.add(txtEditStaffPassword, "cell 2 8,growx");
	
	JButton btnesSubmit = new JButton("Submit");
	btnesSubmit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			adminTools.editUser();
		}
	});
	pnlEditStaff.add(btnesSubmit, "flowx,cell 2 10");
	
	JLabel lblesStatus = new JLabel("");
	pnlEditStaff.add(lblesStatus, "cell 2 10");

pnlAddStaff = new JPanel();
pnlAddStaff.setBounds(6, 6, 886, 446);
frame.getContentPane().add(pnlAddStaff);
pnlAddStaff.setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][][][][]"));
		
				JButton btnBack = new JButton("Back");
				btnBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adminTools.showPanel(pnlManagStaff);
					}
				});
				pnlAddStaff.add(btnBack, "cell 0 0");
				
				JLabel lblNewLabel = new JLabel("Staff ID:");
				pnlAddStaff.add(lblNewLabel, "cell 1 3,alignx trailing");
				
				txtID = new JTextField();
				pnlAddStaff.add(txtID, "cell 2 3,growx");
				txtID.setColumns(1);
				
				JLabel lblNewLabel_1 = new JLabel("Email: ");
				pnlAddStaff.add(lblNewLabel_1, "cell 1 4,alignx trailing");
				
				txtEmail = new JTextField();
				pnlAddStaff.add(txtEmail, "cell 2 4,growx");
				txtEmail.setColumns(10);
				
				JLabel lblNewLabel_2 = new JLabel("First Name: ");
				pnlAddStaff.add(lblNewLabel_2, "cell 1 5,alignx trailing");
				
				txtFName = new JTextField();
				pnlAddStaff.add(txtFName, "cell 2 5,growx");
				txtFName.setColumns(10);
				
				JLabel lblSurname = new JLabel("Surname: ");
				pnlAddStaff.add(lblSurname, "cell 1 6,alignx trailing");
				
				txtSName = new JTextField();
				pnlAddStaff.add(txtSName, "cell 2 6,growx");
				txtSName.setColumns(10);
				
				JLabel lblRole = new JLabel("Role: ");
				pnlAddStaff.add(lblRole, "cell 1 7,alignx trailing");
				
				cmbRole = new JComboBox();
				cmbRole.setModel(new DefaultComboBoxModel(role.values()));
				pnlAddStaff.add(cmbRole, "cell 2 7,growx");
				
				JLabel lblPassword = new JLabel("Password:");
				pnlAddStaff.add(lblPassword, "cell 1 8,alignx trailing");
				
				txtPassword = new JPasswordField();
				pnlAddStaff.add(txtPassword, "cell 2 8,growx");
				
				JButton btnSubmit = new JButton("Submit");
				btnSubmit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adminTools.newUser();
					}
				});
				pnlAddStaff.add(btnSubmit, "flowx,cell 2 10");
				
				JLabel lblStatus = new JLabel("");
				pnlAddStaff.add(lblStatus, "cell 2 10");
			
			pnlAddExam = new JPanel();
			pnlAddExam.setBounds(0, 0, 886, 446);
			frame.getContentPane().add(pnlAddExam);
			pnlAddExam.setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][][][][][]"));
			
			JButton btnBack_1 = new JButton("Back");
			btnBack_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					adminTools.showPanel(pnlManageExams);
				}
			});
			pnlAddExam.add(btnBack_1, "cell 0 0");
			
			JLabel lblModuleCode = new JLabel("Module Code:");
			pnlAddExam.add(lblModuleCode, "cell 1 3,alignx trailing");
			
			txtExamAddModuleCode = new JTextField();
			pnlAddExam.add(txtExamAddModuleCode, "cell 2 3,growx");
			txtExamAddModuleCode.setColumns(10);
			
			JLabel lblAcademicYear = new JLabel("Academic Year: ");
			pnlAddExam.add(lblAcademicYear, "cell 1 4,alignx trailing");
			
			cmbExamAddYear = new JComboBox();
			cmbExamAddYear.setModel(new DefaultComboBoxModel(new String[] {"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"}));
			pnlAddExam.add(cmbExamAddYear, "cell 2 4,growx");
			
			JLabel lblSemester = new JLabel("Semester:");
			pnlAddExam.add(lblSemester, "cell 1 5,alignx trailing");
			
			cmbExamAddSemester = new JComboBox();
			cmbExamAddSemester.setModel(new DefaultComboBoxModel(new String[] {"1", "2"}));
			pnlAddExam.add(cmbExamAddSemester, "cell 2 5,growx");
			
			JLabel lblModuleExamSetter = new JLabel("Module Exam Setter: ");
			pnlAddExam.add(lblModuleExamSetter, "cell 1 6,alignx trailing");
			
			cmbExamAddModuleSetter = new JComboBox();
			pnlAddExam.add(cmbExamAddModuleSetter, "cell 2 6,growx");
			
			JLabel lblExternalModerator = new JLabel("Internal Moderator: ");
			pnlAddExam.add(lblExternalModerator, "cell 1 7,alignx trailing");
			
			cmbExamAddInternalMod = new JComboBox();
			pnlAddExam.add(cmbExamAddInternalMod, "cell 2 7,growx");
			
			JLabel lblInternalExternalModerator = new JLabel("External Moderator: ");
			pnlAddExam.add(lblInternalExternalModerator, "cell 1 8,alignx trailing");
			
			cmbExamAddExternalMod = new JComboBox();
			pnlAddExam.add(cmbExamAddExternalMod, "cell 2 8,growx");
			
			JButton btnSubmitExam = new JButton("Submit Exam");
			btnSubmitExam.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					adminTools.submitExam();
				}
			});
			pnlAddExam.add(btnSubmitExam, "flowx,cell 2 11");
			
			lblAddExamStatus = new JLabel("Status: Awaiting Input");
			pnlAddExam.add(lblAddExamStatus, "cell 2 11");
			
pnlHome = new JPanel();
pnlHome.setBounds(6, 464, 886, 60);
pnlHome.setBorder(new EmptyBorder(10, 10, 10, 10));
frame.getContentPane().add(pnlHome);
pnlHome.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][]", "[]"));
//Home			
			JButton btnManageStaff = new JButton("Manage Staff");
			btnManageStaff.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					adminTools.showPanel(pnlManagStaff);
				}
			});
			btnManageStaff.setFont(new Font("Dialog", Font.BOLD, 15));
			pnlHome.add(btnManageStaff, "cell 6 0");
			
			JButton btnManageExams = new JButton("Manage Exams");
			btnManageExams.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					adminTools.showPanel(pnlManageExams);
				}
			});
			btnManageExams.setFont(new Font("Dialog", Font.BOLD, 15));
			pnlHome.add(btnManageExams, "cell 13 0");
//Edit Staff
		
		
		/** Display list of current exams */
		//TODO: MOVE TO ADMIN TOOLS
		/*
		String[] examList;
		// Count exams
		int[] exams = adminTools.countExams();
		if (exams == null) {
			// Label to alert user if there are no exams created
			JLabel noExamLabel = new JLabel("There are no exams created at this time.");
			noExamLabel.setBounds(30, 60, 240, 25);
			pnlManageExams.add(noExamLabel);
		} else {
			// Create a list of each exam
			// Create a list of labels containing each exam
			// Create a list of buttons allowing user to view/edit each exam
			examList = new String[exams.length];
			JLabel[] examLabel = new JLabel[exams.length];
			JButton[] viewExamButton = new JButton[exams.length];
			for (int i = 0; i < exams.length; i++) { 
				String exam = adminTools.displayExams(exams[i]);
				examList[i] = exam;
				examLabel[i] = new JLabel(i+1+". "+exam);
				examLabel[i].setBounds(50, 60+(i*35), 240, 25);
				int pos = (i*4)+19;
				pnlManageExams.add(examLabel[i], "cell 19 "+pos);
				viewExamButton[i] = new JButton("View");
				viewExamButton[i].setBounds(230, 60+(i*35), 80, 25);
				viewExamButton[i].setName(Integer.toString(exams[i]));
				viewExamButton[i].addActionListener(new ActionListener() { 
				    public void actionPerformed(ActionEvent e) { 
				    	try {
				    		JButton btn = (JButton) e.getSource();
				    		adminTools.viewExam(btn.getName());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				    } 
				});
				pnlManageExams.add(viewExamButton[i]);
			}
		}
		*/

	}
}
