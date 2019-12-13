package org.clav;

import org.clav.chat.Message;

import java.util.ArrayList;

public interface AppHandler {
	void sendMessage(Message message);
	void initiateChat(ArrayList<String> members);

	void processMessage(Message message);
	void processChatInitiation(ArrayList<String> members);
}
