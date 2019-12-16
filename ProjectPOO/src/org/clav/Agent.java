package org.clav;

import org.clav.chat.ChatInit;
import org.clav.chat.ChatManager;
import org.clav.chat.Message;
import org.clav.config.ConfigManager;
import org.clav.network.CLVPacket;
import org.clav.network.CLVPacketFactory;
import org.clav.network.NetworkManager;
import org.clav.user.User;
import org.clav.user.UserManager;

public class Agent implements AppHandler {
	private NetworkManager networkManager;
	private ChatManager chatManager;
	private UserManager userManager;
	private ConfigManager configManager;


	public ChatManager getChatManager() {
		return chatManager;
	}

	public void setChatManager(ChatManager chatManager) {
		this.chatManager = chatManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public void setConfigManager(ConfigManager configManager) {
		this.configManager = configManager;
	}

	public NetworkManager getNetworkManager() {
		return networkManager;
	}

	public void setNetworkManager(NetworkManager networkManager) {
		this.networkManager = networkManager;
	}

	//AppHandler implementation
	@Override
	public User getMainUser() {
		return getUserManager().getMainUser();
	}

	@Override
	public void sendMessage(Message message) {
		CLVPacket packet = CLVPacketFactory.gen_MSG(message);
		this.getNetworkManager().TCP_IP_send(message.getUserID(),packet);
	}

	@Override
	public void initiateChat(ChatInit init) {
		CLVPacket packet = CLVPacketFactory.gen_CHI(init);
		for (String id : init.getIdentifiers()) {
			this.getNetworkManager().TCP_IP_send(id, packet);
		}
	}

	@Override
	public void processMessage(Message message) {
		this.getChatManager().processMessageReception(message);
	}

	@Override
	public void processChatInitiation(ChatInit init) {
		this.getChatManager().createIfNew(init);
	}
}
