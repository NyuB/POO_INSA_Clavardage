package org.clav.chat;

import org.clav.user.User;
import org.clav.utils.HashUtils;

import java.util.ArrayList;
import java.util.Collection;

public class Chat {

	//Le mainUser est inclus dans members
	private ArrayList<String> members;
	private String chatHashCode;
	private History history;

	public Chat(Collection<String> members) {
		this.members = new ArrayList<>(members);
		this.history = new History();
		this.chatHashCode = HashUtils.hashStringList(this.members);
	}

	public void insertMessage(Message message) {
		history.insertMessage(message);

	}

	public void insertMessage(Message message, boolean emetted) {
		history.insertMessage(message);
	}

	public void receiveMessage(Message message) {
		this.insertMessage(message, false);
	}

	public void emitMessage(Message message) {
		this.insertMessage(message, true);
	}

	public ArrayList<String> getMembers() {
		return members;
	}

	public ChatInit genChatInit() {
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
