package org.clav;

import org.clav.network.protocolsimpl.udp.ActivitySignalProtocol;
import org.clav.network.protocolsimpl.udp.ActivitySignalProtocolInit;
import org.clav.network.NetworkManager;
import org.clav.user.User;
import org.clav.user.UserManager;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import static org.clav.utils.constants.NetworkConstants.*;

public class ProtoSnd {
	public static void main(String[] args) {
		try {
			Scanner in = new Scanner(System.in);
			//InetAddress localAddr = InetAddress.getByName("127.0.0.1");
			InetAddress localAddr = InetAddress.getByName("0.0.0.0");
			InetAddress broadcastAddr = InetAddress.getByName("10.1.255.255");
			byte[] singleTarget = "This message is only for YOU".getBytes();
			System.out.println("LOCAL : "  + localAddr);
			DatagramSocket sendSocketUDP = new DatagramSocket(UDPSOCKET_SEND);
			NetworkManager networkManager = NetworkManager.testModeNetworkManager(localAddr,broadcastAddr,sendSocketUDP,null);
			UserManager userManager = new UserManager(null,null);
			userManager.setMainUser(new User("decaeste","DarkPseudoLul"));
			networkManager.executeProtocol(new ActivitySignalProtocol(new ActivitySignalProtocolInit(networkManager,userManager)));
			String ipLine;
			System.out.println("Type IP Adress to test single target message :");
			while(!(ipLine = in.nextLine()).equals("END")){
				try {
					InetAddress address = InetAddress.getByName(ipLine);
					System.out.println("Sending single target message");
					networkManager.UDP_Send(singleTarget, address);

				}
				catch (UnknownHostException e){
					System.out.println("Invalid IP ADDR");
				}
				finally {
					System.out.println("Type IP Adress to test single target message :");
				}
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}

	}

}
