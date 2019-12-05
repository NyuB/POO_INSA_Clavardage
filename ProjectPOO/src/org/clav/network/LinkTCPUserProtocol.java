package org.clav.network;

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
		String identifier = null;

		if (getProtocolInit().getMode() == LinkTCPUserProtocolInit.Mode.ACCEPT) {
			//ATTENTION BLOQUANT
			// /!\ /!\ /!\ /!\


			System.out.println("[TCP]Waiting user identifier for TCP linking");
			identifier = link.read();
			System.out.println("[TCP]Receiving identifier : " + identifier);
			link.setRelatedUser(identifier);

			// /!\ /!\ /!\ /!\
			if (getRelatedNetworkManager().getRelatedAgent().getUserManager().isActiveUser(identifier)) {
				getRelatedNetworkManager().linkTCP(identifier, link);
				System.out.println("[TCP]Sending ACK");
				link.send("ACK");
				System.out.println("[TCP]TCP Link established with user " + identifier);
				TCPTalkProtocolInit init = new TCPTalkProtocolInit(getRelatedNetworkManager(),link);
				getRelatedNetworkManager().executeProtocol(new TCPTalkProtocol(init));
			} else {
				System.out.println("User trying to link with id " + identifier + " is unknown from UserManager");
			}
		} else if (getProtocolInit().getMode() == LinkTCPUserProtocolInit.Mode.CONNECT) {
			identifier = getProtocolInit().getDistantID();
			String id = getProtocolInit().getNetworkManager().getRelatedAgent().getMainUser().getIdentifier();
			System.out.println("[TCP]Trying to send identifier " + id + " to connect target");
			link.send(id);
			System.out.println("[TCP]Waiting " + identifier + " ACK for TCP linking");
			String ack = link.read();
			if (ack.equals("ACK")) {
				System.out.println("[TCP]Receiving ACK from " + identifier);
				getRelatedNetworkManager().linkTCP(identifier,link);
				TCPTalkProtocolInit init = new TCPTalkProtocolInit(getRelatedNetworkManager(),link);
				getRelatedNetworkManager().executeProtocol(new TCPTalkProtocol(init));
			} else {
				System.out.println("[TCP]Receiving unvalid ack message");
			}

		}

	}
}
