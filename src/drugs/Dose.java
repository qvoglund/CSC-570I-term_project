package drugs;

public class Dose {
    private String drugId;
    private String doseId;
    private String dosage;
    private String form;
    private String symptom;
	private String threeToSix = "";
    private String sixToTen = "";
    private String tenToFifteen = "";
    private String fifteenToTwenty = "";
    private String twentyToTwenty_nine = "";
    

    public Dose() { }
    
    // overrides
    @Override
    public String toString() {
    	return "id: " + drugId + ", form: " + form + ", symptom: " + symptom + ", dosage: " + dosage + ", 3to6: " + threeToSix +
    			", 6to10: " + sixToTen + ", 10to15: " + tenToFifteen + ", 15to20: " + fifteenToTwenty +
    			", 20to29: " + twentyToTwenty_nine + "\n";
    }
    
    // properties
    public void setDrugId(String id) { this.drugId = id; }
    public String getDrugId() { return this.drugId; }
    
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

	public void clone(Dose dose) {
		this.drugId = dose.getDrugId();
	    this.doseId = dose.getDoseId();
	    this.dosage = dose.getDosage();
	    this.form = dose.getForm();
	    this.symptom = dose.getSymptom();
	    this.threeToSix = dose.getThreeToSix();
	    this.sixToTen = dose.getSixToTen();
	    this.tenToFifteen = dose.getTenToFifteen();
	    this.fifteenToTwenty = dose.getFifteenToTwenty();
	    this.twentyToTwenty_nine = dose.getTwentyToTwenty_nine();
	}
	
	@Override
	public boolean equals(Object dose) {
		return this.drugId.equals(((Dose) dose).getDrugId()) &&
		this.doseId.equals(((Dose) dose).getDoseId()) &&
	    this.dosage.equals(((Dose) dose).getDosage()) &&
	    this.form.equals(((Dose) dose).getForm()) &&
	    this.symptom.equals(((Dose) dose).getSymptom()) &&
	    this.threeToSix.equals(((Dose) dose).getThreeToSix()) &&
	    this.sixToTen.equals(((Dose) dose).getSixToTen()) &&
	    this.tenToFifteen.equals(((Dose) dose).getTenToFifteen()) &&
	    this.fifteenToTwenty.equals(((Dose) dose).getFifteenToTwenty()) &&
	    this.twentyToTwenty_nine.equals(((Dose) dose).getTwentyToTwenty_nine());
	}
}
