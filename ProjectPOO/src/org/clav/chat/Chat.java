package org.clav.chat;

import org.clav.user.User;

import java.util.HashMap;

public class Chat {
	private HashMap<String, User> members;
	private History history;
	
	public Chat(HashMap<String, User> members) {		
		this.members = members ;
		this.history = new History() ;
	}
	
	public void loadHistory() {
		//TODO DB Local
	}
}
