package drugs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.data.Status;
import org.restlet.data.MediaType;
import java.util.List;

public class JsonOneResource extends ServerResource {
    public JsonOneResource() { }

    @Get
    public Representation toJson() {
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
        List<Dose> doseList;

    	if (drug == null) return badRequest("No drug with ID " + id + "\n");
    	
		// Generate the JSON representation.
		JsonRepresentation json = null;
		try {
			
			JSONArray drugArr = new JSONArray();
			JSONObject drugObj = new JSONObject();
			JSONArray doseArr = new JSONArray();
			JSONObject doseObj = new JSONObject();
			
			
			drugObj = new JSONObject();
	
				
			doseList = drug.getDoses();
			drugObj.append("drugId", drug.getId());
			drugObj.append("drugName", drug.getDrug());
				
			for (Dose dose : doseList) {
				doseObj = new JSONObject();
				doseArr = new JSONArray();
					
				doseObj.append("doseId", dose.getDoseId());
				doseObj.append("dosage", dose.getDosage());
				doseObj.append("form", dose.getForm());
				doseObj.append("symptom", dose.getSymptom());
				doseObj.append("threeToSix", dose.getThreeToSix());
				doseObj.append("sixToTen", dose.getSixToTen());
				doseObj.append("tenToFifteen", dose.getTenToFifteen());
				doseObj.append("fifteenToTwenty", dose.getFifteenToTwenty());
				doseObj.append("twentyToTwenty_nine", dose.getTwentyToTwenty_nine());
	
					
				doseArr.put(doseObj);
				drugObj.append("doses", doseArr);
	
			}
				
				
			drugArr.put(drugObj);
			
			json = new JsonRepresentation(new StringRepresentation(list.toString()));
		    json = new JsonRepresentation(drugArr);
		}
	        catch(Exception e) { }
		return json;
    }
    
    private StringRepresentation badRequest(String msg) {
		Status error = new Status(Status.CLIENT_ERROR_BAD_REQUEST, msg);
		return new StringRepresentation(error.toString());
	}
}


