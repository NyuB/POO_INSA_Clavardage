package org.clav;

import org.clav.chat.Chat;
import org.clav.chat.ChatManager;
import org.clav.chat.Message;
import org.clav.database.TxtChatStorage;
import org.clav.network.CLVPacket;
import org.clav.network.NetworkManager;
import org.clav.user.User;
import org.clav.user.UserManager;
import org.clav.utils.HashUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.clav.network.CLVHeader.CHI;
import static org.clav.network.CLVHeader.MSG;

public class ProtoApp {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter user name");
		String name = in.nextLine();
		Agent agent = new Agent();
		User mainUser = new User(name, name);
		UserManager userManager = new UserManager(agent, mainUser);
		ChatManager chatManager = new ChatManager();
		NetworkManager networkManager = null;
		String line;
		try {
			InetAddress localAddr = InetAddress.getByName("0.0.0.0");
			System.out.println("Enter broadcast address");
			line = in.nextLine();
			//line = "localhost";
			InetAddress broadcastAddr = InetAddress.getByName(line);
			networkManager = new NetworkManager(localAddr, broadcastAddr);
			networkManager.setAppHandler(agent);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		agent.setNetworkManager(networkManager);
		agent.setUserManager(userManager);
		agent.setChatManager(chatManager);

		chatManager.setRelatedAgent(agent);
		agent.start();
		networkManager.startUDPListening();
		networkManager.startUDPSignal();
		networkManager.startTCPListening();
		boolean over = false;

		while (!over && (line = in.nextLine()) != null) {
			String[] cmd = line.split("[\\s]+");
			if (cmd.length > 0) {
				switch (cmd[0]) {
					case "END":
						over = true;
						break;
					case "CHI":
						if (cmd.length > 1) {
							ArrayList<String> ids = new ArrayList<>();
							for (int i = 1; i < cmd.length; i++) {
								ids.add(cmd[i]);
							}
							agent.getUiManager().getController().notifyChatInitiationFromUser(ids);
						}
						break;
					case "SAV":
						chatManager.save();
						break;
					case "CHNB":
						System.out.println(chatManager.getChats().size() + " active chats");
						break;
					case "HIS":
						for(Chat chat:chatManager.getChats().values()){
							System.out.println(chat.getHistory().printHistory());
						}
						break;
					case "SIG":
						switch (cmd[1]) {
							case "on":
								networkManager.startUDPSignal();
								break;
							case "off":
								networkManager.stopActivitySignal();
								break;
							default:
								break;
						}
						break;
					case "LIS":
						switch (cmd[1]) {
							case "on":
								networkManager.startUDPListening();
								break;
							case "off":
								networkManager.stopBroadcastListening();
								break;
							default:
								break;
						}
						break;

					default:
						break;
				}
			}
		}
	}
}
