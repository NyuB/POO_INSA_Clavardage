package org.clav.database;

import org.clav.chat.Chat;
import org.clav.chat.Message;

/**
 * Provides methods to read and store chat data ino/from a form of database
 */
public interface ChatStorage {

	void storeChat(Chat chat);

	Chat getChatByHashCode(String code);

	Iterable<Chat> readAllChats();

	void storeAll(Iterable<Chat> chats);

	void storeMessage(Message message);
}
