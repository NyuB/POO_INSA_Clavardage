package org.clav.chat;

import org.clav.Agent;
import org.clav.network.NetworkManager;
import org.clav.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Chat {
	
	private Agent agent ;
	
	//Le mainUser n'est pas dans le members !!
	private ArrayList<User> members;
	private History history;
	
	public Chat(ArrayList<User> members, Agent agent) {
		this.agent = agent ;
		this.members = members ;
		this.history = new History() ;
	}
	
	public void sendMessage(String message) {
		for(User u : members) {
			agent.getNetworkManager().TCP_IP_send(u.getIdentifier(), message) ;
		}
		history.insertMessage(new Message(agent.getUserManager().getMainUser().getIdentifier() ,message)) ;
	}
	
	public void loadHistory() {
		//TODO DB Local
	}
}
