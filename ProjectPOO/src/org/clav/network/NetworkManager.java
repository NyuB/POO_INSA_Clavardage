package org.clav.network;

import org.clav.user.User;

import java.net.InetAddress;
import java.util.HashMap;

public class NetworkManager {
	private InetAddress networkAddress;
	private InetAddress broadcastAddress;
	private HashMap<User,InetAddress> addrMap;
	public void connect(){
		//TODO
	}
	public void broadcast(int bytes[]){
		//TODO
	}
	public void TCP_IP_send(Object data, InetAddress address){
		//TODO
	}

	public NetworkManager(InetAddress networkAddress, InetAddress broadcastAddress) {
		this.networkAddress = networkAddress;
		this.broadcastAddress = broadcastAddress;
		this.addrMap = new HashMap<>();
	}
}
