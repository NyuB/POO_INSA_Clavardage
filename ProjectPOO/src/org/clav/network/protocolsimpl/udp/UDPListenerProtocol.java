package org.clav.network.protocolsimpl.udp;

import org.clav.network.CLVHeader;
import org.clav.network.CLVPacket;
import org.clav.network.Protocol;
import org.clav.network.ProtocolInit;
import org.clav.user.User;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

import static org.clav.network.CLVHeader.SIG;
import static org.clav.utils.Serializer.fromBytes;
import static org.clav.utils.constants.ProtocolConstants.*;


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
			byte[] buffer = new byte[500];
			DatagramPacket packetUDP = new DatagramPacket(buffer, 500);
			this.log("[UDP]Waiting UDP Packet on port " + getRelatedNetworkManager().getReceiveSocketUDP().getLocalPort());
			while (true) {//TODO Delegate treatment of packet's content to the appropriate managers

				getRelatedNetworkManager().getReceiveSocketUDP().receive(packetUDP);
				//String toTxt = new String(packetUDP.getData(), 0, packetUDP.getLength());
				CLVPacket packet = (CLVPacket) fromBytes(packetUDP.getData());

				//this.log("[UDP]Receiving UDP packet : " + toTxt);
				//String[] data = toTxt.split(SIGACT_HEADER);
				if( packet.header==SIG/*data.length>1*/){
					User user = (User)(packet.data);
					String[] ids = new String[] {user.getIdentifier(),user.getPseudo()};

					boolean toRepr = !getRelatedNetworkManager().getRelatedAgent().getUserManager().isActiveUser(ids[0]);//DEBUG PURPOSE
					if(true || !ids[0].equals(getRelatedNetworkManager().getRelatedAgent().getMainUser().getIdentifier())) {
						getRelatedNetworkManager().getRelatedAgent().getUserManager().createIfAbsent(ids[0], ids[1]);
						getRelatedNetworkManager().addAddrFor(ids[0], packetUDP.getAddress());

						if (toRepr) {
							this.log("[UDP-USER]Updating new user : "+ids[0]+" "+ids[1]);
							getRelatedNetworkManager().getDebug().detectNewUser(ids[0]);
							getRelatedNetworkManager().getDebug().displayUsers(getRelatedNetworkManager().getRelatedAgent().getUserManager().getActiveUsers().keySet());
						}
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
