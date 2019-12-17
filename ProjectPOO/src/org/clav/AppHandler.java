package org.clav;

import org.clav.chat.ChatInit;
import org.clav.chat.Message;
import org.clav.user.User;

import java.util.ArrayList;

public interface AppHandler {
	User getMainUser();

	void sendMessage(Message message);

	void initiateChat(ArrayList<User> distantMembers);

	void processMessage(Message message);

	void processChatInitiation(ChatInit init);

	void processNewUser(User user);

	boolean isActiveID(String identifier);

	Iterable<String> getActivesID();

	User getUserFor(String id);

}
