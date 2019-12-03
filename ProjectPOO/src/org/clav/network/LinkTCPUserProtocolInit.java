package org.clav.network;

import java.net.Socket;

public class LinkTCPUserProtocolInit extends ProtocolInit {
	public enum Mode{
		CONNECT,
		ACCEPT;
	}
	private Socket distant;
	private Mode mode;
	private String distantID;

	public LinkTCPUserProtocolInit(NetworkManager networkManager, Socket distant,Mode mode) {
		super(networkManager);
		this.distant = distant;

		this.mode = mode;
	}

	public LinkTCPUserProtocolInit(NetworkManager networkManager, Socket distant, Mode mode, String distantID) {
		super(networkManager);
		this.distant = distant;
		this.mode = mode;
		this.distantID = distantID;
	}

	public Mode getMode() {
		return mode;
	}

	public Socket getDistant() {
		return distant;
	}

	public String getDistantID() {
		return distantID;
	}
}
