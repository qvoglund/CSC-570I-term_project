package drugs;

import java.sql.*;

public class DrugsDosesDB {

	final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	final String DB_URL = "jdbc:mariadb://localhost:3306/drug_dosage";

	final String USER = "****";
	final String PASS = "***********";

	Connection conn = null;
	Statement stmt = null;

	
	public ResultSet select(String select) {
		ResultSet rs = null;
		String sql = select;
		
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("INIT Database Connection\n");
			DriverManager.getConnection(DB_URL, USER, PASS);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);//
			System.out.println("CONNECTED to Database: " + DB_URL + "\n");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
		} catch (SQLException se) {
			System.out.println("***SQL EXCEPTION***");
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("***EXCEPTION***");
			e.printStackTrace();
		} finally {
			try { if (stmt != null) { conn.close(); }
			} catch (SQLException se) {}
			try { if (conn != null) { conn.close(); }
			} catch (SQLException se) { se.printStackTrace(); }
		}
		
		
		return rs;
	}
	
	public void insert(String insert) {
		String sql = insert;
		
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("INIT Database Connection\n");
			DriverManager.getConnection(DB_URL, USER, PASS);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);//
			System.out.println("CONNECTED to Database: " + DB_URL + "\n");
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			
		} catch (SQLException se) {
			System.out.println("***SQL EXCEPTION***");
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("***EXCEPTION***");
			e.printStackTrace();
		} finally {
			try { if (stmt != null) { conn.close(); }
			} catch (SQLException se) {}
			try { if (conn != null) { conn.close(); }
			} catch (SQLException se) { se.printStackTrace(); }
		}
		
	}
	
}

