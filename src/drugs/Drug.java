package drugs;

import java.util.*;

public class Drug {
    private int id;
    private String drug;
    private List<Dose> doses = new ArrayList<Dose>();

    public Drug() { }
    
    // overrides
    @Override
    public String toString() {
	return "id: " + id + " drug: " + drug + " doses: " + doses;
    }
    
    // properties
    public void setDrug(String drug) { this.drug = drug; }
    public String getDrug() { return this.drug; }
    
    public void setId(int id) { this.id = id; }
    public int getId() { return this.id; }

	public List<Dose> getDoses() { return doses; }
	public void setDoses(List<Dose> doses) { this.doses = doses; }
	public void addDoses(List<Dose> doses) { this.doses.addAll(doses); }
	public void addDoses(Dose dose) { this.doses.add(dose); }
    
    
}
