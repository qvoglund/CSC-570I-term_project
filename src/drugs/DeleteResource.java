package drugs;

import org.restlet.resource.Delete;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.data.Status;
import org.restlet.data.Form;
import org.restlet.data.MediaType;

public class DeleteResource extends ServerResource {
    public DeleteResource() {}
	
	@Delete
	public Representation delete(Representation data){  	
		Status status = null;
		String msg = null;
		Form form = new Form(data);

	 	String sid = (String) form.getFirstValue("id");
		
		if (sid == null) {
			msg = "No ID given.\n";
			status = Status.CLIENT_ERROR_BAD_REQUEST;
		}
		    
		Integer id = null;
		try { 
			id = Integer.parseInt(sid.trim());
		}
		catch(Exception e) { 
			msg = "Ill-formed ID.\n"; 
			status = Status.CLIENT_ERROR_BAD_REQUEST;
		}

		Drug drug = Drugs.find(id);
		if (drug == null) 
			msg = "No adage with ID " + id + "\n";
		else {
			Drugs.getList().remove(drug);
			msg = "Drug " + id + " removed.\n";
		}
		setStatus(status);
		return new StringRepresentation(msg, MediaType.TEXT_PLAIN);	    
	} 
	
    
}   
