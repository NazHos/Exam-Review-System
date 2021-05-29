package main.ui.login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import main.User;
import main.databaseManager;
import main.ui.administrator.administratorWindow;
import main.workflow.WorkflowExamSelection;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Dimension;

public class loginWindow {

	private JFrame frame;
	public JTextField txtUsername;
	public JPasswordField txtPass;

	private static databaseManager db;
	private JLabel lblStatus;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginWindow window = new loginWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public loginWindow() {
		databaseLogin();
		initialize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setVisible(true);
	}
	
	public boolean databaseLogin()
	{
		db = new databaseManager();		
		boolean stat =  db.connect();
		return stat;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
				
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(200, 200, 500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(236, 61, 120, 20);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Staff_ID");
		lblUsername.setFont(new Font("Calibri", Font.BOLD, 18));
		lblUsername.setBounds(30, 61, 120, 20);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Calibri", Font.BOLD, 18));
		lblPassword.setBounds(30, 124, 99, 20);
		frame.getContentPane().add(lblPassword);
		
		JButton btnSignIn = new JButton("Sign in");
		btnSignIn.setFont(new Font("Calibri", Font.BOLD, 18));
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signIn();
			}
		});
		btnSignIn.setBounds(236, 185, 120, 37);
		frame.getContentPane().add(btnSignIn);
		
		txtPass = new JPasswordField();
		txtPass.setBounds(236, 124, 120, 20);
		frame.getContentPane().add(txtPass);	
		
		lblStatus = new JLabel("Status");
		lblStatus.setBounds(47, 185, 125, 39);
		frame.getContentPane().add(lblStatus);
		
	}
	
	public boolean signIn() {
		String password = txtPass.getText();
		String username = txtUsername.getText();
		
		boolean success = db.authenticate(Integer.parseInt(username), password);
		if(success)
		{
			User user = db.getUser(Integer.parseInt(username));
			String role = db.getRole(Integer.parseInt(username));
			
			if(role.equalsIgnoreCase("Administrator")) {
				new administratorWindow(frame.getLocation());
				frame.dispose();
				return true;
			}
			else if(role.equalsIgnoreCase("Teaching_Staff")) {
				new WorkflowExamSelection(frame.getLocation(), user);
				frame.dispose();
				return true;
			}
			else if(role.equalsIgnoreCase("External_moderator")){
				new WorkflowExamSelection(frame.getLocation(), user);
				frame.dispose();
			return true;
			}
			else if(role.equalsIgnoreCase("Office_Staff")) {
				new WorkflowExamSelection(frame.getLocation(), user);
				frame.dispose();
				return true;
			}
			else if(role.equalsIgnoreCase("Vetting_Committee")) {
				new WorkflowExamSelection(frame.getLocation(), user);
				frame.dispose();
				return true;
			}
		}else {
			lblStatus.setText("Status: Invalid username or password");
			txtPass.setText(null);
			txtUsername.setText(null);
			return false; 
		}
		return false;
	}
	
	private static class __Tmp {
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}
}
