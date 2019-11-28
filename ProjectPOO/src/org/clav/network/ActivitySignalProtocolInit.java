package org.clav.network;

import org.clav.user.UserManager;

public class ActivitySignalProtocolInit extends ProtocolInit{
	private UserManager userManager;

	public ActivitySignalProtocolInit(NetworkManager networkManager, UserManager userManager) {
		super(networkManager);
		this.userManager = userManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}
}
