package org.clav.chat;

import org.clav.Agent;
import org.clav.user.User;

import java.util.ArrayList;
public class Chat {
	
	private Agent agent ;
	private int chatID;

	public int getChatID() {
		return chatID;
	}

	//Le mainUser n'est pas dans le members !!
	private ArrayList<User> members;
	private History history;
	
	public Chat(ArrayList<User> members, int chatID,Agent agent) {
		this.chatID = chatID;
		this.agent = agent ;
		this.members = members ;
		this.history = new History() ;
	}

	
	public void sendMessage(String message) {
		for(User u : members) {
			agent.getNetworkManager().TCP_IP_send_str(u.getIdentifier(), message) ;
		}
		history.insertMessage(new Message(agent.getUserManager().getMainUser().getIdentifier() ,getChatID(),message)) ;
	}
	public void receiveMessage(Message message){
		this.history.insertMessage(message);
	}

	public ArrayList<User> getMembers() {
		return members;
	}
	public ChatInit genChatInit(){
		return new ChatInit(this);
	}

	public void loadHistory() {
		//TODO DB Local
	}
	public String getMainUserIdentifier(){
		return this.agent.getMainUser().getIdentifier();
	}
}
