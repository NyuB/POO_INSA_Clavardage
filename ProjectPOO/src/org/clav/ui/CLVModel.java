package org.clav.ui;

import org.clav.AppHandler;
import org.clav.chat.Message;
import org.clav.user.User;

import java.util.ArrayList;

public interface CLVModel {

	void notifyMessageSending(Message message);
	void notifyMessageReception(Message message);

	void notifyChatInitiationFromUser(ArrayList<String> identifiers);
	void notifyChatCreationFromDistant(ArrayList<String> identifiers);

	void notifyNewActiveUser(User user);
	void notifyInactiveUser(User user);

	void notifyPseudoChange(User user);

}
