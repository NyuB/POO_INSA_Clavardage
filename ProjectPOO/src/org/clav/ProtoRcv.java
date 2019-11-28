package org.clav;

import org.clav.network.NetworkManager;
import org.clav.network.ProtocolInit;
import org.clav.network.UDPListenerProtocol;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import static org.clav.network.NetworkManager.*;

public class ProtoRcv {
	public static void main(String[] args) {
		NetworkManager networkManager;
		try {
			DatagramSocket receiveSocketUDP = new DatagramSocket(UDPSOCKET_RECEIVE);
			networkManager = NetworkManager.testModeNetworkManager(InetAddress.getByName("localhost"),InetAddress.getByName("localhost"),null,receiveSocketUDP);
			networkManager.executeProtocol(new UDPListenerProtocol(new ProtocolInit(networkManager)));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}


	}
}
