package org.clav.ui.mvc;

import org.clav.chat.Message;
import org.clav.user.User;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public interface CLVController {

	void notifyMessageSending(String code,String txt);
	void notifyMessageReception(Message message);

	void notifyImageSending(String code, BufferedImage img);

	void notifyChatInitiationFromUser(ArrayList<String> distantIdentifiers);
	void notifyChatInitiationFromDistant(String code);
	void notifyChatClosedByUser(String code);

	void notifyChatStorage(String code);
	void notifyChatStorage();

	void notifyNewActiveUser(User user);
	void notifyInactiveUser(String id);

	void notifyInvalidPseudo();
	void notifyMainUserPseudoChange(String pseudo);

	void assignView(CLVView view);
	void notifySettingsChange();

}
