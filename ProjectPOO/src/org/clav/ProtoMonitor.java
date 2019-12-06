package org.clav;

import org.clav.network.NetworkManager;
import org.clav.network.ProtocolInit;
import org.clav.network.protocolsimpl.tcp.TCPListenerProtocol;
import org.clav.network.protocolsimpl.udp.ActivitySignalProtocol;
import org.clav.network.protocolsimpl.udp.ActivitySignalProtocolInit;
import org.clav.network.protocolsimpl.udp.UDPListenerProtocol;
import org.clav.user.User;
import org.clav.user.UserManager;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ProtoMonitor {
	public static void main(String[] args) {
		Scanner in  = new Scanner(System.in);
		System.out.println("Enter user name");
		String name = in.nextLine();
		Agent agent = new Agent();
		User mainUser = new User(name,name);
		UserManager userManager = new UserManager(agent,mainUser);
		NetworkManager networkManager = null;
		String line;
		try {
			InetAddress localAddr = InetAddress.getByName("0.0.0.0");
			System.out.println("Enter broadcast address");
			line = in.nextLine();
			InetAddress broadcastAddr = InetAddress.getByName(line);
			networkManager = new NetworkManager(localAddr,broadcastAddr);
			networkManager.setRelatedAgent(agent);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		agent.setNetworkManager(networkManager);
		agent.setUserManager(userManager);
		networkManager.executeProtocol(new ActivitySignalProtocol(new ActivitySignalProtocolInit(networkManager,userManager)));
		networkManager.executeProtocol(new UDPListenerProtocol(new ProtocolInit(networkManager)));
		networkManager.executeProtocol(new TCPListenerProtocol(new ProtocolInit(networkManager)));

		JFrame frame = new JFrame("NETWORK MONITOR");
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy=0;
		constraints.fill = GridBagConstraints.BOTH;
		JTextArea area = new JTextArea();
		panel.add(area,constraints);
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);


	}
}
