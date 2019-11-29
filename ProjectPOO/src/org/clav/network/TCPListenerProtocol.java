package org.clav.network;

import org.clav.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true){
			try {
				Socket distant = this.serverSocket.accept();
				String identifier = new BufferedReader(new InputStreamReader(distant.getInputStream())).readLine();
				if(getRelatedNetworkManager().getRelatedAgent().getUserManager().isActiveUser(identifier)){
					User user = getRelatedNetworkManager().getRelatedAgent().getUserManager().getActiveUsers().get(identifier);
					getRelatedNetworkManager().addConnectionTCP(identifier,distant);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
