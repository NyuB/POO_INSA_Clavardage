package org.clav.network;

/**
 * Abstract class, intented to be implemented for each agent component actions requiring network functionality
 */
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
