package org.clav;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.clav.Agent;
import org.clav.chat.Chat;
import org.clav.chat.Message;
import org.clav.network.NetworkManager;
import org.clav.network.ProtocolInit;
import org.clav.network.protocolsimpl.tcp.TCPListenerProtocol;
import org.clav.network.protocolsimpl.udp.UDPListenerProtocol;
import org.clav.user.User;
import org.clav.user.UserManager;

public class TestChat {
	
	public static void main(String[] args) {
		
		//setup
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
			networkManager.setAppHandler(agent);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		agent.setNetworkManager(networkManager);
		agent.setUserManager(userManager);
		
		//protocole reseau
		networkManager.executeProtocol(new UDPListenerProtocol(new ProtocolInit(networkManager)));
		networkManager.executeProtocol(new TCPListenerProtocol(new ProtocolInit(networkManager)));
		
		//chat
		ArrayList<User> members = new ArrayList<User>() ;
		HashMap<String, User> users = userManager.getActiveUsers() ;
		members.addAll(users.values());
		Chat chat = new Chat(members);
		System.out.println("Enter command");
		while(!(line=in.nextLine()).equals("END")){
		
			if(line.equals("SEND")){
				String message = in.nextLine();
				System.out.println("Enter message");
				chat.insertMessage(new Message(mainUser.getIdentifier(),"0",message)) ;
			}
		}
		
	}
}
