package org.clav.network;

public abstract class Protocol implements Runnable {
	private ProtocolInit protocolInit;

	public Protocol(ProtocolInit protocolInit) {
		this.protocolInit = protocolInit;
	}

	public ProtocolInit getProtocolInit() {
		return protocolInit;
	}

	public NetworkManager getRelatedNetworkManager() {
		return getProtocolInit().getNetworkManager();
	}
}
