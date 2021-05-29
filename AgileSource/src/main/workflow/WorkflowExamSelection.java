package main.workflow;

import java.awt.EventQueue;
import main.*;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import java.util.*;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// back and refresh button currently not implemented

public class WorkflowExamSelection {

	private JFrame frmExamModeration;
	protected databaseManager db = new databaseManager();
	protected JLabel lblStaffDetails;
	protected JList list;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WorkflowExamSelection window = new WorkflowExamSelection(null);
					window.frmExamModeration.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WorkflowExamSelection() { this(null, null); }
	public WorkflowExamSelection(User user) { this(null, user); }

	public WorkflowExamSelection(Point point, User user) {
		db.connect();
		if(user == null)
			user = db.getUser(999999);
		initialize(user);
		lblStaffDetails.setText(user.getRole().toString() + " - " + user.getName());
		if(point != null)
			frmExamModeration.setLocation(point);
		frmExamModeration.setVisible(true);		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize(User user) {
				
		//WorkflowTest test1 = new WorkflowTest(2);	// perform test 2
		//ExamList elist = test1.getExamList();
		
		
		ArrayList<Exam> ExamArrayList = db.getExams(-1, -1, -1, user);
		ExamList examList = new ExamList();
		examList.setExList(ExamArrayList);
		
		DefaultListModel listModel = new DefaultListModel();	 // list that is displayed in the jlist
		///////////// build the list model ////////////
		for (int i = 0; i < ExamArrayList.size(); i++) {	// iterate through the list
			Exam exam = ExamArrayList.get(i);
			String title = exam.getModuleName();
			listModel.addElement(title);
		}
		
		
		
		frmExamModeration = new JFrame();
		frmExamModeration.setTitle("Exam Moderation");
		frmExamModeration.setBounds(100, 100, 450, 382);
		frmExamModeration.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmExamModeration.getContentPane().setLayout(null);
		
		list = new JList(listModel);		// init  jlist with the listmodel
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(54, 78, 305, 157);
		frmExamModeration.getContentPane().add(list);
		
		JButton btnNewButton = new JButton("Select");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {		// Select button clicked
				if (list.isSelectionEmpty()) {
					// nothing selected
				}
				else {
					// get the index of exam selected in jlist
					int index = list.getSelectedIndex();
					// open new window for that exam
					ExamModeration exammod = new ExamModeration(ExamArrayList.get(index), examList, user);
					exammod.setLocation(frmExamModeration.getLocation());
					exammod.setVisible(true);
				}
			}
		});
		btnNewButton.setBounds(54, 247, 100, 36);
		frmExamModeration.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Refresh");
		btnNewButton_1.setBounds(54, 294, 100, 39);
		frmExamModeration.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Back");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmExamModeration.dispose();
			}
		});
		btnNewButton_2.setBounds(189, 247, 100, 36);
		frmExamModeration.getContentPane().add(btnNewButton_2);
		
		JLabel lblSelectAnExam = new JLabel("Select an exam to Moderate");
		lblSelectAnExam.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectAnExam.setBounds(54, 35, 305, 31);
		frmExamModeration.getContentPane().add(lblSelectAnExam);
		
		JLabel lblNewLabel = new JLabel("Logged In As: ");
		lblNewLabel.setBounds(54, 8, 106, 15);
		frmExamModeration.getContentPane().add(lblNewLabel);
		
		lblStaffDetails = new JLabel("");
		lblStaffDetails.setBounds(158, 8, 280, 15);
		frmExamModeration.getContentPane().add(lblStaffDetails);
	}
}
