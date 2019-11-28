package org.clav.chat;

import java.util.Date;

public class Message {
	private String userID;
	private Date date;
	private String text;

	public Message(String userID, Date date, String text) {
		this.userID = userID;
		this.date = date;
		this.text = text;
	}
}
