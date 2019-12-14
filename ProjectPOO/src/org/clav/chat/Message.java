package org.clav.chat;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
	private String userID;//Sender ID
	private String chatHashCode;
	private Date date;
	private String text;

	public Message(String userID, String chatHashCode, String text) {
		this.userID = userID;
		this.chatHashCode = chatHashCode;
		this.date = new Date();
		this.text = text;
	}

	/**
	 * @return ID of the message's sender
	 */
	public String getUserID() {
		return userID;
	}

	public Date getDate() {
		return date;
	}

	public String getText() {
		return text;
	}

	public String getChatHashCode() {
		return chatHashCode;
	}
}
