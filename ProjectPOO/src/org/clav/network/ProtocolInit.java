package org.clav.network;

import org.clav.network.NetworkManager;

public class ProtocolInit {
	private NetworkManager networkManager;

	public NetworkManager getNetworkManager() {
		return networkManager;
	}

	public ProtocolInit(NetworkManager networkManager) {
		this.networkManager = networkManager;
	}
}
