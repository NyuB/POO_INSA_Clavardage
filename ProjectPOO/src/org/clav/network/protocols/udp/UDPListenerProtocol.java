package org.clav.network.protocols.udp;

import org.clav.network.CLVPacket;
import org.clav.network.CLVPacketFactory;
import org.clav.network.Protocol;
import org.clav.network.ProtocolInit;
import org.clav.network.server.ServerNotification;
import org.clav.user.PseudoRejection;
import org.clav.user.User;
import org.clav.utils.Serializer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

import static org.clav.network.CLVHeader.*;
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
			byte[] buffer = new byte[1000];
			DatagramPacket packetUDP = new DatagramPacket(buffer, buffer.length);
			this.log("[UDP]Waiting UDP Packet on port " + getRelatedNetworkManager().getReceiveSocketUDP().getLocalPort());
			while (true) {
				try {
					getRelatedNetworkManager().getReceiveSocketUDP().receive(packetUDP);
					CLVPacket packet = (CLVPacket) fromBytes(packetUDP.getData());
					if (packet.header == SIG || packet.header == NOT) {
						User user = (packet.header == SIG) ? (User) (packet.data) : ((ServerNotification) (packet.data)).getUser();
						InetAddress distAddr = (packet.header == SIG) ? packetUDP.getAddress() : ((ServerNotification) (packet.data)).getAddress();

						//TODO test rejection protocol, move to applicative level?
						boolean reject = getRelatedNetworkManager().getAppHandler().checkRejection(user);
						if (reject) {
							PseudoRejection rejection = new PseudoRejection(user.getPseudo(), getRelatedNetworkManager().getAppHandler().getMainUser().getPseudoDate());
							CLVPacket rejectionPacket = CLVPacketFactory.gen_REJ(rejection);
							getRelatedNetworkManager().UDP_Send(Serializer.toBytes(rejectionPacket), distAddr);
						} else {
							String[] ids = new String[]{user.getIdentifier(), user.getPseudo()};
							boolean toRepr = !getRelatedNetworkManager().getAppHandler().isActiveID(ids[0]);//DEBUG PURPOSE
							getRelatedNetworkManager().addAddrFor(ids[0], distAddr);
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

				} catch (ClassCastException e) {
					e.printStackTrace();
				} finally {
					Thread.sleep(0);//Allows interruption
				}


			}

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}
}
