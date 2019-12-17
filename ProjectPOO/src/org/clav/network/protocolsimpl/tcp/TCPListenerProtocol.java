package org.clav.network.protocolsimpl.tcp;

import org.clav.network.TCPUserLink;
import org.clav.network.Protocol;
import org.clav.network.ProtocolInit;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import static org.clav.utils.constants.NetworkConstants.*;

/**
 * Central protocol to capture tcp connections attempts
 */
public class TCPListenerProtocol extends Protocol {
	private ServerSocket serverSocket;
	public TCPListenerProtocol(ProtocolInit protocolInit) {
		super(protocolInit);
		try {
			this.serverSocket = new ServerSocket(TCP_SOCKET_SERVER_PORT);
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
				this.getRelatedNetworkManager().executeProtocol(new LinkTCPUserProtocol(init));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
