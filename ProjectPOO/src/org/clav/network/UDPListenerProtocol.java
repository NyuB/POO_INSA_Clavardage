package org.clav.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;


/**
 * Central protocol to capture broadcasts or low_importance messages
 */
public class UDPListenerProtocol extends Protocol {
	public UDPListenerProtocol(ProtocolInit protocolInit) {
		super(protocolInit);
	}

	@Override
	public void run() {
		try {
			byte[] buffer = new byte[256];
			DatagramPacket packetUDP = new DatagramPacket(buffer, 256);
			while (true) {
				System.out.println("Waiting UDP Packet on port " + getRelatedNetworkManager().getReceiveSocketUDP().getLocalPort());
				getRelatedNetworkManager().getReceiveSocketUDP().receive(packetUDP);
				String toTxt = new String(packetUDP.getData(), 0, packetUDP.getLength());
				System.out.println("Receiving UDP packet : " + toTxt);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
