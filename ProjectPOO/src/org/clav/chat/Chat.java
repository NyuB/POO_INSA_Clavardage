package org.clav.chat;

import org.clav.Agent;
import org.clav.user.User;
import org.clav.utils.HashUtils;

import java.util.ArrayList;
import java.util.Comparator;

public class Chat {

	//Le mainUser est inclus dans members
	private ArrayList<User> members;
	private String chatHashCode;
	private History history;
	public Chat(ArrayList<User> members) {
		this.members = members ;
		this.history = new History();
		this.chatHashCode = HashUtils.hashUserList(members);
	}
	public void insertMessage(Message message) {
		history.insertMessage(message) ;
	}
	public void receiveMessage(Message message){
		this.insertMessage(message,false);
	}
	public void emitMessage(Message message){
		this.insertMessage(message,true);
	}

	public void insertMessage(Message message,boolean emetted){
		history.insertMessage(message);
	}

	public ArrayList<User> getMembers() {
		return members;
	}

	public ChatInit genChatInit(){
		return new ChatInit(this);
	}

	public String getChatHashCode() {
		return chatHashCode;
	}

	public History getHistory() {
		return history;
	}

	public void setHistory(History history) {
		this.history = history;
	}
}
