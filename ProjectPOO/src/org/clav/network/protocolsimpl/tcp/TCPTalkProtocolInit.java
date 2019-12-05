package org.clav.network.protocolsimpl.tcp;

import org.clav.network.NetworkManager;
import org.clav.network.TCPUserLink;
import org.clav.network.ProtocolInit;

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
