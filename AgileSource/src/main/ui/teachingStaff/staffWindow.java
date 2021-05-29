package main.ui.teachingStaff;

import java.awt.BorderLayout;
// Import packages
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;

import main.databaseManager;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

public class staffWindow {
private JFrame frame;
private String fileName = null;
JTextField textField = new JTextField(20);
int resit = 0;
int solution = 0;
JLabel errorLabel = new JLabel("");
JLabel path = new JLabel("File: ");
JCheckBox resitCheckbox = new JCheckBox("Resit paper");
JCheckBox solutionCheckbox = new JCheckBox("Solution paper");
databaseManager db = new databaseManager();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staffWindow window = new staffWindow();
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
	public staffWindow() {
		initialize();
		db.connect();
		frame.setVisible(true);
	}
	
	public static void displayForm()
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame frame = new JFrame("Test");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                try 
                {
                   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                   e.printStackTrace();
                }
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setOpaque(true);
                JTextArea textArea = new JTextArea(15, 50);
                textArea.setWrapStyleWord(true);
                textArea.setEditable(false);
                textArea.setFont(Font.getFont(Font.SANS_SERIF));
                JScrollPane scroller = new JScrollPane(textArea);
                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                JPanel inputpanel = new JPanel();
                inputpanel.setLayout(new FlowLayout());
                JTextField moduleName = new JTextField(20);
                JButton button = new JButton("Submit Exam");
                DefaultCaret caret = (DefaultCaret) textArea.getCaret();
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                panel.add(scroller);
                inputpanel.add(moduleName);
                inputpanel.add(button);
                panel.add(inputpanel);
                frame.getContentPane().add(BorderLayout.CENTER, panel);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                 frame.setResizable(false);
                moduleName.requestFocus();
            }
        });
    }

	/**
	 * Initialise the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() {
		// Window frame
		frame = new JFrame("Upload Exam");
		frame.setBounds(100, 100, 700, 320);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Back button
		JButton backButton = new JButton("Back");
		backButton.setBounds(20, 20, 100, 25);
		backButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
		    	// Go back
				frame.dispose();
		    } 
		});
		
		// Upload new exam button
		JButton uploadButton = new JButton("Upload New Exam");
		uploadButton.setBounds(240, 240, 180, 20);
		uploadButton.addActionListener(new ActionListener() { 
		    public void actionPerformed(ActionEvent e) { 
		    	String documentName = textField.getText();
		    	// Check if filename or documentname are null
		    	if (fileName == null)
		    	{
		    		errorLabel.setText("You need to select a file to upload!");
		    	} else if (documentName == null || documentName.trim().isEmpty()) {
		    		textField.setText("");
		    		errorLabel.setText("You need to enter a name for the file!");
		    	} else {
		    		if (db.uploadExam(fileName, documentName, resit, solution)) {
		    			errorLabel.setText(fileName+" successfully uploaded!");
		    			path.setText("File: ");
		    			resitCheckbox.setSelected(false);
		    			resit = 0;
		    			solutionCheckbox.setSelected(false);
		    			solution = 0;
		    			textField.setText("");
		    		}
		    	}
		    } 
		});
		
		// Select exam button
		JButton openFileBrowserButton = new JButton("Select File");
		openFileBrowserButton.setBounds(180, 120, 180, 20);
		openFileBrowserButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				// Open file browser
				JFileChooser fileChooser = new JFileChooser("C:");
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    fileName = selectedFile.toString();
				    path.setText("File: "+fileName);
				}
		    } 
		});
		
		resitCheckbox.setBounds(450, 80, 180, 20);
		resitCheckbox.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
		        JCheckBox resitCheckbox = (JCheckBox) e.getSource();
		        if (resitCheckbox.isSelected()) {
		            resit = 1;
		        } else {
		            resit = 0;
		        }
		    }
		});
		
		solutionCheckbox.setBounds(450, 120, 180, 20);
		solutionCheckbox.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
		        JCheckBox solutionCheckbox = (JCheckBox) e.getSource();
		        if (solutionCheckbox.isSelected()) {
		        	solution = 1;
		        } else {
		        	solution = 0;
		        }
		    }
		});

		JLabel label = new JLabel("Document name: ");
		label.setBounds(60, 80, 100, 20);
		path.setBounds(180, 180, 420, 20);
		
		textField.setBounds(180, 80, 180, 25);
		frame.add(backButton);
		frame.add(label);
		frame.add(textField);
		frame.add(openFileBrowserButton);
		errorLabel.setBounds(180, 20, 520, 20);
		frame.add(errorLabel);
		frame.add(uploadButton);
		frame.add(resitCheckbox);
		frame.add(solutionCheckbox);
		frame.add(path);
	}
	
}
