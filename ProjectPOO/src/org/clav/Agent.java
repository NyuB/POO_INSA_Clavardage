package org.clav;

import org.clav.chat.*;
import org.clav.config.ConfigManager;
import org.clav.network.CLVPacket;
import org.clav.network.CLVPacketFactory;
import org.clav.network.NetworkManager;
import org.clav.ui.GUIManager;
import org.clav.ui.mvc.CLVModel;
import org.clav.user.PseudoRejection;
import org.clav.user.User;
import org.clav.user.UserManager;
import org.clav.utils.HashUtils;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Central class of the CLV application
 * Divided into diverse manager classes handling the main needs of the application(network, chat history, users identification, ui...)
 * The agent serves as a hub between the managers
 */
public class Agent implements AppHandler, CLVModel {
	private NetworkManager networkManager;
	private ChatManager chatManager;
	private UserManager userManager;
	private ConfigManager configManager;
	private GUIManager GUIManager;

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

	public GUIManager getGUIManager() {
		return GUIManager;
	}

	public void setGUIManager(GUIManager GUIManager) {
		this.GUIManager = GUIManager;
	}

	public void start() throws NullPointerException {
		if(this.networkManager!=null && this.chatManager!=null && this.userManager!=null) {
			this.GUIManager = new GUIManager(this, this);
			this.GUIManager.start();
			this.GUIManager.getView().refreshUsers();
		}
		else{
			throw new NullPointerException();
		}
	}
	public void stop(){
		this.GUIManager.getView().turnOff();
	}
	private void log(String txt){
		System.out.println("[APPH]"+txt);
	}


	//AppHandler,CLVModel
	@Override
	public User getMainUser() {
		return getUserManager().getMainUser();
	}

	//AppHandler
	@Override
	public void sendMessage(Message message) {
		message.setUserID(this.getMainUser().getIdentifier());
		CLVPacket packet = CLVPacketFactory.gen_MSG(message);
		boolean selfTalk = true;
		boolean success = false;
		for (User u : this.getChatManager().getChat(message.getChatHashCode()).getMembers()) {
			if(u.getIdentifier().equals(this.getMainUser().getIdentifier())){
				this.log("Skipping main user");
			}
			else {
				success = success || this.getNetworkManager().TCP_IP_send(u.getIdentifier(), packet);
				selfTalk = false;
			}
		}
		if(selfTalk){
			success = this.getNetworkManager().TCP_IP_send(this.getMainUser().getIdentifier(),packet);
		}
		if(success) {
			this.getChatManager().processMessageEmission(message);
			this.getGUIManager().getView().refreshChat(message.getChatHashCode());
		}
	}

	//AppHandler
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
		this.getGUIManager().getView().refreshChat(code);
	}

	@Override
	public void processChatClosedByUser(String code){
		for(User u : chatManager.getChat(code).getMembers()){
			if( this.getUserManager().isActiveUser(u.getIdentifier())){
				this.getNetworkManager().closeConnectionTCP(u.getIdentifier());
			}
		}
	}

	//AppHandler
	@Override
	public void processMessage(Message message) {
		this.getChatManager().processMessageReception(message);
		this.getGUIManager().getController().notifyMessageReception(message);
	}

	//AppHandler
	@Override
	public void processChatInitiation(ChatInit init) {
		this.getChatManager().createIfNew(init);
		this.getGUIManager().getController().notifyChatInitiationFromDistant(init.getChatHashCode());
	}

	//AppHandler
	@Override
	public boolean isActiveID(String identifier) {
		return this.getUserManager().isActiveUser(identifier);
	}

	//AppHandler
	@Override
	public Iterable<String> getActivesID() {
		return this.getUserManager().getActiveUsers().keySet();

	}

	//AppHandler
	@Override
	public void processNewUser(User user) {
		this.getUserManager().processActive(user);
		if (this.getGUIManager() != null) this.GUIManager.getController().notifyNewActiveUser(user);

	}

	//AppHandler
	@Override
	public void processUserInaction(String id) {
		this.getNetworkManager().closeConnectionTCP(id);
		this.getGUIManager().getController().notifyInactiveUser(id);

	}

	//AppHandler
	@Override
	public void processPseudoRejection(PseudoRejection rejection) {
		synchronized (this.getMainUser().getPseudo()) {
			if (rejection.getDate().before(this.getMainUser().getPseudoDate()) && rejection.getPseudo().equals(this.getMainUser().getPseudo())) {
				this.getNetworkManager().stopActivitySignal();
				this.GUIManager.getController().notifyInvalidPseudo();
				this.getNetworkManager().startUDPSignal();
			}
		}

	}

	//AppHandler
	@Override
	public boolean processMainUserPseudoChange(String newPseudo) {
		return this.getUserManager().changeMainUserPseudo(newPseudo);
	}

	//CLVModel
	@Override
	public History getHistoryFor(String code) {
		return this.getChatManager().getChat(code).getHistory();
	}

	//CLVModel
	@Override
	public HashMap<String, User> getActiveUsers() {
		return this.getUserManager().getActiveUsers();
	}

	//CLVModel
	@Override
	public HashMap<String, Chat> getActiveChats() {
		return this.chatManager.getChats();
	}

	//CLVModel
	@Override
	public Chat getChatFor(String code) {
		return this.getChatManager().getChat(code);
	}

	//AppHandler, CLVModel
	@Override
	public User getUserFor(String identifier) {
		return this.getUserManager().getActiveUsers().get(identifier);
	}


}
