package org.clav;

import org.clav.chat.*;
import org.clav.config.Config;
import org.clav.config.ConfigManager;
import org.clav.network.CLVPacket;
import org.clav.network.CLVPacketFactory;
import org.clav.network.NetworkManager;
import org.clav.network.server.HttpPresenceClient;
import org.clav.ui.GUIManager;
import org.clav.ui.mvc.CLVModel;
import org.clav.user.PseudoRejection;
import org.clav.user.User;
import org.clav.user.UserManager;
import org.clav.utils.HashUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

	private static Agent agent = null;
	public static Agent getInstance(){
		if(agent == null){
			agent = new Agent();
			agent.configManager = new ConfigManager();
		}
		return agent;
	}
	public static void launchAgent() {
		getInstance().configManager.launchAgent(getInstance());
	}

	public Agent() {

	}

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
		if (this.networkManager != null && this.chatManager != null && this.userManager != null) {
			if (this.getGUIManager() == null) this.GUIManager = new GUIManager(this, this);
			this.GUIManager.start();
		} else {
			throw new NullPointerException();
		}
	}

	public void stop() {
		this.GUIManager.getView().turnOff();
	}

	private void log(String txt) {
		System.out.println("[APPH]" + txt);
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
		for (String id : this.getChatManager().getChat(message.getChatHashCode()).getMembers()) {
			if (id.equals(this.getMainUser().getIdentifier())) {//Don't talk to yourself
				this.log("Skipping main user");
			} else {
				selfTalk = false;
				if (this.isActiveID(id)) {
					boolean sendres = this.getNetworkManager().TCP_IP_send(id, packet);
					success = success || sendres;
				}

			}
		}
		if (selfTalk) {
			success = this.getNetworkManager().TCP_IP_send(this.getMainUser().getIdentifier(), packet);
		}
		if (success) {//If at least one user has received the message
			this.getChatManager().processMessageEmission(message);
			this.getGUIManager().getView().refreshChat(message.getChatHashCode());
		}
	}

	@Override
	public void processImage(BufferedImage image) {
		log("Processing image");
		if (!Files.isDirectory(Paths.get("img"))) {
			try {
				Files.createDirectories(Paths.get("img"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			int i = 0;
			File file = new File("img/tmp.jpg");
			while (file.exists()) {
				i++;
				file = new File("img/tmp_" + i + ".jpg");
			}
			ImageIO.write(image, "jpg", file);
			JOptionPane.showMessageDialog(null,"You received an image, it has been stored in img/tmp" + i + ".jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void sendImage(String code, BufferedImage image) {
		CLVPacket packet = CLVPacketFactory.gen_IMG(image);
		boolean selfTalk = true;
		for (String id : this.getChatManager().getChat(code).getMembers()) {
			if (id.equals(this.getMainUser().getIdentifier())) {//Don't talk to yourself
				this.log("Skipping main user");
			} else {
				selfTalk = false;
				if (this.isActiveID(id)) {
					this.getNetworkManager().TCP_IP_send(id, packet);
				}
			}
		}
		if (selfTalk) {
			this.getNetworkManager().TCP_IP_send(this.getMainUser().getIdentifier(), packet);
		}
	}

	//AppHandler
	@Override
	public void initiateChat(ArrayList<String> distantMembers) {
		ArrayList<String> withMain = new ArrayList<>(distantMembers);
		withMain.add(this.getMainUser().getIdentifier());
		String code = HashUtils.hashStringList(withMain);
		if (!this.getChatManager().getChats().containsKey(code)) {
			this.getChatManager().createChat(withMain);
		}
		ChatInit init = this.getActiveChats().get(code).genChatInit();
		CLVPacket packet = CLVPacketFactory.gen_CHI(init);
		for (String id : distantMembers) {
			this.getNetworkManager().TCP_IP_send(id, packet);
		}
		this.getGUIManager().getView().refreshChat(code);
	}

	@Override
	public void processChatClosedByUser(String code) {
		for (String id : chatManager.getChat(code).getMembers()) {
			if (this.getUserManager().isActiveUser(id)) {
				this.getNetworkManager().closeConnectionTCP(id);
			}
		}
	}

	//AppHandler
	@Override
	public void processMessage(Message message) {
		if (this.chatManager.containsChat(message.getChatHashCode())) {
			this.getChatManager().processMessageReception(message);
			this.getGUIManager().getController().notifyMessageReception(message);
		} else {
			this.networkManager.TCP_IP_send(message.getUserID(), CLVPacketFactory.gen_UNK(this.getMainUser().getIdentifier(), message.getChatHashCode()));
		}
	}

	//AppHandler
	@Override
	public void processChatInitiation(ChatInit init) {
		this.getChatManager().createIfNew(init);
		this.getGUIManager().getController().notifyChatInitiationFromDistant(init.getChatHashCode());
	}

	@Override
	public void processChatUnknownRequest(ChatUnknown chatUnknown) {
		//Informs distant agent of chat creation information
		this.networkManager.TCP_IP_send(chatUnknown.getId(), CLVPacketFactory.gen_CHI(this.getChatFor(chatUnknown.getChatHashcode()).genChatInit()));
		//Send each message of the chat to the distant user
		ArrayList<Message> snapshot = new ArrayList<>(this.getHistoryFor(chatUnknown.getChatHashcode()).getMessageHistory());//copy of current history to free it(getMessageHistory is a synchronized method)
		for (Message message : snapshot) {
			this.networkManager.TCP_IP_send(chatUnknown.getId(), CLVPacketFactory.gen_MSG(message));
		}
	}

	//AppHandler, CLVModel
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
		if (!user.getPseudo().equals(this.getMainUser().getPseudo())) {
			this.getUserManager().processActive(user);
			if (this.getGUIManager() != null) this.GUIManager.getController().notifyNewActiveUser(user);
		} else {
			//TODO Currently handled at network level, move it here?
		}
	}

	//AppHandler
	@Override
	public void processUserInaction(String id) {
		this.getNetworkManager().closeConnectionTCP(id);
		this.getNetworkManager().deleteAddrFor(id);
		this.getGUIManager().getController().notifyInactiveUser(id);

	}

	//AppHandler
	@Override
	public boolean checkRejection(User user) {
		//A different user(i.e. different id) chose the same pseudo
		boolean rejected = getMainUser().getPseudo().equals(user.getPseudo()) && !getMainUser().getIdentifier().equals(user.getIdentifier());
		return rejected;
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

	//AppHandler
	@Override
	public void applyConfig() {
		Config config = this.getConfigManager().getConfig();
		this.getNetworkManager().setBroadcastAddress(config.getBroadcastAddr());
		this.getNetworkManager().getPresenceClients().clear();
		if(config.isAutoConnectServlet()){
			this.getNetworkManager().linkPresenceServer(new HttpPresenceClient(config.getServerUrl()));
		}
	}

	@Override
	public void storeChat(String code) {
		this.chatManager.save(code);
	}

	@Override
	public void storeChats() {
		this.chatManager.save();
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

	//CLVModel
	@Override
	public Config getConfig() {
		return this.getConfigManager().getConfig();
	}


}
