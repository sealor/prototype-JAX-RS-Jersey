package io.github.sealor.prototype.jax.rs.jersey;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;
import org.junit.Test;

public class UploadAndDownloadIT {

	@Test
	public void test() throws IOException {
		final String URL = "http://localhost:8080/prototype-JAX-RS-Jersey";

		Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();

		StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart();
		streamDataBodyPart.setName("file");
		streamDataBodyPart.setFilename("filename.txt");
		streamDataBodyPart.setStreamEntity(new ByteArrayInputStream(new byte[] { 0, 1, 2, 3 }));

		FormDataMultiPart multipart = new FormDataMultiPart();
		multipart.bodyPart(streamDataBodyPart);

		WebTarget uploadTarget = client.target(URL + "/file/upload");
		Response uploadResponse = uploadTarget.request().post(Entity.entity(multipart, multipart.getMediaType()));
		assertEquals(204, uploadResponse.getStatus());

		WebTarget downloadTarget = client.target(URL + "/file/download/filename.txt");
		Response downloadResponse = downloadTarget.request().get(Response.class);
		assertEquals(200, downloadResponse.getStatus());

		DataInputStream entityStream = new DataInputStream(downloadResponse.readEntity(InputStream.class));
		byte[] filecontent = new byte[downloadResponse.getLength()];
		entityStream.readFully(filecontent);
		assertArrayEquals(new byte[] { 0, 1, 2, 3 }, filecontent);
	}
}
