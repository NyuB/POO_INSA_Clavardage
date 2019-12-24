package org.clav.ui.components;

import org.clav.ui.mvc.CLVController;
import org.clav.user.User;

import javax.swing.*;

public interface ActiveUsersDisplay {
	void refreshUsers(Iterable<User> users, CLVController clvController);

	void removeUser(User user);

	JComponent getComponent();
}
