package org.clav.network;
import static org.clav.network.NetworkManager.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPListenerProtocol extends Protocol {
	public UDPListenerProtocol(ProtocolInit protocolInit) {
		super(protocolInit);
	}

	@Override
	public void run() {
		try {
			DatagramSocket socketUDP = new DatagramSocket(UDPSOCKET_RECEIVE);
			byte[] buffer = new byte[256];
			DatagramPacket packetUDP = new DatagramPacket(buffer,256);
			while(true){
				System.out.println("Waiting UDP Packet on port "+socketUDP.getLocalPort());
				socketUDP.receive(packetUDP);
				String toTxt = new String(packetUDP.getData(),0,packetUDP.getLength());
				System.out.println("Receiving UDP packet : "+toTxt);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
