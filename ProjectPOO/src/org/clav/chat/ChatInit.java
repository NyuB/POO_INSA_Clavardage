package org.clav.chat;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatInit implements Serializable {
	private ArrayList<String> identifiers;
	private String chatHashCode;
	public ChatInit(Chat chat) {
		this.identifiers = new ArrayList<>();
		for(String id : chat.getMembers()){
			this.identifiers.add(id);
		}
		this.chatHashCode = chat.getChatHashCode();
	}
	public ArrayList<String> getIdentifiers() {
		return identifiers;
	}

	public String getChatHashCode() {
		return this.chatHashCode;
	}
}
