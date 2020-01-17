package org.clav;

import org.clav.chat.Chat;
import org.clav.chat.ChatManager;
import org.clav.config.Config;
import org.clav.config.Installer;
import org.clav.database.EmptyChatStorage;
import org.clav.database.TxtChatStorage;
import org.clav.network.NetworkManager;
import org.clav.network.server.HttpPresenceClient;
import org.clav.user.User;
import org.clav.user.UserManager;
import org.clav.utils.constants.FormatConstant;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProtoConfig {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		new Installer().install();
		Agent.launchAgent();
		Agent agent = Agent.getInstance();
		ChatManager chatManager = agent.getChatManager();
		NetworkManager networkManager = agent.getNetworkManager();
		UserManager userManager = agent.getUserManager();

		boolean over = false;
		String line ;
		while (!over && (line = in.nextLine()) != null) {
			String[] cmd = line.split(FormatConstant.spaceRegex);
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
							agent.getGUIManager().getController().notifyChatInitiationFromUser(ids);
						}
						break;
					case "SAV":
						chatManager.save();
						break;
					case "CHNB":
						System.out.println(chatManager.getChats().size() + " active chats");
						break;
					case "HIS":
						for (Chat chat : chatManager.getChats().values()) {
							System.out.println(chat.getHistory().toString());
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
					case "USR":
						for (User u : agent.getActiveUsers().values()) {
							System.out.println(u.getIdentifier() + " | " + u.getPseudo());
						}
						break;
					case "STO":
						if (cmd.length > 1) {
							switch (cmd[1]) {
								case "none":
									chatManager.setStorage(new EmptyChatStorage());
									break;
								case "txt":
									chatManager.setStorage(new TxtChatStorage("dataproxy.txt"));
									break;
								default:
									break;
							}
						}
					case "PRES":
						if (cmd.length > 1) {
							switch (cmd[1]) {
								case "on":
									networkManager.linkPresenceServer(new HttpPresenceClient(cmd[2]));
									break;
								case "off":
									networkManager.getPresenceClients().clear();
									break;
								default:
									break;
							}
						}


					default:
						break;
				}
			}
		}
	}
}
