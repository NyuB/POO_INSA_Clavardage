package org.clav.ui.components;

import org.clav.ui.mvc.CLVController;
import org.clav.user.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ActiveUsersScrollPane extends JPanel implements ActiveUsersDisplay {
	private HashMap<String, JButton> userButtons;
	private JLabel title;
	private ScrollComponent<JPanel> userPanel;

	public ActiveUsersScrollPane() {
		super(new GridBagLayout());
		this.userButtons = new HashMap<>();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0.1;
		gbc.fill = GridBagConstraints.BOTH;

		this.title = CLVComponentFactory.createLabel("ACTIVE USERS");
		this.add(title,gbc);

		gbc.gridy = 1;
		gbc.weighty = 0.9;
		JPanel panel = new JPanel();
		this.userPanel = new ScrollComponent<>(panel);
		this.add(userPanel,gbc);
	}

	@Override
	public JComponent getComponent() {
		return this;
	}

	@Override
	public void refreshUsers(Iterable<User> users,CLVController clvController) {
		this.userPanel.getComponent().removeAll();
		for(User u : users){
			if(this.userButtons.containsKey(u.getIdentifier())){
				this.userButtons.get(u.getIdentifier()).setText(u.getPseudo());
				this.userPanel.getComponent().add(this.userButtons.get(u.getIdentifier()));
			}
			else{
				JButton button = CLVComponentFactory.createButton(u.getPseudo());
				button.addActionListener(l->{
					ArrayList<String> ids = new ArrayList<>();
					ids.add(u.getIdentifier());
					clvController.notifyChatInitiationFromUser(ids);
				});
				this.userButtons.put(u.getIdentifier(),button);
				this.userPanel.getComponent().add(button);
			}
		}
		this.title.setText("ACTIVE USERS : "+this.userButtons.size());
		this.revalidate();

	}

	@Override
	public void removeUser(User user) {
		//TODO

	}
}
