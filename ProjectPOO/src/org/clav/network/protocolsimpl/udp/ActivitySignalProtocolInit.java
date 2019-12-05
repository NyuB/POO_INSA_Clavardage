package org.clav.network.protocolsimpl.udp;

import org.clav.network.NetworkManager;
import org.clav.network.ProtocolInit;
import org.clav.user.UserManager;

public class ActivitySignalProtocolInit extends ProtocolInit {
	private UserManager userManager;

	public ActivitySignalProtocolInit(NetworkManager networkManager, UserManager userManager) {
		super(networkManager);
		this.userManager = userManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}
}
