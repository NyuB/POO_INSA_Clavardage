package org.clav.ui.mvc;

import org.clav.ui.components.CLVPanel;
import org.clav.ui.components.ChatPanelGrid;

import javax.swing.*;

public class CLVFrame extends JFrame implements CLVView {
	private CLVController controller;
	private CLVModel model;
	private ChatPanelGrid chatGrid;
	CLVPanel contentPane;
	public CLVFrame(CLVController controller, CLVModel model) {
		super("CLV APP");
		this.controller = controller;
		this.model = model;
		this.controller.assignView(this);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(1400,800);
		this.contentPane = new CLVPanel(controller,model);

		this.chatGrid = contentPane.getChatPanelGrid();
		this.setContentPane(contentPane);
	}

	@Override
	public void refreshChat(String code) {
		System.out.println("Frame refreshing chat with code "+code);
		this.chatGrid.refreshChat(code);
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

	}
}
