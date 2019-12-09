package org.clav.network.protocolsimpl.tcp;

import org.clav.network.TCPUserLink;
import org.clav.network.Protocol;

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
			identifier = link.read();
			this.log("[TCP]Receiving identifier : " + identifier);
			link.setRelatedUser(identifier);
			if (getRelatedNetworkManager().getRelatedAgent().getUserManager().isActiveUser(identifier)) {
				getRelatedNetworkManager().linkTCP(identifier, link);
				this.log("[TCP]Sending ACK");
				link.send("ACK");
				this.log("[TCP]TCP Link established with user " + identifier);
				TCPTalkProtocolInit init = new TCPTalkProtocolInit(getRelatedNetworkManager(),link);
				getRelatedNetworkManager().executeProtocol(new TCPTalkProtocol(init));
			} else {
				this.log("User trying to link with id " + identifier + " is unknown from UserManager");
			}
		} else if (getProtocolInit().getMode() == LinkTCPUserProtocolInit.Mode.CONNECT) {
			identifier = getProtocolInit().getDistantID();
			String id = getProtocolInit().getNetworkManager().getRelatedAgent().getMainUser().getIdentifier();
			this.log("[TCP]Trying to send identifier " + id + " to connect target");
			link.send(id);
			this.log("[TCP]Waiting " + identifier + " ACK for TCP linking");
			String ack = link.read();
			if (ack.equals("ACK")) {
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
