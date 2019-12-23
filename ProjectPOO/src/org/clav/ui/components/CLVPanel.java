package org.clav.ui.components;

import org.clav.ui.mvc.CLVController;
import org.clav.ui.mvc.CLVModel;
import org.clav.ui.mvc.CLVView;

import javax.swing.*;
import java.awt.*;

public class CLVPanel extends JPanel {
	private CLVChatDisplay chatDisplay;
	private ActiveUsersPanel activeUsersPanel;
	private ButtonsTopBar topBar;
	public CLVPanel(CLVController clvController, CLVView view,CLVModel model) {
		super(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.weightx= 1;
		gbc.weighty= 0.1;
		gbc.fill = GridBagConstraints.BOTH;
		this.topBar = new ButtonsTopBar(4);
		this.topBar.setUpButton(0,"PSEUDO",l->{
			clvController.notifyMainUserPseudoChange(JOptionPane.showInputDialog(this,"New pseudo"));
		});
		this.topBar.setUpButton(1,"CHAT",l->{
			System.out.println("CHI Request from UI");
			clvController.notifyChatInitiationFromUser(view.popUserSelectionDialog());
		});
		this.add(topBar,gbc);

		gbc.gridwidth = 1;
		gbc.gridy = 1;
		gbc.weightx= 0.9;
		gbc.weighty= 0.9;
		this.chatDisplay = new CLVChatTabPanel(clvController,model);
		this.add(chatDisplay.getComponent(),gbc);



		gbc.gridx = 1;
		gbc.weightx = 0.1;
		this.activeUsersPanel = new ActiveUsersPanel();
		this.add(activeUsersPanel,gbc);


	}

	public CLVChatDisplay getChatDisplay() {
		return chatDisplay;
	}

	public ActiveUsersPanel getActiveUsersPanel() {
		return activeUsersPanel;
	}
}
