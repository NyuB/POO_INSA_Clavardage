package org.clav;

import org.clav.chat.ChatManager;
import org.clav.config.ConfigManager;
import org.clav.network.NetworkManager;
import org.clav.user.User;
import org.clav.user.UserManager;

public class Agent {
	private NetworkManager networkManager;
	private ChatManager chatManager;
	private UserManager userManager;
	private ConfigManager configManager;
	private User mainUser;

	public static void main(String[] args) {

	}

	public NetworkManager getNetworkManager1() {
		return networkManager;
	}

	public ChatManager getChatManager() {
		return chatManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}
}
