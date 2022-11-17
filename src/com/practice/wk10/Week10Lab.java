package com.practice.wk10;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Week10Lab implements ActionListener {
	
	
	private final String url = "jdbc:postgresql://localhost:5432/postgres";
	private  String user = "postgres";
	private  String password = "2845";
	
	
	public Connection connection () {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connected to the PostgreSQL server successfully.");
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
	
	
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JLabel idLabel = new JLabel("ID");
	private JLabel pwdLabel = new JLabel("Password");
	private JTextField idInput = new JTextField();
	private JPasswordField pwdInput = new JPasswordField();
	private JButton loginButton = new JButton("Login");
	
	
	public static void main(String[] args) {
		new Week10Lab();
	}
	
	public Week10Lab() {
		panel.setLayout(null);
		idLabel.setBounds(20, 10, 60, 30);
		pwdLabel.setBounds(20, 50, 60, 30);
		idInput.setBounds(100, 10, 80, 30);
		pwdInput.setBounds(100, 50, 80, 30);
		loginButton.setBounds(200, 25, 80, 35);
		
		loginButton.addActionListener(this);
		
		panel.add(idLabel);
		panel.add(pwdLabel);
		panel.add(idInput);
		panel.add(pwdInput);
		panel.add(loginButton);
		
		frame.add(panel);
		
		frame.setTitle("Week10Lab JDBC Lab 3");
		frame.setSize(320, 130);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == loginButton) {
			user = idInput.getText();
			password = new String(pwdInput.getPassword());
			
			this.connection();
		}
	}
}
