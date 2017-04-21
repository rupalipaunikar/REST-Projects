package com.msngr.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Its a sub resource
 * @author rupalip
 *
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class CommentResource {

	@GET
	public String getAllComments(@PathParam("messageId") long messageId){
		return "Returned all comments for message : "+messageId;
	}
	
	@Path("/{commentId}")
	@GET
	public String getComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId){
		return "Returned comment : "+commentId+" for message : "+messageId;
	}
}
