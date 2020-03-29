package drugs;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.sql.*;

public class Drugs {
	
    private static CopyOnWriteArrayList<Drug> drugs;
    private static AtomicInteger doseId;
    public Drugs() {	}

    public static String toPlain() {
		String retval = "";
		int i = 1;
		for (Drug drug : drugs) retval += drug.toString() + "\n";
		return retval;
    }
    
    public static CopyOnWriteArrayList<Drug> getList() { return drugs; }

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

    // Support POST operation.
    public static void add(int id, String drug_name) {
		int localId = id;
		Drug drug = new Drug();
		drug.setDrug(drug_name);
		drug.setId(localId);
		drugs.add(drug);
    }

    public static void addDose(String id, String symptom, String dosage, String form, String threeToSix, String sixToTen, String tenToFifteen,
    		String fifteenToTwenty, String twentyToTwenty_nine) {
    	
    	int newId = doseId.incrementAndGet();
    	
    	Dose dose = new Dose();
    	dose.setDrugId(id);
    	dose.setDoseId(newId);
    	dose.setSymptom(symptom);
    	dose.setDosage(dosage);
    	dose.setForm(form);
    	dose.setThreeToSix(threeToSix);
    	dose.setSixToTen(sixToTen);
    	dose.setTenToFifteen(tenToFifteen);
    	dose.setFifteenToTwenty(fifteenToTwenty);
    	dose.setTwentyToTwenty_nine(twentyToTwenty_nine);
    	
    	Drug drug = Drugs.find(Integer.parseInt(id));
    	System.out.println("DRUG: " + drug + " DOSE: " + dose + "\n");
    	drug.addDoses(dose);
    }
    
    public static void initializeDatabase() {

    	if (drugs == null) {
    		
    		drugs = new CopyOnWriteArrayList<Drug>();
    		doseId = new AtomicInteger();
	    	final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
			final String DB_URL = "jdbc:mariadb://localhost:3306/drug_dosage";
		
			final String USER = "root";
			final String PASS = "Spaceghost9";
		
			Connection conn = null;
			Statement stmt = null;
			
			try {
				Class.forName(JDBC_DRIVER);
				System.out.println("INIT Database Connection\n");
				System.out.println("test"+DriverManager.getConnection(DB_URL, USER, PASS));
				conn = DriverManager.getConnection(DB_URL, USER, PASS);//
				System.out.println("CONNECTED to Database: " + DB_URL + "\n");
				stmt = conn.createStatement();
		
				String sql = "SELECT * FROM drugs";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					//System.out.println("Adding: id '" + rs.getString("id") + "' drug '" + rs.getString("drug") + "'\n");
					add(Integer.parseInt(rs.getString("id")), rs.getString("drug"));
				}
				
				sql = "SELECT * FROM doses";
				rs = stmt.executeQuery(sql);
				
				ResultSetMetaData rsmd = rs.getMetaData();
				while (rs.next()) {
					//System.out.println("Adding: id '" + rs.getString("id") + "' ");
					
					for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
						//System.out.print(rsmd.getColumnName(i) + ": " + rs.getString(i) + " ");	
					}
					System.out.println();
					addDose(rs.getString("id"), rs.getString("symptom"), rs.getString("dosage"), rs.getString("form"), rs.getString("3to6"),
							rs.getString("6to10"), rs.getString("10to15"), rs.getString("15to20"), rs.getString("20to29"));
				}
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
}
