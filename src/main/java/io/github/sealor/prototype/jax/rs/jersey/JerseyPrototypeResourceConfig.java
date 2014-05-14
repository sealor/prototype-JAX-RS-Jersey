package io.github.sealor.prototype.jax.rs.jersey;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class JerseyPrototypeResourceConfig extends ResourceConfig {

	public JerseyPrototypeResourceConfig() {
		super();

		packages(this.getClass().getPackage().getName());
		register(MultiPartFeature.class);
	}
}
