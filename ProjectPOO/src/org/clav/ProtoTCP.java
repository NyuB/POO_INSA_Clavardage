package org.clav;

import org.clav.config.ConfigManager;
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


		String filepath = args[0];
		System.out.println("Using configuration file : "+filepath);
		ConfigManager configManager = new ConfigManager(filepath);
		Agent agent = new Agent();
		User mainUser = new User(configManager.getUserID(),configManager.getUserID());
		UserManager userManager = new UserManager(agent,mainUser);
		agent.setUserManager(userManager);
		configManager.configNetworkManager(agent);


		Scanner in  = new Scanner(System.in);
		String line;
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
				agent.getNetworkManager().TCP_IP_send(line,message);
			}
			else{
				System.out.println("UNKNOWN CMD");
			}
			System.out.println("Enter command");
		}
	}
}
