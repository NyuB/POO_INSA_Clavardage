package org.clav;

import org.clav.network.*;
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
		try {
			InetAddress localAddr = InetAddress.getByName("0.0.0.0");
			InetAddress broadcastAddr = InetAddress.getByName("10.1.255.255");
			networkManager = new NetworkManager(localAddr,broadcastAddr);
			networkManager.setRelatedAgent(agent);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		agent.setNetworkManager(networkManager);
		agent.setUserManager(userManager);
		String line;
		System.out.println("Enter command");
		while(!(line=in.nextLine()).equals("END")){
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
			}
			else if(line.equals("TCPSEND")){
				System.out.println("Enter destination identifier");
				line = in.nextLine();
				System.out.println("Enter message");
				String message = in.nextLine();
				networkManager.TCP_IP_send(line,message);
			}
			System.out.println("Enter command");
		}



	}
}
