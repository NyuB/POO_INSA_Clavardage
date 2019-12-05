package org.clav.network;

public class TCPTalkProtocolInit extends ProtocolInit {
	private TCPUserLink link;

	public TCPTalkProtocolInit(NetworkManager networkManager, TCPUserLink link) {
		super(networkManager);
		this.link = link;
	}

	public TCPUserLink getLink() {
		return link;
	}
}
