package org.clav.ui.mvc;

import org.clav.chat.Message;
import org.clav.user.User;

import java.util.ArrayList;

public interface CLVController {

	void notifyMessageSending(String code,String txt);
	void notifyMessageReception(Message message);

	void notifyChatInitiationFromUser(ArrayList<String> distantIdentifiers);
	void notifyChatInitiationFromDistant(String code);

	void notifyNewActiveUser(User user);
	void notifyInactiveUser(User user);

	void notifyPseudoChangeFromDistant(User user);
	void notifyMainUserPseudoChange(String pseudo);
	void notifyMainUserPseudoRejected(String old);

	void assignView(CLVView view);

}
