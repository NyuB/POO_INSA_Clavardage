package org.clav.chat;

import java.util.Date;

public class Message {
	private String userID;
	private int chatID;
	private Date date;
	private String text;

	public Message(String userID,int chatID, String text) {
		this.userID = userID;
		this.chatID = chatID;
		this.date = new Date();
		this.text = text;
	}

	public int getChatID() {
		return chatID;
	}

	public String getUserID() {
		return userID;
	}

	public Date getDate() {
		return date;
	}

	public String getText() {
		return text;
	}
}
