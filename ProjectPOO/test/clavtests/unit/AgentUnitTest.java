package clavtests.unit;

import org.clav.Agent;
import org.clav.chat.ChatManager;
import org.clav.network.NetworkManager;
import org.clav.user.User;
import org.clav.user.UserManager;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AgentUnitTest {
	@Test
	public void construct(){
		Agent agent = new Agent();
	}

	@Test
	public void setStartAndStop(){
		Agent agent = new Agent();
		agent.setChatManager(new ChatManager());

		try {
			InetAddress localhost = InetAddress.getByName("localhost");
			agent.setNetworkManager(new NetworkManager(localhost,localhost));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		agent.setUserManager(new UserManager(new User("Main","Main")));
		agent.start();
		agent.stop();

	}
}
