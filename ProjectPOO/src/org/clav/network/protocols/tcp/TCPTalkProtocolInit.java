package org.clav.network.protocols.tcp;

import org.clav.network.NetworkManager;
import org.clav.network.ProtocolInit;
import org.clav.network.TCPUserLink;

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
