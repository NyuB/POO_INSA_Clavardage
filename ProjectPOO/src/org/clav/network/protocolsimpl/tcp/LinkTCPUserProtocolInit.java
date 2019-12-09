package org.clav.network.protocolsimpl.tcp;

import org.clav.network.NetworkManager;
import org.clav.network.TCPUserLink;
import org.clav.network.ProtocolInit;

import java.net.Socket;

public class LinkTCPUserProtocolInit extends ProtocolInit {
	public enum Mode{
		CONNECT,
		ACCEPT;
	}
	private TCPUserLink link;
	private Mode mode;
	private String distantID;

	public LinkTCPUserProtocolInit(NetworkManager networkManager, TCPUserLink link, Mode mode) {
		super(networkManager);
		this.link = link;
		this.mode = mode;
	}

	public LinkTCPUserProtocolInit(NetworkManager networkManager, TCPUserLink link, Mode mode, String distantID) {
		super(networkManager);
		this.link = link;
		this.mode = mode;
		this.distantID = distantID;
	}

	public Mode getMode() {
		return mode;
	}
	public TCPUserLink getLink(){
		return this.link;
	}

	public String getDistantID() {
		return distantID;
	}
}
