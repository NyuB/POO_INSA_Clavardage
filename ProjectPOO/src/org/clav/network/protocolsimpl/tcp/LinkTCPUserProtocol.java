package org.clav.network.protocolsimpl.tcp;

import org.clav.network.CLVPacket;
import org.clav.network.CLVPacketFactory;
import org.clav.network.Protocol;
import org.clav.network.TCPUserLink;

import static org.clav.network.CLVHeader.ACK;

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
				getRelatedNetworkManager().executeProtocol(new TCPTalkProtocol(init));
			} else {
				this.log("User trying to link with id " + identifier + " is unknown from UserManager");
			}
		} else if (getProtocolInit().getMode() == LinkTCPUserProtocolInit.Mode.CONNECT) {
			identifier = getProtocolInit().getDistantID();
			String id = getProtocolInit().getNetworkManager().getAppHandler().getMainUser().getIdentifier();
			this.log("[TCP]Trying to send identifier " + id + " to connect target");
			link.send(id);
			this.log("[TCP]Waiting " + identifier + " ACK for TCP linking");
			CLVPacket ack = link.read();
			if (ack.header == ACK) {
				this.log("[TCP]Receiving ACK from " + identifier);
				getRelatedNetworkManager().linkTCP(identifier,link);
				TCPTalkProtocolInit init = new TCPTalkProtocolInit(getRelatedNetworkManager(),link);
				getRelatedNetworkManager().executeProtocol(new TCPTalkProtocol(init));
			} else {
				this.log("[TCP]Receiving unvalid ack message");
			}
		}
	}
}
