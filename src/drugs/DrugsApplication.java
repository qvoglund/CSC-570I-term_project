package drugs;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.routing.Router;
import org.restlet.data.Status;
import org.restlet.data.MediaType;

public class DrugsApplication extends Application {
    @Override
    public synchronized Restlet createInboundRoot() {
	
		// Create the routing table.
		Router router = new Router(getContext());
	    router.attach("/",            								PlainResource.class);	// Plain text of drugs/dosages
		router.attach("/xml",         								XmlAllResource.class);	// Xml
		router.attach("/xml/{id}",    								XmlOneResource.class);	// Xml of one drug with it's dosages
		router.attach("/xml/{id}/{symptom}/{dosage}/{form}",    	XmlOneResource.class);	// Xml of one drug and one dosage
		router.attach("/xml/{id}/{symptom}/{dosage}/{form}/{kg}",   XmlOneResource.class);	// Xml of one drug and one dosage for a specific weight
		router.attach("/json",        								JsonAllResource.class);	// Json
		router.attach("/json/{id}",   								JsonOneResource.class);	// Json of one drug and it's dosages
		router.attach("/create",      								CreateResource.class);	// Create a drug along with or without a dosage
		router.attach("/update", 									UpdateResource.class);	// Update a drug or dose information
		router.attach("/delete/{id}", 								DeleteResource.class);	// Delete a drug along with all of it's dosages
		
        return router;
    }
    
    private String badRequest(String msg) {
		Status error = new Status(Status.CLIENT_ERROR_BAD_REQUEST, msg);
		return error.toString();
    }

}   
