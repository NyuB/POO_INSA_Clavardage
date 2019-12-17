package org.clav.ui.mvc;

import org.clav.chat.Chat;
import org.clav.chat.History;
import org.clav.user.User;
import java.util.Collection;
import java.util.HashMap;


public interface CLVModel {
	HashMap<String,Chat> getActiveChats();
	History getHistoryFor(String code);
	Chat getChatFor(String code);
	HashMap<String,User> getActiveUsers();
	User getUserFor(String id);
	User getMainUser();
}
