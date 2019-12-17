package org.clav.config;

import org.clav.Agent;
import org.clav.network.NetworkManager;
import org.clav.user.UserManager;

import java.io.*;
import java.net.InetAddress;

public class ConfigManager {
	private InetAddress localAddr;
	private InetAddress broadcastAddr;
	private String userID;
	private boolean autoSignalUDP;
	private boolean autoListenUDP;
	private boolean autoListenTCP;


	public ConfigManager(String file) {
		applyConfigFile(file);
	}

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

	public InetAddress getLocalAddr() {
		return localAddr;
	}

	public InetAddress getBroadcastAddr() {
		return broadcastAddr;
	}

	public String getUserID() {
		return userID;
	}

	public boolean isAutoSignalUDP() {
		return autoSignalUDP;
	}

	public boolean isAutoListenUDP() {
		return autoListenUDP;
	}

	public boolean isAutoListenTCP() {
		return autoListenTCP;
	}

	public void configNetworkManager(Agent agent) {
		NetworkManager networkManager = new NetworkManager(this.getLocalAddr(), this.getBroadcastAddr());
		networkManager.setAppHandler(agent);
		agent.setNetworkManager(networkManager);
		if(this.autoSignalUDP)networkManager.startUDPSignal();
		if(this.autoListenUDP)networkManager.startUDPListening();
		if(this.autoListenTCP)networkManager.startTCPListening();


	}

	public UserManager configUserManager(Agent agent) {
		return new UserManager(agent, null);//TODO
	}
}
