package org.clav.network.protocols.tcp;

import org.clav.network.CLVPacket;
import org.clav.network.CLVPacketFactory;
import org.clav.network.Protocol;
import org.clav.network.TCPUserLink;

import static org.clav.network.CLVHeader.ACK;

/**
 * Executes the tcp linkage (from the applicative point of view) between a distant user and the main user
 * If protocolInit field is in accept mode, waits the distant user identifier, then send a ack and update network manager tcp link table if the identifier is valid(aka present in active list of the network manager appHandler user list)
 * If protocolInit field is in connect mode, sends the main user identifier, then wait a ack to update the network manager tcp links table
 */
public class LinkTCPUserProtocol extends Protocol {
	public LinkTCPUserProtocol(LinkTCPUserProtocolInit protocolInit) {
		super(protocolInit);
	}

	@Override
	public LinkTCPUserProtocolInit getProtocolInit() {
		return (LinkTCPUserProtocolInit) super.getProtocolInit();
	}

	@Override
	public void run() {
		TCPUserLink link = this.getProtocolInit().getLink();
		String identifier;
		if (getProtocolInit().getMode() == LinkTCPUserProtocolInit.Mode.ACCEPT) {
			this.log("[TCP]Waiting user identifier for TCP linking");
			identifier = link.readStr();
			this.log("[TCP]Receiving identifier : " + identifier);
			link.setRelatedUser(identifier);
			if (getRelatedNetworkManager().getAppHandler().isActiveID(identifier)) {
				getRelatedNetworkManager().linkTCP(identifier, link);
				this.log("[TCP]Sending ACK");
				link.send(CLVPacketFactory.gen_ACK());
				this.log("[TCP]TCP Link established with user " + identifier);
				TCPTalkProtocolInit init = new TCPTalkProtocolInit(getRelatedNetworkManager(),link);
				getRelatedNetworkManager().executeProtocol(new TCPTalkProtocol(init),true);
			} else {
				this.log("User trying to link with id " + identifier + " is unknown from UserManager");
				link.send(CLVPacketFactory.gen_ERR());
			}
		} else if (getProtocolInit().getMode() == LinkTCPUserProtocolInit.Mode.CONNECT) {
			identifier = getProtocolInit().getDistantID();
			String id = getProtocolInit().getNetworkManager().getAppHandler().getMainUser().getIdentifier();
			this.log("[TCP]Trying to send identifier " + id + " to connect target");
			link.send(CLVPacketFactory.gen_STR(id));
			this.log("[TCP]Waiting " + identifier + " ACK for TCP linking");
			CLVPacket ack = link.read();
			if (ack.header == ACK) {
				this.log("[TCP]Receiving ACK from " + identifier);
				getRelatedNetworkManager().linkTCP(identifier,link);
				TCPTalkProtocolInit init = new TCPTalkProtocolInit(getRelatedNetworkManager(),link);
				getRelatedNetworkManager().executeProtocol(new TCPTalkProtocol(init),true);
			} else {
				this.log("[TCP]Receiving unvalid ack message");
			}
		}
	}
}
