package org.clav.network.server;
import java.io.Serializable;

public class ServerSubcription implements Serializable {
	private String id;

	public String getId() {
		return id;
	}

	public ServerSubcription(String id) {
		this.id = id;
	}
}
