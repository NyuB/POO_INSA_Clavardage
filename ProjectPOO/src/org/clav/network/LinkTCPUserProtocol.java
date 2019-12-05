package org.clav.network;

import org.clav.user.User;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.net.Socket;

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
		Socket distant = this.getProtocolInit().getDistant();
		String identifier = null;

		if (getProtocolInit().getMode() == LinkTCPUserProtocolInit.Mode.ACCEPT) {
			//ATTENTION BLOQUANT
			// /!\ /!\ /!\ /!\

			try {
				System.out.println("[TCP]Waiting user identifier for TCP linking");
				identifier = new BufferedReader(new InputStreamReader(distant.getInputStream())).readLine();
				System.out.println("[TCP]Receiving identifier : " + identifier);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// /!\ /!\ /!\ /!\
			if (getRelatedNetworkManager().getRelatedAgent().getUserManager().isActiveUser(identifier)) {
				getRelatedNetworkManager().addConnectionTCP(identifier, distant);
				System.out.println("[TCP]Sending ACK");
				getRelatedNetworkManager().getTCPLinkFor(identifier).send("ACK");
				System.out.println("[TCP]TCP Link established with user " + identifier);
			}
			else{
				System.out.println("User trying to link with id "+identifier+" is unknown from UserManager");
			}
		}

		else if (getProtocolInit().getMode() == LinkTCPUserProtocolInit.Mode.CONNECT) {
			try {
				identifier = getProtocolInit().getDistantID();
				System.out.println("[TCP]Trying to send identifier to connect target");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				new PrintWriter(distant.getOutputStream()).println(getProtocolInit().getNetworkManager().getRelatedAgent().getMainUser().getIdentifier());
				System.out.println("Waiting " + identifier + " ACK for TCP linking");
				String ack = new BufferedReader(new InputStreamReader(distant.getInputStream())).readLine();
				if (ack.equals("ACK")) {
					System.out.println("Receiving ACK from " + identifier);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
