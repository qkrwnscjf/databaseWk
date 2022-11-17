package com.postgresqltutorial;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Week11Lab implements ActionListener {
	
	private String url;
    private String user;
    private String password;
	private Connection conn;
	
	private JFrame frame;
	private JPanel panel;
	private JLabel idLabel;
	private JLabel pwdLabel;
	private JTextField idInput;
	private JPasswordField pwdInput;
	private JButton loginButton;
	
	private JTextArea check_area;
	private JComboBox<String> check_box;
	
	String[] queries = {"SELECT * FROM course", 
			"SELECT * FROM section", "SELECT * FROM prereq"};
	
	/* 2021049298 박준철
	 * Replace url, user and password, if preferred.
	 */
	public Week11Lab() {
		url = "jdbc:postgresql://localhost:5432/postgres";
		user = "postgres";
		password = "2845";

		frame = new JFrame();
		panel = new JPanel();
		idLabel = new JLabel("ID");
		pwdLabel = new JLabel("Password");
		idInput = new JTextField(user);
		pwdInput = new JPasswordField(password);
		loginButton = new JButton("Login");
		
		panel.setLayout(null);
		
		// Specify location of Components
		idLabel.setBounds(20, 10, 60, 30);
		pwdLabel.setBounds(20, 50, 60, 30);
		idInput.setBounds(100, 10, 80, 30);
		pwdInput.setBounds(100, 50, 80, 30);
		loginButton.setBounds(200, 25,  80, 35);
		
		// Add an ActionListener to the Login Button
		loginButton.addActionListener(this);
		
		// Add Components to Panel
		panel.add(idLabel);
		panel.add(pwdLabel);
		panel.add(idInput);
		panel.add(pwdInput);
		panel.add(loginButton);
		// Add Panel to Frame
		frame.add(panel);
		
		frame.setTitle("Week11Lab JDBC Lab 4");					// name on the top of the frame
		frame.setSize(320, 130);								// size of the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// call System.exit() on closing
		frame.setVisible(true);									// display the frame on the screen
	}
	
	/*
	 * This method pops up Course Info window
	 * replacing Login window.
	 * Course Info window must be shown after 
	 * the user successfully log in (and the 
	 * connection with the database server is
	 * successfully established).
	 * 
	 */
	private void courseInfo() {
		check_area = new JTextArea();
		check_box = new JComboBox<String>();
		
		frame.setVisible(false);
		
		frame = new JFrame();
		panel = new JPanel();
		
		panel.setFont(new Font(null, 1, 12));
		panel.setBorder(new TitledBorder("Inquiry"));
		panel.setBounds(380, 80, 490, 280);
		panel.setLayout(null);
		
		check_box.addItem("Course");
		check_box.addItem("Section");
		check_box.addItem("Prereq");
		
		check_area.setBorder(new LineBorder(Color.gray, 2));
		check_area.setEditable(false);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(check_area);
		
		check_box.setBounds(20, 40, 70, 30);
		scroll.setBounds(10, 80, 460, 270);
		
		check_box.addActionListener(this);
		
		panel.add(check_box);
		panel.add(scroll);
		
		frame.add(panel);
		
		frame.setTitle("Course Info");
		frame.setSize(500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}

	/*
	 * Modify this method to pop up Course Info window
	 * replacing the Login window. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			// Get the text entered in JTextField and JPasswordField
			user = idInput.getText();
			password = new String(pwdInput.getPassword());
			
			conn = this.connect();
			if(conn != null) {
				courseInfo();
			}
			
		} else if (e.getSource() == check_box) {
				try {
					showTable();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			
			
		}
	
	
	/*
	 * This method takes a table name
	 * and return query to retrieve every
	 * tuple of that table.
	 * The input table name does not need to
	 * be case sensitive.
	 * 
	 */
	private String getQuery(String tn) {
		String retQuery = null;
		if (tn.equalsIgnoreCase("course")) {
			retQuery = queries[0];
		} else if (tn.equalsIgnoreCase("section")) {
			retQuery = queries[1];
		} else if (tn.equalsIgnoreCase("prereq")) {
			retQuery = queries[2];
		}
		return retQuery;
	}
	
	/*
	 * Implement this method.
	 * The table content must be displayed on
	 * the Course Info window's JTextArea.
	 * 
	 * First row of the displayed table content
	 * must be the name of columns separated by
	 * a tab ('\t').
	 * 
	 * Second row of the displayed table content
	 * must be dash (-) as many times as the sum
	 * of length of column name string times 3.
	 * (# of dash = length of all column names * 3)
	 * 
	 * Then, each row of the table should be 
	 * displayed line by line.
	 * 
	 * The display should be as follow:
	 * <Column names separated by a tab>
	 * ----------------------------------- 
	 * <The 1st row of the chosen table>
	 * <The 2nd row of the chosen table>
	 * ...
	 * <The last row of the chosen table>
	 *  
	 */
	private void showTable() throws SQLException {
		String sql = getQuery((String) check_box.getSelectedItem());
	      PreparedStatement pstmt = null;
	      ResultSet rs;
	      ResultSetMetaData rsmd;
	      try {
	         String out = "";
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();
	         rsmd = rs.getMetaData();

	         int colCount = rsmd.getColumnCount();

	         for (int i = 1; i <= colCount; i++) {
	            System.out.print(rsmd.getColumnName(i) + "\t");
	            out += rsmd.getColumnName(i) + "\t";
	         }
	         System.out.println();
	         out += "\n";

	         String del = "";
	         for (int i = 1; i <= colCount; i++){
	            del += "---------------------";

	         }
	         out += del;
	         out += "\n";
	         System.out.print(del);
	         System.out.println();


	         while (rs.next()) {
	            for (int i = 1; i <= colCount; i++) {
	               System.out.print(rs.getObject(i) + "\t");
	               out += rs.getObject(i) + "\t";
	            }
	            System.out.println();
	            out += "\n";
	         }

	         check_area.setText(out);

	         rs.close();
	         pstmt.close();

	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
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
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Week11Lab();
        
    }

}
