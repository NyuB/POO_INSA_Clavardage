package org.clav.ui.components;

import org.clav.ui.mvc.CLVController;
import org.clav.user.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ActiveUsersPanel extends JPanel implements ActiveUsersDisplay {
	private HashMap<String, JButton> userButtons;
	public ActiveUsersPanel() {
		super(new GridLayout(0, 1));
		this.userButtons = new HashMap<>();
	}


	private void refreshUsers(Iterable<User> users) {
		this.userButtons.clear();
		for (User user : users) {
			this.userButtons.put(user.getIdentifier(), CLVComponentFactory.createButton(user.getPseudo()));
		}
		this.checkDisplay();
	}
	@Override
	public void refreshUsers(Iterable<User> users, CLVController clvController){
		refreshUsers(users);
		for(String id : this.userButtons.keySet()){
			JButton b = this.userButtons.get(id);
			b.addActionListener(l->{
				ArrayList<String> ids = new ArrayList<>();
				ids.add(id);
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

	@Override
	public void removeUser(User user) {
		if (this.userButtons.containsKey(user.getIdentifier())) {
			this.userButtons.remove(user.getIdentifier());
			this.checkDisplay();
		}
	}

	@Override
	public JComponent getComponent() {
		return this;
	}
}
