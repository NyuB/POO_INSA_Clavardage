package org.clav.network.server;

import org.clav.user.User;

import java.io.Serializable;
import java.net.InetAddress;

public class ServerNotification implements Serializable {
	private User user;
	private InetAddress address;

	public ServerNotification(User user, InetAddress address) {
		this.user = user;
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public InetAddress getAddress() {
		return address;
	}
}
