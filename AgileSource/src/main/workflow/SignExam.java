package main.workflow;

import java.awt.BorderLayout;
import main.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SignExam extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private Exam exam;
	private ExamList eList;	// spaghetti 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignExam frame = new SignExam(null, null);
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
	public SignExam(Exam examRef, ExamList elRef) {
		
		this.exam = examRef;
		this.eList = elRef;
		setTitle("Exam Signing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 322, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCompleteTheFields = new JLabel("Complete the fields below");
		lblCompleteTheFields.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCompleteTheFields.setBounds(10, 11, 263, 30);
		contentPane.add(lblCompleteTheFields);
		
		textField = new JTextField();
		textField.setBounds(10, 69, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 130, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblName.setBounds(10, 38, 130, 20);
		contentPane.add(lblName);
		
		JLabel lblDateddmmyyyy = new JLabel("Date (DD/MM/YYYY):");
		lblDateddmmyyyy.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDateddmmyyyy.setBounds(10, 100, 227, 19);
		contentPane.add(lblDateddmmyyyy);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {			// submit button pressed
				// check if either field is empty
				String test1 = textField.getText();
				String test2 = textField_1.getText();
				if (test1 == "" || test2 == "") {
					// error display
				}
				else {
					String name = textField.getText();
					String date = textField_1.getText();
					// pass this info, sign exam and make available to next
					// currently adds a new copy of the existing exam with new sign date
					Exam newExam = new Exam();
					newExam = exam;	// copy the exam being signed
					newExam.setSignedDate(date);
					int i = newExam.getStageInt() + 1;
					if (i == 6) {	// error: exam was already completed
						
					}
					else {
						newExam.setStageInt(i);
						eList.addExamDB(newExam);
					}
				}
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSubmit.setBounds(186, 121, 110, 38);
		contentPane.add(btnSubmit);
		
		JLabel lblError = new JLabel("<html>Please Ensure both <br/> fields are completed</html>");
		lblError.setBounds(176, 52, 120, 47);
		contentPane.add(lblError);
		lblError.setVisible(false);
	}
}
