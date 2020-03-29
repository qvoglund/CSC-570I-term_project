package drugs;

public class Dose {
    private String drugId;
    private String doseId;
    private String dosage;
    private String form;
    private String symptom;
	private String threeToSix;
    private String sixToTen;
    private String tenToFifteen;
    private String fifteenToTwenty;
    private String twentyToTwenty_nine;
    

    public Dose() { }
    
    // overrides
    @Override
    public String toString() {
    	return "id: " + drugId + " form: " + form + " symptom: " + symptom + "\n";
    }
    
    // properties
    public void setDrugId(String id) { this.drugId = id; }
    public String geDrugId() { return this.drugId; }
    
    public void setDoseId(int doseId) { this.doseId = String.valueOf(doseId); }
    public String getDoseId() { return this.doseId; }

	public String getDosage() {return dosage; }
	public void setDosage(String dosage) { this.dosage = dosage; }

	public String getForm() { return form; }
	public void setForm(String form) { this.form = form; }

	public String getThreeToSix() { return threeToSix;}
	public void setThreeToSix(String threeToSix) { this.threeToSix = threeToSix;}

	public String getSixToTen() { return sixToTen;}
	public void setSixToTen(String sixToTen) { this.sixToTen = sixToTen; }

	public String getTenToFifteen() { return tenToFifteen; }
	public void setTenToFifteen(String tenToFifteen) { this.tenToFifteen = tenToFifteen; }

	public String getFifteenToTwenty() { return fifteenToTwenty; }
	public void setFifteenToTwenty(String fifteenToTwenty) { this.fifteenToTwenty = fifteenToTwenty; }

	public String getTwentyToTwenty_nine() { return twentyToTwenty_nine; }
	public void setTwentyToTwenty_nine(String twentyToTwenty_nine) { this.twentyToTwenty_nine = twentyToTwenty_nine; }
	
	public String getSymptom() { return symptom; }
	public void setSymptom(String symptom) { this.symptom = symptom; }
}
