package org.clav;

import org.clav.network.NetworkManager;
import org.clav.network.ProtocolInit;
import org.clav.network.protocolsimpl.udp.UDPListenerProtocol;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import static org.clav.utils.constants.NetworkConstants.*;

public class ProtoRcv {
	public static void main(String[] args) {
		NetworkManager networkManager;


		try {
			InetAddress localAddr = InetAddress.getByAddress(new byte[] {0,0,0,0});
			DatagramSocket receiveSocketUDP = new DatagramSocket(UDPSOCKET_RECEIVE_PORT);
			networkManager = NetworkManager.testModeNetworkManager(localAddr,localAddr,null,receiveSocketUDP);
			networkManager.executeProtocol(new UDPListenerProtocol(new ProtocolInit(networkManager)));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}


	}
}
