package org.clav.ui.components;

import org.clav.ui.mvc.CLVController;
import org.clav.ui.mvc.CLVModel;

import javax.swing.*;
import java.awt.*;

public class CLVPanel extends JPanel {
	private ChatPanelGrid chatPanelGrid;
	private ActiveUsersPanel activeUsersPanel;
	public CLVPanel(CLVController clvController, CLVModel model) {
		super(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx= 0.9;
		gbc.weighty= 1;
		gbc.fill = GridBagConstraints.BOTH;
		this.chatPanelGrid = new ChatPanelGrid(clvController,model);
		this.add(chatPanelGrid,gbc);



		gbc.gridx = 1;
		gbc.weightx = 0.1;
		this.activeUsersPanel = new ActiveUsersPanel();
		this.add(activeUsersPanel,gbc);


	}

	public ChatPanelGrid getChatPanelGrid() {
		return chatPanelGrid;
	}

	public ActiveUsersPanel getActiveUsersPanel() {
		return activeUsersPanel;
	}
}
