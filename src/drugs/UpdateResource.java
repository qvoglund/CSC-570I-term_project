package drugs;

import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.data.Status;
import org.restlet.data.MediaType;
import org.restlet.data.Form;

public class UpdateResource extends ServerResource {
    public UpdateResource() { }

    @Put
    public Representation update(Representation data) {
	Status status = null;
	String msg = null;

	// Extract the data from the POST body.
	Form form = new Form(data);
	String sid = form.getFirstValue("id");
	String drug_name = form.getFirstValue("drug");

	if (sid == null || drug_name == null) {
	    msg = "An ID and drug name must be provided.\n";
	    status = Status.CLIENT_ERROR_BAD_REQUEST;
	}
	else {
	    int id = Integer.parseInt(sid.trim());
	    Drug drug = Drugs.find(id);
	    if (drug == null) {
		msg = "There is no drug with ID " + id + "\n";
		status = Status.CLIENT_ERROR_BAD_REQUEST;
	    }
	    else {
		drug.setDrug(drug_name);
		msg = "Drug " + id + " has been updated to '" + drug + "'.\n";
		status = Status.SUCCESS_OK;
	    }
	}

	setStatus(status);
	return new StringRepresentation(msg, MediaType.TEXT_PLAIN);
    }
}


