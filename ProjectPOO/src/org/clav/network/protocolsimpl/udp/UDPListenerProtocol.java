package org.clav.network.protocolsimpl.udp;

import org.clav.network.Protocol;
import org.clav.network.ProtocolInit;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

import static org.clav.utils.constants.ProtocolConstants.*;


/**
 * Central protocolsimpl to capture broadcasts or low_importance messages
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
			this.log("[UDP]Waiting UDP Packet on port " + getRelatedNetworkManager().getReceiveSocketUDP().getLocalPort());
			while (true) {

				getRelatedNetworkManager().getReceiveSocketUDP().receive(packetUDP);
				String toTxt = new String(packetUDP.getData(), 0, packetUDP.getLength());
				//this.log("[UDP]Receiving UDP packet : " + toTxt);
				String[] data = toTxt.split(SIGACT_HEADER);
				if(data.length>1){
					String[] ids = data[1].split("--");

					boolean toRepr = !getRelatedNetworkManager().getRelatedAgent().getUserManager().isActiveUser(ids[0]);//DEBUG PURPOSE
					if(true || !ids[0].equals(getRelatedNetworkManager().getRelatedAgent().getMainUser().getIdentifier())) {//TODO stop listening user ownn broadcast
						//this.log("[UDP-USER]Updating new user : "+ids[0]+" "+ids[1]);
						getRelatedNetworkManager().getRelatedAgent().getUserManager().createIfAbsent(ids[0], ids[1]);
						getRelatedNetworkManager().addAddrFor(ids[0], packetUDP.getAddress());
						if (toRepr) getRelatedNetworkManager().getDebug().displayUsers(getRelatedNetworkManager().getRelatedAgent().getUserManager().getActiveUsers().keySet());
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
