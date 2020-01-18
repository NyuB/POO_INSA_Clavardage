package org.clav.network.protocols.tcp;

import org.clav.chat.ChatInit;
import org.clav.chat.ChatUnknown;
import org.clav.chat.Message;
import org.clav.network.CLVPacket;
import org.clav.network.Protocol;
import org.clav.utils.Serializer;

/**
 * Packet header which will be processed : STR,MSG,CHI (allow maintaining the connection) ERR,END (close the connection)
 * @see org.clav.network.CLVHeader
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

	/**
	 * Override with call to super to add more header type processing
	 * @param packet the CLV packet to treat
	 * @return false if the connection has to be closed
	 */

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
			case IMG:
				return this.process_IMG(packet);
			case UNK:
				return this.process_UNK(packet);
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

	protected boolean process_UNK(CLVPacket packet){
		ChatUnknown chatUnknown = (ChatUnknown)packet.data;
		this.getRelatedNetworkManager().getAppHandler().processChatUnknownRequest(chatUnknown);
		return true;
	}
	protected boolean process_IMG(CLVPacket packet){
		byte[] bytes = (byte[])packet.data;
		this.getRelatedNetworkManager().getAppHandler().processImage(Serializer.bytesAsImage(bytes));
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
		}
		this.getRelatedNetworkManager().closeConnectionTCP(this.getProtocolInit().getLink().getRelatedUserID());

	}
}
