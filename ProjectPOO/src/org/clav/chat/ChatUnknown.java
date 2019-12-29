package org.clav.chat;

import java.io.Serializable;

public class ChatUnknown implements Serializable {
	private String id;
	private String chatHashcode;

	public ChatUnknown(String id, String chatHashcode) {
		this.id = id;
		this.chatHashcode = chatHashcode;
	}

	public String getId() {
		return id;
	}

	public String getChatHashcode() {
		return chatHashcode;
	}
}
