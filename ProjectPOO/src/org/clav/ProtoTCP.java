package org.clav;

import org.clav.network.NetworkManager;
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



	}
}
