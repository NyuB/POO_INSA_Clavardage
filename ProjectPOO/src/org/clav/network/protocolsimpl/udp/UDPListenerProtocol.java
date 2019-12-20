package org.clav.network.protocolsimpl.udp;

import org.clav.network.CLVPacket;
import org.clav.network.Protocol;
import org.clav.network.ProtocolInit;
import org.clav.user.User;

import java.io.IOException;
import java.net.DatagramPacket;

import static java.lang.Thread.yield;
import static org.clav.network.CLVHeader.SIG;
import static org.clav.utils.Serializer.fromBytes;


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
			while (true) {
				getRelatedNetworkManager().getReceiveSocketUDP().receive(packetUDP);
				CLVPacket packet = (CLVPacket) fromBytes(packetUDP.getData());
				if( packet.header==SIG/*data.length>1*/){
					User user = (User)(packet.data);
					String[] ids = new String[] {user.getIdentifier(),user.getPseudo()};
					boolean toRepr = !getRelatedNetworkManager().getAppHandler().isActiveID(ids[0]);//DEBUG PURPOSE
					if(true || !ids[0].equals(getRelatedNetworkManager().getAppHandler().getMainUser().getIdentifier())) {//TODO Stop talking to yourself
						getRelatedNetworkManager().addAddrFor(ids[0], packetUDP.getAddress());
						getRelatedNetworkManager().getAppHandler().processNewUser(user);
						if (toRepr) {
							this.log("[UDP-USER]Updating new user : "+ids[0]+" "+ids[1]);
							getRelatedNetworkManager().getDebug().detectNewUser(ids[0]);
							getRelatedNetworkManager().getDebug().displayUsers(getRelatedNetworkManager().getAppHandler().getActivesID());
						}
					}
				}
				Thread.sleep(0);//Allows interruption

			}

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}
}
