package org.clav.chat;

import org.clav.user.User;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatInit implements Serializable {
	public enum Mode{
		FIRST,
		RESPONSE;
	}
	int id;
	String messageSender;
	ArrayList<String> identifiers;
	Mode mode;
	public ChatInit(Chat chat) {
		this.id = chat.getChatID();
		this.identifiers = new ArrayList<>();
		for(User u : chat.getMembers()){
			this.identifiers.add(u.getIdentifier());
		}
		this.messageSender = chat.getMainUserIdentifier();

	}

	public ArrayList<String> getIdentifiers() {
		return identifiers;
	}

	public Mode getMode() {
		return mode;
	}

	public int getId() {
		return id;
	}

	public String getMessageSender() {
		return messageSender;
	}
}
