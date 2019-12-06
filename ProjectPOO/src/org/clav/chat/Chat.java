package org.clav.chat;

import org.clav.network.NetworkManager;
import org.clav.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Chat {
	
	private User mainUser ;
	private NetworkManager networkManager;
	
	//Le mainUser n'est pas dans le members !!
	private ArrayList<User> members;
	private History history;
	
	public Chat(ArrayList<User> members, NetworkManager network, User user) {
		this.mainUser = user ;
		this.networkManager = network ;
		this.members = members ;
		this.history = new History() ;
		
		for(User u : members) {
			networkManager.initiateConnectionTCP(u.getIdentifier());
		}
		
	}
	
	public void sendMessage(String message) {
		for(User u : members) {
			networkManager.TCP_IP_send(u.getIdentifier(), message) ;
		}
		//TODO gerer le cas où l'envoi foire
		history.insertMessage(new Message(mainUser.getIdentifier() ,message)) ;
	}
	
	public void loadHistory() {
		//TODO DB Local
	}
}
