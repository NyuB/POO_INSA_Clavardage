package org.clav.config;

import org.clav.Agent;
import org.clav.network.NetworkManager;
import org.clav.user.UserManager;

import java.io.*;
import java.net.InetAddress;

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

	public void configNetworkManager(Agent agent) {
		NetworkManager networkManager = new NetworkManager(this.config.getLocalAddr(), this.config.getBroadcastAddr());
		networkManager.setAppHandler(agent);
		agent.setNetworkManager(networkManager);
		if(this.config.isAutoSignalUDP())networkManager.startUDPSignal();
		if(this.config.isAutoListenUDP())networkManager.startUDPListening();
		if(this.config.isAutoListenTCP())networkManager.startTCPListening();


	}

	public UserManager configUserManager(Agent agent) {
		return new UserManager(agent, null);//TODO
	}
}
