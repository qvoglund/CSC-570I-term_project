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
import java.util.List;

public class XmlOneResource extends ServerResource {
    public XmlOneResource() { }

    @Get
    public Representation toXml() {
		// Extract the adage's id.
		String sid = (String) getRequest().getAttributes().get("id");
		if (sid == null) return badRequest("No ID provided\n");
	
		int id;
		try {
			    id = Integer.parseInt(sid.trim());
			}
			catch(Exception e) { return badRequest("No such ID\n"); }
		
		// Search for the Adage.
		List<Drug> list = Drugs.getList();
		Drug drug = Drugs.find(id);
		if (drug == null) return badRequest("No drug with ID " + id + "\n");
	
		// Generate the XML response.
		DomRepresentation dom = null;  
	    try {  
	    	dom = new DomRepresentation(MediaType.TEXT_XML);  
	        dom.setIndenting(true);
	        Document doc = dom.getDocument();  
	  
	        Element drugElement = doc.createElement("drug");  
	        doc.appendChild(drugElement);
	        
	        Element drugName, drugId, doseId, doseInfo, symptom, dosage, form, threeToSix, sixToTen, tenToFifteen, fifteenToTwenty, twentyToTwenty_nine;
	        
	        List<Dose> doseList;
	            
	        doseList = drug.getDoses();
				
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
				if (dose.getSymptom() != null)
					symptom.appendChild(doc.createTextNode(dose.getSymptom()));
					
				doseInfo.appendChild(symptom);
					
				dosage = doc.createElement("dosage");
				if (dose.getDosage() != null) 
					dosage.appendChild(doc.createTextNode(dose.getDosage()));
					
				doseInfo.appendChild(dosage);
					
				form = doc.createElement("form");
				if (dose.getForm() != null) 
					form.appendChild(doc.createTextNode(String.valueOf(dose.getForm())));

				doseInfo.appendChild(form);
					
				threeToSix = doc.createElement("threeToSix");
				if (dose.getThreeToSix() != null)
					threeToSix.appendChild(doc.createTextNode(String.valueOf(dose.getThreeToSix())));

				doseInfo.appendChild(threeToSix);
					
				sixToTen = doc.createElement("sixToTen");
				if (dose.getSixToTen() != null)
					sixToTen.appendChild(doc.createTextNode(String.valueOf(dose.getSixToTen())));

				doseInfo.appendChild(sixToTen);
					
				tenToFifteen = doc.createElement("tenToFifteen");
				if (dose.getTenToFifteen() != null)
					tenToFifteen.appendChild(doc.createTextNode(String.valueOf(dose.getTenToFifteen())));

				doseInfo.appendChild(tenToFifteen);
					
				fifteenToTwenty = doc.createElement("fifteenToTwenty");
				if (dose.getFifteenToTwenty() != null)
					fifteenToTwenty.appendChild(doc.createTextNode(String.valueOf(dose.getFifteenToTwenty())));

				doseInfo.appendChild(fifteenToTwenty);
					
				twentyToTwenty_nine = doc.createElement("twentyToTwenty_nine");
				if (dose.getTwentyToTwenty_nine() != null)
					twentyToTwenty_nine.appendChild(doc.createTextNode(String.valueOf(dose.getTwentyToTwenty_nine())));

				doseInfo.appendChild(twentyToTwenty_nine);
						
			    drugElement.appendChild(doseInfo);

			}	// End of dose loop

	    }
		catch(Exception e) { }
		return dom;
    }
	
	private StringRepresentation badRequest(String msg) {
		Status error = new Status(Status.CLIENT_ERROR_BAD_REQUEST, msg);
		return new StringRepresentation(error.toString());
	}
}


