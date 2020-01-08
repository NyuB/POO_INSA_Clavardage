package org.clav.network.server;

import org.clav.user.User;

import java.io.Serializable;

public class ServerPublication implements Serializable {
	private User user;

	public ServerPublication(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
