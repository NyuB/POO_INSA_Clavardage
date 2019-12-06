package org.clav;

import org.clav.network.*;
import org.clav.network.protocolsimpl.tcp.TCPListenerProtocol;
import org.clav.network.protocolsimpl.udp.ActivitySignalProtocol;
import org.clav.network.protocolsimpl.udp.ActivitySignalProtocolInit;
import org.clav.network.protocolsimpl.udp.UDPListenerProtocol;
import org.clav.user.User;
import org.clav.user.UserManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ProtoTCP {
	public static void main(String[] args) {
		Scanner in  = new Scanner(System.in);
		System.out.println("Enter user name");
		String name = in.nextLine();
		Agent agent = new Agent();
		User mainUser = new User(name,name);
		UserManager userManager = new UserManager(agent,mainUser);
		NetworkManager networkManager = null;
		String line;
		try {
			InetAddress localAddr = InetAddress.getByName("0.0.0.0");
			System.out.println("Enter broadcast address");
			line = in.nextLine();
			InetAddress broadcastAddr = InetAddress.getByName(line);
			networkManager = new NetworkManager(localAddr,broadcastAddr);
			networkManager.setRelatedAgent(agent);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		agent.setNetworkManager(networkManager);
		agent.setUserManager(userManager);
		networkManager.startUDPListening();
		networkManager.startUDPSignal();
		networkManager.startTCPListening();

		System.out.println("Enter command");
		while(!(line=in.nextLine()).equals("END")){
			/*
			if(line.equals("SIGNAL")){
				networkManager.executeProtocol(new ActivitySignalProtocol(new ActivitySignalProtocolInit(networkManager,userManager)));
			}
			else if(line.equals("UDPLISTEN")){
				networkManager.executeProtocol(new UDPListenerProtocol(new ProtocolInit(networkManager)));
			}
			else if(line.equals("TCPLISTEN")){
				networkManager.executeProtocol(new TCPListenerProtocol(new ProtocolInit(networkManager)));
			}
			else if(line.equals("TCPCONNECT")){
				System.out.println("Enter destination identifier");
				line = in.nextLine();
				networkManager.initiateConnectionTCP(line);
			}*/
			if(line.equals("TCPSEND")){
				System.out.println("Enter destination identifier");
				line = in.nextLine();
				System.out.println("Enter message");
				String message = in.nextLine();
				networkManager.TCP_IP_send(line,message);
			}
			else{
				System.out.println("UNKNOWN CMD");
			}
			System.out.println("Enter command");
		}
	}
}
