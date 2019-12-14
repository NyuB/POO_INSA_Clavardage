package org.clav;

import org.clav.chat.ChatInit;
import org.clav.chat.Message;
import org.clav.user.User;

import java.util.ArrayList;

public interface AppHandler {
	User getMainUser();

	void sendMessage(Message message);

	void initiateChat(ChatInit init);

	void processMessage(Message message);

	void processChatInitiation(ChatInit init);


}
