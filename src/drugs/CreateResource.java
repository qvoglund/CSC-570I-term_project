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
		else if ((exists = Drugs.find(drug)) != null && symptom == null && dosage == null && form == null) {
			msg = "Drug, " + exists.getDrug() + ", already exists in the database with id: " + exists.getId() + "\n";
			status = Status.CLIENT_ERROR_BAD_REQUEST;
		}
		else {
			int drugId = -1;
			
			try {
				DrugsDosesDB db = new DrugsDosesDB();
				ResultSet rs;
				String sql;
				
				if (exists == null) {
					sql = "INSERT INTO drugs (drug) VALUES (\"" + drug + "\")";
					db.insert(sql);
					
					sql = "SELECT MAX(id) as id FROM drugs";
					
					rs = db.select(sql);
		
					if (rs.next()) {
						drugId = rs.getInt("id");
						Drugs.add(drugId, drug);
					    msg = "The drug, " + drug + ", with id: " + drugId + " has been added.\n";
					}
				} else {
					drugId = exists.getId();
				}
				if (symptom != null && form != null && dosage != null) {
					sql = "SELECT * FROM doses WHERE symptom = \"" + symptom + "\" AND form = '" + form + "' AND id = " + drugId;
					rs = db.select(sql);
				
					if (rs.next()) {
						msg += "Symptom, " + symptom + ", already existed with form " + form + " for drug, " + drug + ". You can update it using uri http://{address}:8182/drugs/update/{drugId}/.\n";
					
					} else {
						sql = "INSERT INTO doses VALUES (" + drugId + ", '" + symptom + "', '" + dosage + "', '" + form + "', '" + threeToSix + "', '" +
								sixToTen + "', '" + tenToFifteen + "', '" + fifteenToTwenty + "', '" + twentyToTwenty_nine + "')";
						db.insert(sql);
						Dose dose = new Dose();
						dose.setDrugId(String.valueOf(drugId));
						dose.setSymptom(symptom);
						dose.setDosage(dosage);
						dose.setForm(form);
						dose.setThreeToSix(threeToSix != null ? threeToSix : "");
						dose.setSixToTen(sixToTen != null ? sixToTen : "");
						dose.setTenToFifteen(tenToFifteen != null ? tenToFifteen : "");
						dose.setFifteenToTwenty(fifteenToTwenty != null ? fifteenToTwenty : "");
						dose.setTwentyToTwenty_nine(twentyToTwenty_nine != null ? twentyToTwenty_nine : "");
						Drugs.addDose(dose);
						msg += "Dose, " + dose + " added to drug, " + drug + "\n";
					}
				}
				
			} catch (SQLException se) {
				System.out.println("***SQL EXCEPTION***");
				se.printStackTrace();
			} catch (Exception e) {
				System.out.println("***EXCEPTION***");
				e.printStackTrace();
			} 
		    status = Status.SUCCESS_OK;
		}

		setStatus(status);
		return new StringRepresentation(msg, MediaType.TEXT_PLAIN);
	
    }
}


