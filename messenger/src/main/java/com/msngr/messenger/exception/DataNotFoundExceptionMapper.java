package com.msngr.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.msngr.messenger.model.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException>{

	@Override
	public Response toResponse(DataNotFoundException dfe) {
		ErrorMessage errorMessage = new ErrorMessage(404, dfe.getMessage());
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
	}

}
