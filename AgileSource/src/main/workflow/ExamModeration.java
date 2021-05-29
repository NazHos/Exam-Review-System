package main.workflow;


import java.awt.BorderLayout;
import main.*;
import main.ui.teachingStaff.staffWindow;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// currently for testing purposes
// Takes an Exam type as param
public class ExamModeration extends JFrame {

	private JPanel contentPane;
	private Exam exam;
	private ExamList examList;
	private JTextArea txtSend;
	private JTextArea txtComments;
	
	databaseManager db = new databaseManager();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExamModeration frame = new ExamModeration(null, null, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public ExamModeration(Exam examRef, ExamList elRef, User usr) {
		db.connect();
		initilize(examRef, elRef, usr);
		updateComments();
		this.setVisible(true);
	}
	
	public void initilize(Exam examRef, ExamList elRef, User usr)
	{
		setTitle("Exam Moderation");
		
		this.exam = examRef;
		this.examList = elRef;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 439);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblExamTitle = new JLabel("Exam Title:");
		lblExamTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblExamTitle.setBounds(10, 38, 414, 39);
		contentPane.add(lblExamTitle);
		// add the exam info to the window
		lblExamTitle.setText(exam.getModuleName());
		
		
		JLabel lblModerationDeadline = new JLabel("Moderation Deadline:");
		lblModerationDeadline.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblModerationDeadline.setBounds(10, 88, 365, 27);
		contentPane.add(lblModerationDeadline);
		
		JButton btnNewButton = new JButton("Download Exam");
		btnNewButton.setBounds(1, 206, 166, 39);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Sign Exam");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {	// sign button clicked
				// open signing window
				SignExam sign = new SignExam(exam, examList);
				sign.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(10, 362, 93, 27);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Upload New Version");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				staffWindow sw = new staffWindow();
			}
		});
		btnNewButton_2.setBounds(1, 257, 166, 39);
		contentPane.add(btnNewButton_2);
		
		JLabel lblStage = new JLabel("Stage:");
		lblStage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStage.setBounds(10, 66, 365, 27);
		contentPane.add(lblStage);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(177, 147, 250, 195);
		contentPane.add(scrollPane);
		
		txtComments = new JTextArea();
		txtComments.setEditable(false);
		txtComments.setLineWrap(true);
		scrollPane.setViewportView(txtComments);
		
		JButton btnNextStage = new JButton("Send to Next Stage");
		btnNextStage.setEnabled(false);
		btnNextStage.setBounds(1, 303, 166, 39);
		contentPane.add(btnNextStage);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(177, 350, 180, 39);
		contentPane.add(scrollPane_1);
		
		txtSend = new JTextArea();
		txtSend.setLineWrap(true);
		scrollPane_1.setViewportView(txtSend);
		
		JButton btnSendComment = new JButton(">");
		btnSendComment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(db.uploadComment(txtSend.getText(), exam, usr))
					updateComments();
			}
		});
		btnSendComment.setBounds(367, 360, 57, 23);
		contentPane.add(btnSendComment);
			
		
		lblStage.setText("Stage: " + exam.getStage().toString());
		lblModerationDeadline.setText("Moderation Deadline: " + exam.getCurrentDeadline());
		
		JButton btnNewButton_3 = new JButton("Back");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_3.setBounds(10, 11, 89, 23);
		contentPane.add(btnNewButton_3);
	}

	public boolean updateComments() {
		debug.log("COMMENTS");

		txtComments.setText("");
		txtSend.setText("");
		ArrayList<String> comments = db.getComments(exam.getExamID());
		if(comments == null)
			return false;
		String commentString = "";
		for(String c : comments)
			commentString += c;
		txtComments.setText(commentString);
		return true;
	}
}
