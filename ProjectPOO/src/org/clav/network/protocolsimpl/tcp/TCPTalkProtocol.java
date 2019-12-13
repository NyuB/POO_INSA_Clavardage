package org.clav.network.protocolsimpl.tcp;

import org.clav.chat.Message;
import org.clav.network.CLVHeader;
import org.clav.network.CLVPacket;
import org.clav.network.Protocol;

import java.util.ArrayList;

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
			switch (packet.header) {
				case END:
					open = false;
					break;
				case STR:
					this.getRelatedNetworkManager().getDebug().receiveChatMessageFrom(this.getDistantID(), (String) packet.data);
					break;
				case MSG:
					this.getRelatedNetworkManager().getRelatedAgent().processMessage((Message) packet.data);
					break;
				case CHI:
					this.getRelatedNetworkManager().getRelatedAgent().processChatInitiation((ArrayList<String>) packet.data);
				default:
					break;
			}
			packet = this.getProtocolInit().getLink().read();




		}
		this.getRelatedNetworkManager().closeConnectionTCP(this.getProtocolInit().getLink().getRelatedUserID());

	}
}
