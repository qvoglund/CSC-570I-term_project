package drugs;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import org.restlet.data.Status;
import org.restlet.data.MediaType;
import java.util.List;

public class XmlAllResource extends ServerResource {
    public XmlAllResource() { }

    @Get
    public Representation toXml() {
		List<Drug> list = Drugs.getList();

		DomRepresentation dom = null;  
	    try {  
	    	dom = new DomRepresentation(MediaType.TEXT_XML);  
	        dom.setIndenting(true);
	        Document doc = dom.getDocument();  
	  
	        Element root = doc.createElement("drugs"); 
	        doc.appendChild(root);
	        Element drugElement, drugName, id, doseId, doseInfo, symptom, dosage, form, threeToSix, sixToTen, tenToFifteen, fifteenToTwenty, twentyToTwenty_nine;
	        
	        List<Dose> doseList;
	            
		    for (Drug drug : list) {
		    	
		    	doseList = drug.getDoses();
		    	drugElement = doc.createElement("drug");
				root.appendChild(drugElement);
				
				drugName = doc.createElement("name");
				drugName.appendChild(doc.createTextNode(drug.getDrug()));

				id = doc.createElement("id");
				drugElement.appendChild(drugName);
				id.appendChild(doc.createTextNode(String.valueOf(drug.getId())));	
				drugElement.appendChild(id);

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

		    }	// End of drug loop
		    
	    } catch(Exception e) { }
	        
		return dom;
    }
}


