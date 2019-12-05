package org.clav.network.protocolsimpl.tcp;

import org.clav.network.Protocol;

public class TCPTalkProtocol extends Protocol {
	public TCPTalkProtocol(TCPTalkProtocolInit protocolInit) {
		super(protocolInit);
	}

	@Override
	public TCPTalkProtocolInit getProtocolInit() {
		return (TCPTalkProtocolInit) super.getProtocolInit();
	}

	@Override
	public void run() {
		System.out.println("[TCP]Starting tcp talk.Waiting tcp message from "+this.getProtocolInit().getLink().getRelatedUserID());
		String line = this.getProtocolInit().getLink().read();
		while(line!=null && !line.equals("[END]")){
			System.out.println("\t"+line);
		}
		this.getRelatedNetworkManager().closeConnectionTCP(this.getProtocolInit().getLink().getRelatedUserID());

	}
}
