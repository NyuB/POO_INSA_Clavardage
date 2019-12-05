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
		System.out.println("[TCP]Starting tcp talk");
		while(true){
			System.out.println("[TCP]Waiting tcp message from "+this.getProtocolInit().getLink().getRelatedUserID());
			System.out.println("\t"+this.getProtocolInit().getLink().read());
		}

	}
}
