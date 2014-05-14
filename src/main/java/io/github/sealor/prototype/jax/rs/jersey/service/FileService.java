package io.github.sealor.prototype.jax.rs.jersey.service;

import io.github.sealor.prototype.jax.rs.jersey.application.FileStorage;

import java.io.InputStream;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("file")
public class FileService {

	@EJB
	private FileStorage fileStorage;

	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void upload(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		this.fileStorage.createOrUpdate(fileDetail.getFileName(), uploadedInputStream);
	}

	@GET
	@Path("download/{filename}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response download(@PathParam("filename") String filename) {
		return Response.ok().entity(this.fileStorage.get(filename)).build();
	}
}
