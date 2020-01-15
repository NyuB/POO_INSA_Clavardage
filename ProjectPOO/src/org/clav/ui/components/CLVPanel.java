package org.clav.ui.components;

import org.clav.ui.mvc.CLVController;
import org.clav.ui.mvc.CLVModel;
import org.clav.ui.mvc.CLVView;

import javax.swing.*;
import java.awt.*;

public class CLVPanel extends JPanel {
	private CLVMultiChatDisplay chatDisplay;
	private ActiveUsersDisplay activeUsersPanel;
	private ButtonsTopBar topBar;
	public CLVPanel(CLVController clvController, CLVView view,CLVModel model,ComponentFactory componentFactory) {
		super(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.weightx= 1;
		gbc.weighty= 0.1;
		gbc.fill = GridBagConstraints.BOTH;
		this.topBar = new ButtonsTopBar(4,componentFactory);
		this.topBar.setUpButton(0,"PSEUDO",l->{
			clvController.notifyMainUserPseudoChange(JOptionPane.showInputDialog(this,"New pseudo"));
		});
		this.topBar.setUpButton(1,"CHAT",l->{
			System.out.println("CHI Request from UI");
			clvController.notifyChatInitiationFromUser(view.popUserSelectionDialog());
		});
		this.topBar.setUpButton(2,"STORE",l->{
			System.out.println("STORE Request from panel");
			clvController.notifyChatStorage();
		});
		this.topBar.setUpButton(3,"SETTINGS",l->{
			System.out.println("Settings Request from panel");
			view.popSettingDialog();
		});
		this.add(topBar,gbc);

		gbc.gridwidth = 1;
		gbc.gridy = 1;
		gbc.weightx= 0.9;
		gbc.weighty= 0.9;
		this.chatDisplay = new CLVChatTabPanel(clvController,model,componentFactory);
		this.add(chatDisplay.getComponent(),gbc);

		gbc.gridx = 1;
		gbc.weightx = 0.1;
		this.activeUsersPanel = new ActiveUsersScrollPane(componentFactory);
		this.add(activeUsersPanel.getComponent(),gbc);


	}

	public CLVMultiChatDisplay getChatDisplay() {
		return chatDisplay;
	}

	public ActiveUsersDisplay getActiveUsersPanel() {
		return activeUsersPanel;
	}
}
