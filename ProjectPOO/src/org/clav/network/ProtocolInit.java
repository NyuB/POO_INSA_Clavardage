package org.clav.network;

public abstract class ProtocolInit {
	private NetworkManager networkManager;

	public NetworkManager getNetworkManager() {
		return networkManager;
	}

	public ProtocolInit(NetworkManager networkManager) {
		this.networkManager = networkManager;
	}
}
