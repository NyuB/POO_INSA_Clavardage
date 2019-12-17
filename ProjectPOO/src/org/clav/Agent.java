package org.clav;

import org.clav.chat.*;
import org.clav.config.ConfigManager;
import org.clav.network.CLVPacket;
import org.clav.network.CLVPacketFactory;
import org.clav.network.NetworkManager;
import org.clav.ui.mvc.CLVModel;
import org.clav.ui.UIManager;
import org.clav.user.User;
import org.clav.user.UserManager;
import org.clav.utils.HashUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class Agent implements AppHandler, CLVModel {
	private NetworkManager networkManager;
	private ChatManager chatManager;
	private UserManager userManager;
	private ConfigManager configManager;
	private UIManager uiManager;

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

	public UIManager getUiManager() {
		return uiManager;
	}

	public void setUiManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}


	public void start() {
		this.uiManager = new UIManager(this,this);
		this.uiManager.start();
	}


	//AppHandler,CLVModel Impl
	@Override
	public User getMainUser() {
		return getUserManager().getMainUser();
	}

	//AppHandler Impl
	@Override
	public void sendMessage(String distantID,Message message) {
		message.setUserID(this.getMainUser().getIdentifier());
		CLVPacket packet = CLVPacketFactory.gen_MSG(message);
		this.getNetworkManager().TCP_IP_send(distantID, packet);
		this.getChatManager().processMessageEmission(message);
		this.getUiManager().getView().refreshChat(message.getChatHashCode());
	}

	//AppHandler Impl
	@Override
	public void initiateChat(ArrayList<User> distantMembers) {
		ArrayList<User> withMain = new ArrayList<>(distantMembers);
		withMain.add(this.getMainUser());
		String code = HashUtils.hashUserList(withMain);
		if (!this.getChatManager().getChats().containsKey(code)) {
			this.getChatManager().createChat(withMain);
		}
		ChatInit init = this.getActiveChats().get(code).genChatInit();
		CLVPacket packet = CLVPacketFactory.gen_CHI(init);
		for (User u : distantMembers) {
			this.getNetworkManager().TCP_IP_send(u.getIdentifier(), packet);
		}
		this.getUiManager().getView().refreshChat(code);
	}

	//AppHandler Impl
	@Override
	public void processMessage(Message message) {
		this.getChatManager().processMessageReception(message);
		this.getUiManager().getController().notifyMessageReception(message);
	}

	//AppHandler Impl
	@Override
	public void processChatInitiation(ChatInit init) {
		this.getChatManager().createIfNew(init);
		this.getUiManager().getController().notifyChatInitiationFromDistant(init.getChatHashCode());
	}

	//AppHandler Impl
	@Override
	public boolean isActiveID(String identifier) {
		return this.getUserManager().isActiveUser(identifier);
	}

	@Override
	public Iterable<String> getActivesID() {
		return this.getUserManager().getActiveUsers().keySet();

	}

	//AppHandler Impl
	@Override
	public void processNewUser(User user) {
		this.getUserManager().createIfAbsent(user.getIdentifier(),user.getPseudo());
		if(this.getUiManager()!=null) this.uiManager.getController().notifyNewActiveUser(user);

	}

	//CLVModel Impl
	@Override
	public History getHistoryFor(String code) {
		return this.getChatManager().getChat(code).getHistory();
	}

	//CLVModel Impl
	@Override
	public HashMap<String, User> getActiveUsers() {
		return this.getUserManager().getActiveUsers();
	}

	//CLVModel Impl
	@Override
	public HashMap<String, Chat> getActiveChats() {
		return this.chatManager.getChats();
	}

	//CLVModel Impl
	@Override
	public Chat getChatFor(String code) {
		return this.getChatManager().getChat(code);
	}

	//AppHandler, CLVModel Impl
	@Override
	public User getUserFor(String id) {
		return this.getUserManager().getActiveUsers().get(id);
	}


}
