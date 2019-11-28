package org.clav;

import org.clav.network.ActivitySignalProtocol;
import org.clav.network.ActivitySignalProtocolInit;
import org.clav.network.NetworkManager;
import org.clav.user.User;
import org.clav.user.UserManager;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import static org.clav.network.NetworkManager.*;

public class ProtoSnd {
	public static void main(String[] args) {
		try {
			DatagramSocket sendSocketUDP = new DatagramSocket(UDPSOCKET_SEND);
			NetworkManager networkManager = NetworkManager.testModeNetworkManager(InetAddress.getByName("localhost"),InetAddress.getByName("localhost"),sendSocketUDP,null);
			UserManager userManager = new UserManager();
			userManager.setMainUser(new User("decaeste","DarkPseudoLul"));
			networkManager.executeProtocol(new ActivitySignalProtocol(new ActivitySignalProtocolInit(networkManager,userManager)));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}

	}

}
