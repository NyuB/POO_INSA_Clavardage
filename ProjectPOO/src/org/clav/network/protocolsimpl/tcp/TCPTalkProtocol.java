package org.clav.network.protocolsimpl.tcp;

import org.clav.chat.ChatInit;
import org.clav.chat.Message;
import org.clav.network.CLVPacket;
import org.clav.network.Protocol;

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

	protected void processPacket(CLVPacket packet) {
		switch (packet.header) {
			case END:
				this.process_END(packet);
				break;
			case STR:
				this.process_STR(packet);
				break;
			case MSG:
				this.process_MSG(packet);
				break;
			case CHI:
				this.process_CHI(packet);
				break;
			case ERR:
				this.process_ERR(packet);
				break;
			default:
				break;
		}

	}

	protected void process_END(CLVPacket packet) {
		this.getRelatedNetworkManager().closeConnectionTCP(this.getProtocolInit().getLink().getRelatedUserID());
	}

	protected void process_STR(CLVPacket packet) {
		this.getRelatedNetworkManager().getDebug().receiveChatMessageFrom(this.getDistantID(), (String) packet.data);
	}

	protected void process_MSG(CLVPacket packet) {
		this.getRelatedNetworkManager().getAppHandler().processMessage((Message) packet.data);
	}

	protected void process_CHI(CLVPacket packet) {
		this.getRelatedNetworkManager().getAppHandler().processChatInitiation((ChatInit) packet.data);
	}

	protected void process_ERR(CLVPacket packet) {

	}

	@Override
	public void run() {
		this.getRelatedNetworkManager().log("[TCP]Starting tcp talk.Waiting tcp message from " + this.getProtocolInit().getLink().getRelatedUserID());

		CLVPacket packet = this.getProtocolInit().getLink().read();
		boolean open = true;
		while (packet != null && open) {
			switch (packet.header) {
				case END:
					open = false;
					break;
				case ERR:
					open = false;
					break;
				case STR:
					this.getRelatedNetworkManager().getDebug().receiveChatMessageFrom(this.getDistantID(), (String) packet.data);
					break;
				case MSG:
					this.getRelatedNetworkManager().getAppHandler().processMessage((Message) packet.data);
					break;
				case CHI:
					this.getRelatedNetworkManager().getAppHandler().processChatInitiation((ChatInit) packet.data);
					break;
				default:
					break;
			}
			packet = this.getProtocolInit().getLink().read();


		}
		this.getRelatedNetworkManager().closeConnectionTCP(this.getProtocolInit().getLink().getRelatedUserID());

	}
}
