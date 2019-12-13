package org.clav.chat;

import org.clav.Agent;
import org.clav.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatManager {
	private int minIDAvailable;
	private Agent relatedAgent;
	private HashMap<Integer,Chat> chats;
	private HashMap<String,HashMap<Integer,Integer>> foreignIdMap;

	public ChatManager() {
		this.chats = new HashMap<>();
		this.foreignIdMap = new HashMap<>();
		this.minIDAvailable = 0;
	}

	public void createChat(ArrayList<User> members){
		Chat chat = new Chat(members,this.minIDAvailable,this.getRelatedAgent());
		this.chats.put(this.minIDAvailable,chat);
		while (this.chats.containsKey(this.minIDAvailable)){
			this.minIDAvailable++;
		}
	}
	public void leaveChat(Chat chat){
		this.chats.remove(chat.getChatID());
		this.minIDAvailable = Integer.min(chat.getChatID(),this.minIDAvailable);

	}
	public void updateMissingHistory(Chat chat){

	}
	public void sendMissingHistory(Chat chat, User user){


	}
	private Chat getChatByForeign(String identifier,int foreignId){
		return this.chats.get(this.foreignIdMap.get(identifier).get(foreignId));
	}
	public void insertMessage(Message message){
		Chat relatedCHat = this.getChatByForeign(message.getUserID(),message.getChatID());
		relatedCHat.receiveMessage(message);
	}

	public void setRelatedAgent(Agent relatedAgent) {
		this.relatedAgent = relatedAgent;
	}

	public int getMinIDAvailable() {
		return minIDAvailable;
	}

	public Agent getRelatedAgent() {
		return relatedAgent;
	}
}
