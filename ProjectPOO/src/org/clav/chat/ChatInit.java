package org.clav.chat;

import org.clav.user.User;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatInit implements Serializable {
	int id;
	ArrayList<String> identifiers;

	public ChatInit(Chat chat) {
		this.id = chat.getChatID();
		this.identifiers = new ArrayList<>();
		for(User u : chat.getMembers()){
			this.identifiers.add(u.getIdentifier());
		}

	}

	public ArrayList<String> getIdentifiers() {
		return identifiers;
	}
}
