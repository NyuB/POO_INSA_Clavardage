package org.clav.ui.components;

import org.clav.ui.mvc.CLVController;
import org.clav.ui.mvc.CLVModel;
import org.clav.user.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ActiveUsersPanel extends JPanel {
	private HashMap<String, JButton> userButtons;
	public ActiveUsersPanel() {
		super(new GridLayout(0, 1));
		this.userButtons = new HashMap<>();
	}

	public void refreshUsers(Iterable<User> users) {
		this.userButtons.clear();
		for (User user : users) {
			this.userButtons.put(user.getIdentifier(), new JButton(user.getPseudo()));
		}
		this.checkDisplay();
	}
	public void refreshUsers(Iterable<User> users, CLVController clvController){
		refreshUsers(users);
		for(JButton b : this.userButtons.values()){
			b.addActionListener(l->{
				ArrayList<String> ids = new ArrayList<>();
				ids.add(b.getText());
				clvController.notifyChatInitiationFromUser(ids);
			});
		}
	}

	private void checkDisplay() {
		this.removeAll();
		this.setLayout(new GridLayout(this.userButtons.size(), 1));
		for (JButton b : this.userButtons.values()) {
			this.add(b);
		}
		this.revalidate();
	}

	public void removeUser(User user) {
		if (this.userButtons.containsKey(user.getIdentifier())) {
			this.userButtons.remove(user.getIdentifier());
			this.checkDisplay();
		}
	}
}
