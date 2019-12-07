package org.clav;

import org.clav.debug.graphic.DebugFrame;
import org.clav.network.NetworkManager;
import org.clav.user.User;
import org.clav.user.UserManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ProtoMonitor {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		//System.out.println("Enter user name");
		//String name = in.nextLine();

		String name = "decaeste";
		Agent agent = new Agent();
		User mainUser = new User(name, name);
		UserManager userManager = new UserManager(agent, mainUser);
		NetworkManager networkManager = null;
		String line;
		try {
			InetAddress localAddr = InetAddress.getByName("0.0.0.0");
			System.out.println("Enter broadcast address");
			//line = in.nextLine();
			line = "localhost";
			InetAddress broadcastAddr = InetAddress.getByName(line);
			networkManager = new NetworkManager(localAddr, broadcastAddr);
			networkManager.setRelatedAgent(agent);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		agent.setNetworkManager(networkManager);
		agent.setUserManager(userManager);
		networkManager.startUDPListening();
		networkManager.startUDPSignal();
		networkManager.startTCPListening();

		DebugFrame frame = new DebugFrame();
		frame.setVisible(true);
		while ((line = in.nextLine()) != null) {
			String[] cmd = line.split("\\s");
			switch (cmd[0]) {
				case "ADD":
					frame.addChat(cmd[1]);
					break;
				case "SEND":
					if (cmd.length >= 3) frame.writeMsg(cmd[1], cmd[2]);
					break;
				default:
					System.out.println("UNKNOWN CMD");


			}
		}
	}
}
