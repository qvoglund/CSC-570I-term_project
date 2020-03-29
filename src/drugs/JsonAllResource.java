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

public class JsonAllResource extends ServerResource {
    public JsonAllResource() { }

    @Get
    public Representation toJson() {
    	List<Drug> list = Drugs.getList();
    	List<Dose> doseList;

	// Generate the JSON representation.
	JsonRepresentation json = null;
	try {
		JSONArray drugArr = new JSONArray();
		JSONObject drugObj = new JSONObject();
		JSONArray doseArr = new JSONArray();
		JSONObject doseObj = new JSONObject();
		
		for (Drug drug : list) {
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
		}
	    json = new JsonRepresentation(new StringRepresentation(list.toString()));
	    json = new JsonRepresentation(drugArr);
	}
        catch(Exception e) { }
	return json;
    }
}


