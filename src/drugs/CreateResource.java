package drugs;

import org.restlet.resource.Post;
import java.sql.*;

import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.data.Status;
import org.restlet.data.MediaType;
import org.restlet.data.Form;

public class CreateResource extends ServerResource {
    public CreateResource() { }

    @Post
    public Representation create(Representation data) {
	Status status = null;
	String msg = "";

	// Extract the data from the POST body.
	Form fr = new Form(data);
	String drug = fr.getFirstValue("drug");
	String symptom = fr.getFirstValue("symptom");
	String dosage = fr.getFirstValue("dosage");
	String form = fr.getFirstValue("form");
	String threeToSix = fr.getFirstValue("3to6");
	String sixToTen = fr.getFirstValue("6to10");
	String tenToFifteen = fr.getFirstValue("10to15");
	String fifteenToTwenty = fr.getFirstValue("15to20");
	String twentyToTwenty_nine = fr.getFirstValue("20to29");
	
	Drug exists;

	if (drug == null) {
	    msg = "No drug name was submitted\n";
	    status = Status.CLIENT_ERROR_BAD_REQUEST;
	}
	else if ((exists = Drugs.find(drug)) != null && symptom == null) {
		msg = "Drug, " + exists.getDrug() + ", already exists in the database with id: " + exists.getId() + "\n";
		status = Status.CLIENT_ERROR_BAD_REQUEST;
	}
	else {
		final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
		final String DB_URL = "jdbc:mariadb://localhost:3306/drug_dosage";
	
		final String USER = "root";
		final String PASS = "Spaceghost9";
	
		Connection conn = null;
		Statement stmt = null;
		int drugId = -1;
		
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("INIT Database Connection\n");
			System.out.println("test"+DriverManager.getConnection(DB_URL, USER, PASS));
			conn = DriverManager.getConnection(DB_URL, USER, PASS);//
			System.out.println("CONNECTED to Database: " + DB_URL + "\n");
			stmt = conn.createStatement();
			ResultSet rs;
			String sql;
			
			if (exists == null) {
				sql = "INSERT INTO drugs (drug) VALUES (\"" + drug + "\")";
				System.out.println(sql);
				stmt.executeUpdate(sql);
				
				sql = "SELECT MAX(id) as id FROM drugs";
				rs = stmt.executeQuery(sql);
	
				if (rs.next()) {
					drugId = rs.getInt("id");
					System.out.println(drugId);
					Drugs.add(drugId, drug);
				    msg = "The drug, " + drug + ", with id: " + drugId + " has been added.\n";
				}
			} else {
				drugId = exists.getId();
			}
			sql = "SELECT * FROM doses WHERE symptom = \"" + symptom + "\" AND form = '" + form + "' AND id = " + drugId;
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				msg += "Symptom, " + symptom + ", already existed with form " + form + " for drug, " + drug + ". Updating symptom with new information.\n";
				sql = "UPDATE doses SET dosage = '" + dosage + "', form = '" + form + "', 3to6 = '" + threeToSix + "', 6to10 = '" + sixToTen +
						"', 10to15 = '" + tenToFifteen + "', 15to20 = '" + fifteenToTwenty + "', 20to29 = '" + twentyToTwenty_nine + "' WHERE id = " + drugId + " AND " +
								"symptom = '" + symptom + "'";
			} else {
				msg += "Symptom, " + symptom + " added to drug, " + drug + "\n";
				sql = "INSERT INTO doses VALUES (" + drugId + ", '" + symptom + "', '" + dosage + "', '" + form + "', '" + threeToSix + "', '" +
						sixToTen + "', '" + tenToFifteen + "', '" + fifteenToTwenty + "', '" + twentyToTwenty_nine + "')";
			}
			System.out.println(sql);
			stmt.execute(sql);
			
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
	
	    status = Status.SUCCESS_OK;
		}

		setStatus(status);
		return new StringRepresentation(msg, MediaType.TEXT_PLAIN);
	
    }
}


