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
	/*
	Restlet janitor = new Restlet(getContext()) {  
		public void handle(Request request, Response response) {  
		    String msg = null;

		    String sid = (String) request.getAttributes().get("id");
		    if (sid == null) msg = badRequest("No ID given.\n");

		    Integer id = null;
		    try { 
			id = Integer.parseInt(sid.trim());
		    }
		    catch(Exception e) { msg = badRequest("Ill-formed ID.\n"); }

		    Adage adage = Adages.find(id);
		    if (adage == null) 
			msg = badRequest("No adage with ID " + id + "\n");
		    else {
			Adages.getList().remove(adage);
			msg = "Adage " + id + " removed.\n";
		    }
		    
		    // Generate HTTP response.
		    response.setEntity(msg, MediaType.TEXT_PLAIN);  
		}  
	    };  
	*/
	// Create the routing table.
	Router router = new Router(getContext());
    router.attach("/",            PlainResource.class);
	router.attach("/xml",         XmlAllResource.class);
	router.attach("/xml/{id}",    XmlOneResource.class);
	router.attach("/json",        JsonAllResource.class);
	router.attach("/json/{id}",   JsonOneResource.class);
	router.attach("/create",      CreateResource.class);
	router.attach("/update", UpdateResource.class);
	router.attach("/delete/{id}", DeleteResource.class);
	
        return router;
    }
    
    private String badRequest(String msg) {
	Status error = new Status(Status.CLIENT_ERROR_BAD_REQUEST, msg);
	return error.toString();
    }

}   
