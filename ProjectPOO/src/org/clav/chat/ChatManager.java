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

	private int createChat(ArrayList<User> members){
		Chat chat = new Chat(members,this.minIDAvailable,this.getRelatedAgent());
		this.chats.put(this.minIDAvailable,chat);
		int res= this.minIDAvailable;
		while (this.chats.containsKey(this.minIDAvailable)){
			this.minIDAvailable++;
		}
		return res;
	}
	private int createChat(ChatInit init){
		ArrayList<User> users = new ArrayList<>();
		for(String id:init.getIdentifiers()){
			users.add(this.getRelatedAgent().getUserManager().getActiveUsers().get(id));

		}
		return this.createChat(users);
	}
	private boolean haveSameMembers(Chat chat,ArrayList<String> identifiers){
		if(chat.getMembers().size()!=identifiers.size())return false;
		for(User u:chat.getMembers()){
			if(!identifiers.contains(u.getIdentifier()))return false;
		}
		return true;
	}
	private int createChatIfAbsent(ChatInit init){
		for(int i:this.chats.keySet()){
			if(this.haveSameMembers(this.chats.get(i),init.getIdentifiers())){
				return this.chats.get(i).getChatID();
			}
		}
		return this.createChat(init);

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
