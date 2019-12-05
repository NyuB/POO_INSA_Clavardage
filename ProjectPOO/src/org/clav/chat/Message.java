package org.clav.chat;

import java.util.Date;

public class Message {
	private String userID;
	private Date date;
	private String text;

	public Message(String userID, String text) {
		this.userID = userID;
		this.date = new Date();
		this.text = text;
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
