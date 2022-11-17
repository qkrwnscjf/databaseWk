package com.assignment.wk10;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * =========================
 * Week10 Assignment 
 * CSE3010 Database
 * Fall, 2022
 * =========================
 * 
 * -----------
 * Description
 * -----------
 * 
 * This is the skeleton code for the week10 assignment.
 * 
 * -------
 * Grading
 * -------
 * 
 * Execute .java files submitted by students by using
 * the eclipse environment.
 * 
 * For example, if the file 'Week10Submit.java'
 * is submitted by a student, then place it into
 * a package 'com.assignment.wk10' under the project 
 * 'PostgreSQLJDBC'. Then, see if it is compiled and run
 * without any error. 
 * 
 * Then, compare the console output of 'Week10Submit.java' 
 * with the console output of the solution code.
 * 
 * Total: 4 points
 * 1 point for each task (no partial mark)
 * 
 * @author - Beom Heyn Kim
 *
 */
public class Week10Submit implements ActionListener {

    private String url;
    private String user;
    private String password;
	
	private JFrame frame;
	private JPanel panel;
	private JLabel idLabel;
	private JLabel pwdLabel;
	private JTextField idInput;
	private JPasswordField pwdInput;
	private JButton loginButton;
	private JButton getTablesButton;
	private JLabel statusLabel;
	private JTextField statusMessageLabel;
	
	private Connection conn;
	
	public Week10Submit() {
		
	}
	
	/*
	 * Task 1. 
	 * Implement this method to properly initialize 
	 * GUI components. 
	 */
	public void task1() {
		url = "jdbc:postgresql://localhost/postgres";
		user = "postgres";
		password = "2845";
		
		// fill in here
		frame = new JFrame();
		panel = new JPanel();
		idLabel = new JLabel("ID");
		pwdLabel = new JLabel("Password");
		idInput = new JTextField();
		pwdInput = new JPasswordField();
		loginButton = new JButton("Login");
		getTablesButton = new JButton("Table");
		statusLabel = new JLabel("Status");
		statusMessageLabel = new JTextField();
		
		panel.setLayout(null);
		
		idLabel.setBounds(80, 10, 60, 30);
		pwdLabel.setBounds(160, 10, 60, 30);
		idInput.setBounds(70, 50, 80, 30);
		pwdInput.setBounds(160, 50, 80, 30);
		loginButton.setBounds(70, 100, 80, 35);
		getTablesButton.setBounds(160, 100, 80, 35);
		statusLabel.setBounds(60, 150, 60, 30);
		statusMessageLabel.setBounds(60, 180, 200, 35);
		
		
		loginButton.addActionListener(this);
		getTablesButton.addActionListener(this);
		
		
		
		// end
	}
	
	/*
	 * Task 2. 
	 * Implement this method to properly display 
	 * GUI components. 
	 */
	public void task2() {
		// fill in here
		String statusMessageString = "OK";
		panel.add(idLabel);
		panel.add(pwdLabel);
		panel.add(idInput);
		panel.add(pwdInput);
		panel.add(loginButton);
		panel.add(getTablesButton);
		panel.add(statusLabel);
		panel.add(statusMessageLabel);
		
		frame.add(panel);
		
		statusMessageLabel.setText(statusMessageString);
		
		frame.setTitle("Week10 Assignment");
		frame.setSize(320, 320);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		// end
	}
	
	/*
	 * Task 3. 
	 * Implement this method to connect to the database 
	 * with the input user ID and password. 
	 */
	public Connection task3() {
		String statusMessageString = "Connected";
		// fill in here
		user = idInput.getText();
		password = new String(pwdInput.getPassword());
		statusMessageLabel.setText("Connected");
		// end
		return this.connect();
	}
	
	/*
	 * Task 4.
	 * Implement this method to retrieve the list of tables
	 * and print out on Console.
	 */
	public void task4() {
		System.out.println("=====");
		System.out.println("Task4");
		System.out.println("=====");	
		String statusMessageString = "Available Tables are printed out on Console";
		try {
			// fill in here
			task3();
			statusMessageLabel.setText(statusMessageString);
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("\\dt");
				System.out.println(rs);
				rs.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// end
		
		}
			catch (NullPointerException ne) {
			ne.printStackTrace();
			statusMessageLabel.setText("NullPointerException: Did you Login?");
		}
		System.out.println("");
		System.out.println("");
	}
    
    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
    
    /**
     * Disconnect from the PostgreSQL database
     *
     */
    public void disconnect(Connection conn) {
        try {
        	conn.close();
            System.out.println("Connection is closed successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
	public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == loginButton) {
    		conn = task3();
    	} else if (e.getSource() == getTablesButton) {
    		task4();
    	}
	}
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Week10Submit sub = new Week10Submit();
        sub.task1();
        sub.task2();

    }

	
}

