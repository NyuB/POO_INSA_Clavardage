package org.clav.ui.mvc;

import org.clav.ui.components.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CLVFrame extends JFrame implements CLVView {
	private CLVController controller;
	private CLVModel model;
	private CLVChatDisplayer chatGrid;
	private CLVPanel contentPane;
	public CLVFrame(CLVController controller, CLVModel model) {
		super("CLV APP "+model.getMainUser().getIdentifier());
		this.controller = controller;
		this.model = model;
		this.controller.assignView(this);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(1400,800);
		this.contentPane = new CLVPanel(controller,this,model);
		this.chatGrid = contentPane.getChatPanelGrid();
		this.setContentPane(contentPane);
	}

	@Override
	public void refreshChat(String code) {
		this.chatGrid.updateChat(code);
	}

	@Override
	public void refreshUsers() {
		this.contentPane.getActiveUsersPanel().refreshUsers(this.model.getActiveUsers().values(),this.controller);

	}

	@Override
	public void refreshAll() {
		//TODO
	}

	@Override
	public void turnOn() {
		this.setVisible(true);

	}

	@Override
	public void turnOff() {
		this.setVisible(false);
		this.dispose();
	}

	@Override
	public String popInvalidPseudoDialog() {
		return JOptionPane.showInputDialog(this,"Pseudo already used, please enter another pseudo");
	}

	@Override
	public ArrayList<String> popUserSelectionDialog() {
		UserSelectionPanel selectionPanel = new UserSelectionPanel(this.model.getActiveUsers().values());
		JDialog dialog = new JDialog(this,"Select users",true);
		dialog.setSize(500,500);
		dialog.setContentPane(new ScrollComponent<UserSelectionPanel>(selectionPanel));
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		return selectionPanel.getSelected();
	}
}
