package drugs;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.restlet.data.Status;
import org.restlet.data.MediaType;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class XmlOneResource extends ServerResource {
    public XmlOneResource() { }

    @Get
    public Representation toXml() {
		// Extract the drug and dose info
    	 
		String sid = (String) getRequest().getAttributes().get("id");
		String getSymptom = (String) getRequest().getAttributes().get("symptom");
		String getDosage = (String) getRequest().getAttributes().get("dosage");
		String getForm = (String) getRequest().getAttributes().get("form");
		String getWeightKg = (String) getRequest().getAttributes().get("kg");
		Boolean weight = false;
		double kg = -1;
		
		try {
			getSymptom = getSymptom != null ? decodeUrlString(getSymptom) : null;
			getDosage = getDosage != null ? decodeUrlString(getDosage) : null;
			getForm = getForm != null ? decodeUrlString(getForm) : null;
			if (getWeightKg != null) {
				kg = Double.parseDouble(getWeightKg);
				weight = true;
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} 
		
		if (sid == null) return badRequest("No ID provided\n");
	
		int id;
		try {
			    id = Integer.parseInt(sid.trim());
		}
		catch(Exception e) { return badRequest("No such ID\n"); }
		
		// Search for the Drug.
		Drug drug = Drugs.find(id);
		if (drug == null) return badRequest("No drug with ID " + id + "\n");
		
		List<Dose> doseList;
        doseList = drug.getDoses();
        
		if (getSymptom != null && getForm != null && getDosage != null) {
			Dose d = Drugs.findDose(id, getSymptom, getForm, getDosage);

			if (d == null) return badRequest("No dose with ID " + id + ", symptom " + getSymptom +
					", form " + getForm + ", dosage " + getDosage + "\n");
			doseList.clear();
			doseList.add(d);
		}
		// Generate the XML response.
		DomRepresentation dom = null;  
	    try {  
	    	dom = new DomRepresentation(MediaType.TEXT_XML);  
	        dom.setIndenting(true);
	        Document doc = dom.getDocument();  
	  
	        Element drugElement = doc.createElement("drug");  
	        doc.appendChild(drugElement);
	        
	        Element drugName, drugId, doseId, doseInfo, symptom, dosage, form, threeToSix, sixToTen, tenToFifteen, fifteenToTwenty, twentyToTwenty_nine;
	        
	       
				
			drugName = doc.createElement("name");
			drugName.appendChild(doc.createTextNode(drug.getDrug()));

			drugId = doc.createElement("drugId");
			drugElement.appendChild(drugName);
			drugId.appendChild(doc.createTextNode(String.valueOf(drug.getId())));	
			drugElement.appendChild(drugId);

			for (Dose dose : doseList) {
					
				doseInfo = doc.createElement("doseInfo");
	
				doseId = doc.createElement("doseId");	
				doseId.appendChild(doc.createTextNode(dose.getDoseId()));
				doseInfo.appendChild(doseId);
	
				symptom = doc.createElement("symptom");
				if (dose.getSymptom() != null){
					symptom.appendChild(doc.createTextNode(dose.getSymptom()));		
					doseInfo.appendChild(symptom);
				}
						
				dosage = doc.createElement("dosage");
				if (dose.getDosage() != null) {
					dosage.appendChild(doc.createTextNode(dose.getDosage()));
					doseInfo.appendChild(dosage);
				}
						
				form = doc.createElement("form");
				if (dose.getForm() != null) {
					form.appendChild(doc.createTextNode(String.valueOf(dose.getForm())));
					doseInfo.appendChild(form);
				}
						
				threeToSix = doc.createElement("threeToSix");
				if (dose.getThreeToSix() != null && (!weight || (weight && kg >= 3 && kg < 6))) {
					threeToSix.appendChild(doc.createTextNode(String.valueOf(dose.getThreeToSix())));
					doseInfo.appendChild(threeToSix);
				}
				sixToTen = doc.createElement("sixToTen");
				if (dose.getSixToTen() != null && (!weight || (weight && kg >= 6 && kg < 10))) {
					sixToTen.appendChild(doc.createTextNode(String.valueOf(dose.getSixToTen())));
					doseInfo.appendChild(sixToTen);
				}		
				tenToFifteen = doc.createElement("tenToFifteen");
				if (dose.getTenToFifteen() != null && (!weight || (weight && kg >= 10 && kg < 15))) {
					tenToFifteen.appendChild(doc.createTextNode(String.valueOf(dose.getTenToFifteen())));
					doseInfo.appendChild(tenToFifteen);
				}		
				fifteenToTwenty = doc.createElement("fifteenToTwenty");
				if (dose.getFifteenToTwenty() != null && (!weight || (weight && kg >= 15 && kg < 20))) {
					fifteenToTwenty.appendChild(doc.createTextNode(String.valueOf(dose.getFifteenToTwenty())));
					doseInfo.appendChild(fifteenToTwenty);
				}
				twentyToTwenty_nine = doc.createElement("twentyToTwenty_nine");
				if (dose.getTwentyToTwenty_nine() != null && (!weight || (weight && kg >= 20 && kg < 29))) {
					twentyToTwenty_nine.appendChild(doc.createTextNode(String.valueOf(dose.getTwentyToTwenty_nine())));
					doseInfo.appendChild(twentyToTwenty_nine);
				}
							
			    drugElement.appendChild(doseInfo);
	
			}	// End of dose loop
		}
		catch(Exception e) { }
		return dom;
    }
	
    private String decodeUrlString(String url) throws UnsupportedEncodingException {
        return URLDecoder.decode( url, "UTF-8" );
    }
	private StringRepresentation badRequest(String msg) {
		Status error = new Status(Status.CLIENT_ERROR_BAD_REQUEST, msg);
		return new StringRepresentation(error.toString());
	}
}


