package drugs;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.sql.*;

public class Drugs {
	
    private static CopyOnWriteArrayList<Drug> drugs;
    private static CopyOnWriteArrayList<Dose> doses;
    private static AtomicInteger doseId;
    
    public Drugs() {	}

    public static String toPlain() {
		String retval = "";
		int i = 1;
		for (Drug drug : drugs) retval += drug.toString() + "\n";
		return retval;
    }
    
    public static CopyOnWriteArrayList<Drug> getList() { return drugs; }
    public static CopyOnWriteArrayList<Dose> getDoseList() { return doses; }

    // Support GET one operation.
    public static Drug find(int id) {
		Drug drug = null;
		for (Drug d : drugs) {
		    if (d.getId() == id) {
				drug = d;
				break;
		    }
		}	
		return drug;
    }
    
    public static Drug find(String drugName) {
    	Drug drug = null;
    	for (Drug d : drugs) {
    		if (d.getDrug().equals(drugName)) {
    			drug = d;
    			break;
    		}
    	}
    	return drug;
    }
    
    public static Dose findDose(int id, String symptom, String form, String dosage) {
    	Dose dose = null;
    	for (Dose d : doses) {
    		if (d.getDrugId().equals(String.valueOf(id)) && d.getSymptom().equals(symptom) &&
    			d.getForm().equals(form) && d.getDosage().equals(dosage)) {
    			dose = d;
    			break;
    		}
    	}
    	return dose;
    }

    // Support POST operation.
    public static void add(int id, String drug_name) {
		int localId = id;
		Drug drug = new Drug();
		drug.setDrug(drug_name);
		drug.setId(localId);
		drugs.add(drug);
    }

    public static void addDose(Dose ds) {
    	int newId = -1;
    	
    	
    	Dose dose = new Dose();
    	if (ds.getDoseId() == null) {
    		newId = doseId.incrementAndGet();
    	} else {
    		newId = Integer.parseInt(ds.getDoseId());
    	}
    	dose.setDoseId(newId);
    	dose.setDrugId(ds.getDrugId());
    	dose.setSymptom(ds.getSymptom());
    	dose.setDosage(ds.getDosage());
    	dose.setForm(ds.getForm());
    	dose.setThreeToSix(ds.getThreeToSix());
    	dose.setSixToTen(ds.getSixToTen());
    	dose.setTenToFifteen(ds.getTenToFifteen());
    	dose.setFifteenToTwenty(ds.getFifteenToTwenty());
    	dose.setTwentyToTwenty_nine(ds.getTwentyToTwenty_nine());
    	
    	Drug drug = Drugs.find(Integer.parseInt(ds.getDrugId()));

    	drug.addDoses(dose);
    	doses.add(dose);
    }
    
    public static void initializeDatabase() {

    	if (drugs == null) {
    		
    		drugs = new CopyOnWriteArrayList<Drug>();
    		doses = new CopyOnWriteArrayList<Dose>();
    		doseId = new AtomicInteger();
			
			DrugsDosesDB db = new DrugsDosesDB();
			
			ResultSet rs = db.select("SELECT * FROM drugs");
			
			try {
				while (rs.next()) {
					Drugs.add(Integer.parseInt(rs.getString("id")), rs.getString("drug"));
				}
				
				rs = db.select("SELECT * FROM doses");
				
				Dose ds = new Dose();
				while (rs.next()) {	
					ds.setDrugId(rs.getString("id"));
					ds.setSymptom(rs.getString("symptom"));
					ds.setDosage(rs.getString("dosage"));
					ds.setForm(rs.getString("form"));
					ds.setThreeToSix(rs.getString("3to6"));
					ds.setSixToTen(rs.getString("6to10"));
					ds.setTenToFifteen(rs.getString("10to15"));
					ds.setFifteenToTwenty(rs.getString("15to20"));
					ds.setTwentyToTwenty_nine(rs.getString("20to29"));
					Drugs.addDose(ds);
				}
			} catch (NumberFormatException e) { e.printStackTrace();
			} catch (SQLException e) { e.printStackTrace(); }
    	}
    }
}
