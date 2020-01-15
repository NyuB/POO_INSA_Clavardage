package org.clav.config;

import org.clav.Agent;
import org.clav.chat.ChatManager;
import org.clav.network.NetworkManager;
import org.clav.network.server.HttpPresenceClient;
import org.clav.ui.GUIManager;
import org.clav.user.User;
import org.clav.user.UserManager;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ConfigManager {
	private Config config ;
	public ConfigManager() {
		FileInputStream file;
		try {
			file = new FileInputStream("Objconfig.ser");
			ObjectInputStream in = new ObjectInputStream(file) ;
			this.config = (Config) in.readObject() ;
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	private void applyConfigFile(String file) {
		File settingFile = new File(file);

		try {
			BufferedReader reader = new BufferedReader(new FileReader(settingFile));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] param = line.split("[\\s]+");
				switch (param[0]) {
					case "LOCAL":
						if (param.length > 1) this.localAddr = InetAddress.getByName(param[1]);
						break;
					case "BROAD":
						if (param.length > 1) this.broadcastAddr = InetAddress.getByName(param[1]);
						break;
					case "ID":
						if (param.length > 1) this.userID = param[1];
						break;
					case "UDP":
						for (int i = 1; i < param.length; i++) {
							String arg = param[i];
							if (arg.equals("LISTEN")) this.autoListenUDP = true;
							else if (arg.equals("SIGNAL")) this.autoSignalUDP = true;
						}
						break;
					case "TCP":
						for (int i = 1; i < param.length; i++) {
							String arg = param[i];
							if (arg.equals("LISTEN")) this.autoListenTCP = true;
						}
						break;

					default:
						break;


				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	*/

	public Config getConfig() {
		return this.config ;
	}

	public void configAgent(Agent agent) {
		NetworkManager networkManager = new NetworkManager(this.config.getLocalAddr(), this.config.getBroadcastAddr());
		networkManager.setAppHandler(agent);
		agent.setNetworkManager(networkManager);

		String userId = JOptionPane.showInputDialog(null,"Enter your unique identifier. It should have been given to you by the compagny and it is not your pseudo, you will be able to choose it later");
		UserManager userManager = new UserManager(new User(userId,userId));
		userManager.setAppHandler(agent);
		agent.setUserManager(userManager);
		ChatManager chatManager = new ChatManager();
		agent.setChatManager(chatManager);
		chatManager.setAppHandler(agent);
		GUIManager guiManager = new GUIManager(agent,agent);
		agent.setGUIManager(guiManager);
	}

	public void launchAgent(Agent agent){
		agent.getGUIManager().start();
		if(this.config.isAutoConnectServlet())agent.getNetworkManager().linkPresenceServer(new HttpPresenceClient(config.getServerUrl()));
		if(this.config.isAutoSignalUDP())agent.getNetworkManager().startUDPSignal();
		if(this.config.isAutoListenUDP())agent.getNetworkManager().startUDPListening();
		if(this.config.isAutoListenTCP())agent.getNetworkManager().startTCPListening();

	}

	public UserManager configUserManager(Agent agent) {
		return new UserManager(null);//TODO
	}
}
