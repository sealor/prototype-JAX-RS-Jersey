package io.github.sealor.prototype.jax.rs.jersey.application;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ejb.Singleton;

@Singleton
public class FileStorage implements Serializable {

	private static final long serialVersionUID = 1105895240054834008L;

	private Map<String, byte[]> storage = new LinkedHashMap<String, byte[]>();

	public void createOrUpdate(String filename, InputStream uploadedInputStream) {
		this.storage.put(filename, toByteArray(uploadedInputStream));
	}

	public byte[] get(String filename) {
		return this.storage.get(filename);
	}

	private static byte[] toByteArray(InputStream input) {
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[4 * 1024];
			int length;
			while ((length = input.read(buffer)) != -1) {
				output.write(buffer, 0, length);
			}
			return output.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
