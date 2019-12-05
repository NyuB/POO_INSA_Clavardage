package org.clav.network;

import org.clav.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static org.clav.network.NetworkManager.*;

/**
 * Central protocol to capture tcp connections attempts
 */
public class TCPListenerProtocol extends Protocol {
	ServerSocket serverSocket;
	public TCPListenerProtocol(ProtocolInit protocolInit) {
		super(protocolInit);
		try {

			this.serverSocket = new ServerSocket(TCP_SOCKET_RECEIVE);
			System.out.println("TCP Server created");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true){
			try {
				System.out.println("TCP server of "+getRelatedNetworkManager().getRelatedAgent().getMainUser().getIdentifier()+" waiting for connection request");
				Socket distant = this.serverSocket.accept();
				System.out.println("TCP Server receiving connection request, engaging link protocol");
				TCPUserLink link = new TCPUserLink(null,distant);
				LinkTCPUserProtocolInit init = new LinkTCPUserProtocolInit(this.getRelatedNetworkManager(),link, LinkTCPUserProtocolInit.Mode.ACCEPT);
				this.getRelatedNetworkManager().executeProtocol(new LinkTCPUserProtocol(init));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
