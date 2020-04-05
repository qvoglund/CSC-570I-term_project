package drugs;

import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.data.Status;
import org.restlet.data.MediaType;
import org.restlet.data.Form;

public class UpdateResource extends ServerResource {
    public UpdateResource() { }

    @Put
    public Representation update(Representation data) {
		Status status = null;
		String msg = null;
	
		// Extract the data from the POST body.
		Form fr = new Form(data);
		String sid = fr.getFirstValue("id");
	    int id = Integer.parseInt(sid.trim());
		String drug_name = fr.getFirstValue("drug");
		String symptom = fr.getFirstValue("symptom");
		String newSymptom = fr.getFirstValue("newSymptom");
		String dosage = fr.getFirstValue("dosage");
		String newDosage = fr.getFirstValue("newDosage");
		String form = fr.getFirstValue("form");
		String newForm = fr.getFirstValue("newForm");
		String threeToSix = fr.getFirstValue("3to6");
		String sixToTen = fr.getFirstValue("6to10");
		String tenToFifteen = fr.getFirstValue("10to15");
		String fifteenToTwenty = fr.getFirstValue("15to20");
		String twentyToTwenty_nine = fr.getFirstValue("20to29");
		DrugsDosesDB db = new DrugsDosesDB();
		
		if (symptom == null && form == null && dosage == null) {
			Drug drug = Drugs.find(id);
		    if (drug == null) {
				msg = "There is no drug with ID " + id + "\n";
				status = Status.CLIENT_ERROR_BAD_REQUEST;
		    }
		    else {
		    	String sql = "UPDATE drugs SET drug = '" + drug_name + "' WHERE id = " + id;
		    	db.insert(sql);
				drug.setDrug(drug_name);
				msg = "Drug " + id + " has been updated to '" + drug + "'.\n";
				status = Status.SUCCESS_OK;
		    }
		}
		else if (symptom == null || form == null || dosage == null) {
			msg = "A symptom, form, and dosage must be provided.\n";
			status = Status.CLIENT_ERROR_BAD_REQUEST;
		}
		else {
			Dose dose = Drugs.findDose(id, symptom, form, dosage);
			if (dose == null) {
				msg = "There is no dose with ID " + id + ", symptom " + symptom + ", form " +
						form + ", and dosage " + dosage + "\n";
				status = Status.CLIENT_ERROR_BAD_REQUEST;
			}
			else {
				Dose newDose = new Dose();
				newDose.clone(dose);

				String drugId = dose.getDrugId();
				newDose.setDrugId(drugId);
				newDose.setSymptom(newSymptom == null ? symptom : newSymptom);
				newDose.setDosage(newDosage == null ? dosage : newDosage);
				newDose.setForm(newForm == null ? form : newForm);
				newDose.setThreeToSix(threeToSix == null ? dose.getThreeToSix() : threeToSix);
				newDose.setSixToTen(sixToTen == null ? dose.getSixToTen() : sixToTen);
				newDose.setTenToFifteen(tenToFifteen == null ? dose.getTenToFifteen() : tenToFifteen);
				newDose.setFifteenToTwenty(fifteenToTwenty == null ? dose.getFifteenToTwenty() : fifteenToTwenty);
				newDose.setTwentyToTwenty_nine(twentyToTwenty_nine == null ? dose.getTwentyToTwenty_nine() : twentyToTwenty_nine);

				if (!Drugs.getDoseList().contains(newDose)) {

					String sql = "UPDATE doses SET dosage = '" + newDose.getDosage() + "', form = '" + newDose.getForm() + "', symptom = '" + newDose.getSymptom() + "', 3to6 = '" + newDose.getThreeToSix() + "', 6to10 = '" + newDose.getSixToTen() +
							"', 10to15 = '" + newDose.getTenToFifteen() + "', 15to20 = '" + newDose.getFifteenToTwenty() + "', 20to29 = '" + newDose.getTwentyToTwenty_nine() + "' WHERE id = " + drugId + " AND " +
									"symptom = '" + symptom + "' AND dosage = '" + dosage + "' AND form = '" + form + "'";
					db.insert(sql);
						
					Drug drug = Drugs.find(Integer.parseInt(newDose.getDrugId()));
					Drugs.addDose(newDose);

					msg = "Dose with drugId = " + drugId + ", symptom = " + newDose.getSymptom() + 
							", form = " + newDose.getForm() +" updated. " + (threeToSix != null ? " 3to6 = " + threeToSix : "") +
							(sixToTen != null ? " 6to10 = " + sixToTen : "") +
							(tenToFifteen != null ? " 10to15 = " + tenToFifteen : "") +
							(fifteenToTwenty != null ? " 15to20 = " + fifteenToTwenty : "") +
							(twentyToTwenty_nine != null ? " 20to29 = " + twentyToTwenty_nine : "") + "\n";
				} else {
					msg = "Dose information already exists.\n";
				}
						
			}			
		}
	
		setStatus(status);
		return new StringRepresentation(msg, MediaType.TEXT_PLAIN);
    }
    
}


