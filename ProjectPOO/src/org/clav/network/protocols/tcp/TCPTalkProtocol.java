package org.clav.network.protocols.tcp;

import org.clav.chat.ChatInit;
import org.clav.chat.Message;
import org.clav.network.CLVPacket;
import org.clav.network.Protocol;

/**
 * Packet header which will be processed : STR,MSG,CHI (allow maintaining the connection) ERR,END (close the connection)
 */
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

	//Return false if the connection has to be closed
	//Override with call to super to ADD more header type processing
	protected boolean processPacket(CLVPacket packet) {
		switch (packet.header) {
			case END:
				return this.process_END(packet);
			case STR:
				return this.process_STR(packet);
			case MSG:
				return this.process_MSG(packet);
			case CHI:
				return this.process_CHI(packet);
			case ERR:
				return this.process_ERR(packet);
			default:
				return true;
		}

	}

	//Override each of these method to CHANGE processing of each header type

	protected boolean process_END(CLVPacket packet) {
		this.getRelatedNetworkManager().closeConnectionTCP(this.getProtocolInit().getLink().getRelatedUserID());
		return false;
	}

	protected boolean process_STR(CLVPacket packet) {
		this.getRelatedNetworkManager().getDebug().receiveChatMessageFrom(this.getDistantID(), (String) packet.data);
		return true;
	}

	protected boolean process_MSG(CLVPacket packet) {
		this.getRelatedNetworkManager().getAppHandler().processMessage((Message) packet.data);
		return true;
	}

	protected boolean process_CHI(CLVPacket packet) {
		this.getRelatedNetworkManager().getAppHandler().processChatInitiation((ChatInit) packet.data);
		return true;
	}

	protected boolean process_ERR(CLVPacket packet) {
		return false;
	}

	@Override
	public void run() {
		this.getRelatedNetworkManager().log("[TCP]Starting tcp talk.Waiting tcp message from " + this.getProtocolInit().getLink().getRelatedUserID());

		CLVPacket packet;
		boolean open = true;
		while (open && (packet = this.getProtocolInit().getLink().read())!=null) {
			open = this.processPacket(packet);
			/*
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
			}*/
		}
		this.getRelatedNetworkManager().closeConnectionTCP(this.getProtocolInit().getLink().getRelatedUserID());

	}
}
