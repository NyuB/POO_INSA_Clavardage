package org.clav.network.protocolsimpl.tcp;

import org.clav.network.CLVHeader;
import org.clav.network.CLVPacket;
import org.clav.network.Protocol;

import static org.clav.network.CLVHeader.*;

public class TCPTalkProtocol extends Protocol {
	public TCPTalkProtocol(TCPTalkProtocolInit protocolInit) {
		super(protocolInit);
	}

	private String getDistantID() {
		return this.getProtocolInit().getLink().getRelatedUserID();
	}

	@Override
	public TCPTalkProtocolInit getProtocolInit() {
		return (TCPTalkProtocolInit) super.getProtocolInit();
	}

	@Override
	public void run() {
		this.getRelatedNetworkManager().log("[TCP]Starting tcp talk.Waiting tcp message from " + this.getProtocolInit().getLink().getRelatedUserID());
		CLVPacket packet = this.getProtocolInit().getLink().read();
		boolean open = true;
		while (packet != null && open) {
			//TODO Delegate treatment of packet content to the appropriate managers
			if (packet.header == END) {
				open = false;
			}
			else if(packet.header == STR) {
				this.getRelatedNetworkManager().getDebug().receiveChatMessageFrom(this.getDistantID(), (String)packet.data);
				packet = this.getProtocolInit().getLink().read();
			}
		}
		this.getRelatedNetworkManager().closeConnectionTCP(this.getProtocolInit().getLink().getRelatedUserID());

	}
}
