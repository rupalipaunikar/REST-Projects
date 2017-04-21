package com.msngr.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.msngr.messenger.model.Message;
import com.msngr.messenger.resources.beans.MessageFilterBean;
import com.msngr.messenger.service.MessageService;

@Path("/messages")
//@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

	private MessageService messageService = new MessageService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJSONMessages(@BeanParam MessageFilterBean messageFilterBean){
		System.out.println("Json messages called");
		
		int year = messageFilterBean.getYear();
		int start = messageFilterBean.getStart();
		int size = messageFilterBean.getSize();
		
		if(year > 0){
			return messageService.getMessagesForYear(year);
		}
		if(start >= 0 && size > 0){
			return messageService.getAllMessagesPaginated(start, size);
		}
		return messageService.getAllMessages();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Message> getXMLMessages(@BeanParam MessageFilterBean messageFilterBean){
		System.out.println("XML messages called");
		
		int year = messageFilterBean.getYear();
		int start = messageFilterBean.getStart();
		int size = messageFilterBean.getSize();
		
		if(year > 0){
			return messageService.getMessagesForYear(year);
		}
		if(start >= 0 && size > 0){
			return messageService.getAllMessagesPaginated(start, size);
		}
		return messageService.getAllMessages();
	}
	
	@Path("/{messageId}")
	@GET
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo){
		Message message = messageService.getMessage(id);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");
		return message;
	}

	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(newMessage).build();
		
//		return Response.status(Status.CREATED).entity(newMessage).build();
	}
	
	@Path("/{messageId}")
	@PUT
	public Message updateMessage(@PathParam("messageId") long id, Message message){
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@Path("/{messageId}")
	@DELETE
	public void deleteMessage(@PathParam("messageId") long id){
		messageService.removeMessage(id);
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource(){
		return new CommentResource();
	}
	
	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String link = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(String.valueOf(message.getId()))
				.build()
				.toString();
		return link;
	}
	
	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String link = uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path(String.valueOf(message.getAuthor()))
				.build()
				.toString();
		return link;
	}
	
	private String getUriForComments(UriInfo uriInfo, Message message) {
		String link = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(MessageResource.class, "getCommentResource")
//				.path(CommentResource.class)
				.resolveTemplate("messageId", message.getId())
				.build()
				.toString();
		return link;
	}
}
