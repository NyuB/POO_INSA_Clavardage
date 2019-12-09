package org.clav.network.protocolsimpl.tcp;

import org.clav.network.Protocol;

public class TCPTalkProtocol extends Protocol {
	public TCPTalkProtocol(TCPTalkProtocolInit protocolInit) {
		super(protocolInit);
	}
	private String getDistantID(){
		return this.getProtocolInit().getLink().getRelatedUserID();
	}
	@Override
	public TCPTalkProtocolInit getProtocolInit() {
		return (TCPTalkProtocolInit) super.getProtocolInit();
	}
	@Override
	public void run() {
		this.getRelatedNetworkManager().log("[TCP]Starting tcp talk.Waiting tcp message from "+this.getProtocolInit().getLink().getRelatedUserID());
		String line = this.getProtocolInit().getLink().read();
		while(line!=null && !line.equals("END")){//TODO Transmit objects instead of strings
			this.getRelatedNetworkManager().getDebug().receiveChatMessageFrom(this.getDistantID(),line);//TODO Delegate treatment of packet content to the appropritate managers
			line = this.getProtocolInit().getLink().read();
		}
		this.getRelatedNetworkManager().closeConnectionTCP(this.getProtocolInit().getLink().getRelatedUserID());

	}
}
