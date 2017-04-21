package com.msngr.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

	@GET
	@Path("annotations")
	public String demoAnnotations(@MatrixParam("param") String param,
								 @HeaderParam("headerKey") String headerKey){
		return "Param : "+param +"\nHeaderKey : "+headerKey;
	}
	
	@GET
	@Path("context")
	public String demoContext(@Context UriInfo uriInfo, @Context HttpHeaders headers){
		return "Path : "+uriInfo.getAbsolutePath().toString() +
			   "\nMediaType : "+headers.getMediaType();
	}
}
