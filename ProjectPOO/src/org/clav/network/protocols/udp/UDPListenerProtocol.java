package org.clav.network.protocols.udp;

import org.clav.network.CLVPacket;
import org.clav.network.CLVPacketFactory;
import org.clav.network.Protocol;
import org.clav.network.ProtocolInit;
import org.clav.user.PseudoRejection;
import org.clav.user.User;
import org.clav.utils.Serializer;

import java.io.IOException;
import java.net.DatagramPacket;


import static org.clav.network.CLVHeader.REJ;
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
				if (packet.header == SIG) {
					User user = (User) (packet.data);
					//TODO test rejection protocol, move to applicative level?
					if (user.getPseudo().equals(this.getRelatedNetworkManager().getAppHandler().getMainUser().getPseudo())) {
						PseudoRejection rejection = new PseudoRejection(user.getPseudo(), getRelatedNetworkManager().getAppHandler().getMainUser().getPseudoDate());
						CLVPacket rejectionPacket = CLVPacketFactory.gen_REJ(rejection);
						getRelatedNetworkManager().UDP_Send(Serializer.toBytes(rejectionPacket), packetUDP.getAddress());
					} else {
						String[] ids = new String[]{user.getIdentifier(), user.getPseudo()};
						boolean toRepr = !getRelatedNetworkManager().getAppHandler().isActiveID(ids[0]);//DEBUG PURPOSE
						getRelatedNetworkManager().addAddrFor(ids[0], packetUDP.getAddress());
						getRelatedNetworkManager().getAppHandler().processNewUser(user);
						if (toRepr) {
							this.log("[UDP-USER]Updating new user : " + ids[0] + " " + ids[1]);
							getRelatedNetworkManager().getDebug().detectNewUser(ids[0]);
							getRelatedNetworkManager().getDebug().displayUsers(getRelatedNetworkManager().getAppHandler().getActivesID());
						}
					}

				} else if (packet.header == REJ) {
					PseudoRejection rejection = (PseudoRejection) packet.data;
					getRelatedNetworkManager().getAppHandler().processPseudoRejection(rejection);

				} else {
					log("[UDP]Receiving unknown packet");
				}
				Thread.sleep(0);//Allows interruption

			}

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}
}
