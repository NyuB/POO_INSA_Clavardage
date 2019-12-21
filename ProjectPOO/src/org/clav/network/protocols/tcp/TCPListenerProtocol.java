package org.clav.network.protocols.tcp;

import org.clav.network.Protocol;
import org.clav.network.ProtocolInit;
import org.clav.network.TCPUserLink;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Central protocol to capture tcp connections attempts. Launches parallel threads to handle communication after connection
 * @see TCPTalkProtocol
 */
public class TCPListenerProtocol extends Protocol {
	private ServerSocket serverSocket;
	public TCPListenerProtocol(ProtocolInit protocolInit) {
		super(protocolInit);
		try {
			this.serverSocket = new ServerSocket(this.getRelatedNetworkManager().TCP_LOCAL_DEBUG_PORT);//new ServerSocket(TCP_SOCKET_SERVER_PORT);
			this.getRelatedNetworkManager().log("TCP Server created");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true){
			try {
				this.log("[TCP]Server of "+getRelatedNetworkManager().getAppHandler().getMainUser().getIdentifier()+" waiting for connection request");
				Socket distant = this.serverSocket.accept();
				this.log("[TCP]Server receiving connection request, engaging link protocol");
				TCPUserLink link = new TCPUserLink(null,distant);
				LinkTCPUserProtocolInit init = new LinkTCPUserProtocolInit(this.getRelatedNetworkManager(),link, LinkTCPUserProtocolInit.Mode.ACCEPT);
				this.getRelatedNetworkManager().executeProtocol(new LinkTCPUserProtocol(init),true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
