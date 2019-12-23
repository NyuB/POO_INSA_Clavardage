package clavtests.unit;

import org.clav.Agent;
import org.clav.chat.ChatManager;
import org.clav.network.NetworkManager;
import org.clav.user.User;
import org.clav.user.UserManager;

import org.junit.Assert;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkManagerUnitTest {

	@Test
	public void constructorTest() throws UnknownHostException {
		NetworkManager networkManager;
		InetAddress localhost = InetAddress.getByName("localhost");
		networkManager = new NetworkManager(localhost, localhost);


	}

	@Test
	public void aloneStartAndStopSignal() throws InterruptedException, UnknownHostException {
		int initialCount = Thread.activeCount();
		NetworkManager networkManager;
		InetAddress localhost = InetAddress.getByName("localhost");
		networkManager = new NetworkManager(localhost, localhost);
		networkManager.startUDPSignal();
		Thread.sleep(1000);
		networkManager.stopActivitySignal();
		networkManager.startUDPSignal();
		Thread.sleep(1000);
		networkManager.stopActivitySignal();
	}

	@Test
	public void withAgentStartAndStopSignal() throws InterruptedException, UnknownHostException {
		int initialCount = Thread.activeCount();
		Agent agent = new Agent();
		agent.setChatManager(new ChatManager());
		InetAddress localhost = InetAddress.getByName("localhost");
		NetworkManager networkManager =  new NetworkManager(localhost, localhost);
		agent.setNetworkManager(networkManager);
		networkManager.setAppHandler(agent);
		agent.setUserManager(new UserManager(new User("Main", "Main")));
		networkManager.startUDPSignal();
		Thread.sleep(1000);
		Assert.assertEquals("UDP thread launched and should be running",initialCount+1,Thread.activeCount());
		networkManager.stopActivitySignal();
		Thread.sleep(1000);
		Assert.assertEquals("UDP thread stopped",initialCount,Thread.activeCount());
	}


}
