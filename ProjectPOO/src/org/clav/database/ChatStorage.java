package org.clav.database;

import org.clav.chat.Chat;
import org.clav.chat.Message;

public interface ChatStorage {
	void storeChat(Chat chat);

	Chat getChatByHashCode(String code);

	Iterable<Chat> readAllChats();

	void storeAll(Iterable<Chat> chats);

	void storeMessage(Message message);
}
