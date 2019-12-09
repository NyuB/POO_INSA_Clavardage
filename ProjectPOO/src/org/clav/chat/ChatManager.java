package org.clav.chat;

import org.clav.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatManager {
	private HashMap<Integer,Chat> chats;
	private HashMap<Integer,Integer> idMap;

	public ChatManager() {
		this.chats = new HashMap<>();
		this.idMap = new HashMap<>();
	}

	public Chat createChat(ArrayList<User> members){
		//TODO
		return null;
	}
	public void leaveChat(Chat chat){
		//TODO

	}
	public void updateMissingHistory(Chat chat){

	}
	public void sendMissingHistory(Chat chat, User user){


	}
	private Chat getChatFor(int foreignId){
		return this.chats.get(this.idMap.get(foreignId));
	}
	public void insertMessage(Message message){
		Chat relatedCHat = this.getChatFor(message.getChatID()); //TODO
	}


}
