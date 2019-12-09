package org.clav;
import org.clav.debug.graphic.DebugModel;
import org.clav.network.NetworkManager;
import org.clav.user.User;
import org.clav.user.UserManager;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ProtoMonitor {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		System.out.println("Enter user name");
		String name = in.nextLine();

		Agent agent = new Agent();
		User mainUser = new User(name, name);
		UserManager userManager = new UserManager(agent, mainUser);
		NetworkManager networkManager = null;
		String line;
		try {
			InetAddress localAddr = InetAddress.getByName("0.0.0.0");
			System.out.println("Enter broadcast address");
			line = in.nextLine();
			//line = "localhost";
			InetAddress broadcastAddr = InetAddress.getByName(line);
			networkManager = new NetworkManager(localAddr, broadcastAddr);
			networkManager.setRelatedAgent(agent);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		DebugModel debugModel = new DebugModel(agent);

		networkManager.plug(debugModel);
		debugModel.debugFrame.setVisible(true);
		agent.setNetworkManager(networkManager);
		agent.setUserManager(userManager);
		networkManager.startUDPListening();
		networkManager.startUDPSignal();
		networkManager.startTCPListening();

		while ((line = in.nextLine()) != null) {
			String[] cmd = line.split("[\\s]+");
			switch (cmd[0]) {
				case "ADD":
					if(cmd.length>1)debugModel.debugFrame.addChat(cmd[1]);
					break;
				case "DEL":
					if(cmd.length>1)debugModel.debugFrame.delChat(cmd[1]);
				case "SEND":
					if (cmd.length >= 3) {
						StringBuilder sb = new StringBuilder(cmd[2]);
						for(int i=3;i<cmd.length;i++){
							sb.append(" ");
							sb.append(cmd[i]);
						}
						agent.getNetworkManager().TCP_IP_send(cmd[1],sb.toString());
					}
					break;
				default:
					System.out.println("UNKNOWN CMD");
			}
		}
	}
}
