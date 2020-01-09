package org.clav.database;

import org.clav.chat.Chat;
import org.clav.chat.Message;

import java.util.ArrayList;


/**
 * Empty shell to proxy a database when a real one is not available
 * Does nothing
 */
public class EmptyChatStorage implements ChatStorage {
	private void log(String log){
		System.out.println("[DATA]"+log);
	}
	@Override
	public void storeChat(Chat chat) {
		log("Storing single chat");
	}

	@Override
	public Chat getChatByHashCode(String code) {
		log("Searching single chat");
		return null;
	}

	@Override
	public Iterable<Chat> readAllChats() {
		log("Reading all stored chats");
		return new ArrayList<>();
	}

	@Override
	public void storeMessage(Message message){
		log("Storing message");

	}

	@Override
	public void storeAll(Iterable<Chat> chats) {
		log("Storing all chats from list");

	}
}
