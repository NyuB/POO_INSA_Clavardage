package org.clav;

import org.clav.chat.ChatManager;
import org.clav.chat.Message;
import org.clav.config.ConfigManager;
import org.clav.network.NetworkManager;
import org.clav.user.User;
import org.clav.user.UserManager;

public class Agent {
	private NetworkManager networkManager;
	private ChatManager chatManager;
	private UserManager userManager;
	private ConfigManager configManager;


	public ChatManager getChatManager() {
		return chatManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public User getMainUser() {
		return getUserManager().getMainUser();
	}

	public NetworkManager getNetworkManager() {
		return networkManager;
	}

	public void processMessage(Message message){
		this.getChatManager().insertMessage(message);
	}

	public void setNetworkManager(NetworkManager networkManager) {
		this.networkManager = networkManager;
	}

	public void setChatManager(ChatManager chatManager) {
		this.chatManager = chatManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setConfigManager(ConfigManager configManager) {
		this.configManager = configManager;
	}
}
