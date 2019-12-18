package org.clav.ui.components;

import org.clav.ui.mvc.CLVController;
import org.clav.ui.mvc.CLVModel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ChatPanelGrid extends JPanel {

	private CLVController controller;
	private CLVModel model;
	private HashMap<String, ChatPanel> activeChats;
	private int r;
	private int c;

	public ChatPanelGrid(CLVController controller, CLVModel model) {
		super(new GridLayout(1, 0));
		this.controller = controller;
		this.model = model;
		this.activeChats = new HashMap<>();
		this.r = 0;
		this.c = 0;
	}

	private void checkDisplay() {
		System.out.println("Chat grid checking display");
		this.removeAll();
		this.setLayout(new GridLayout(r, c));
		for (ChatPanel chatPanel : this.activeChats.values()) {
			this.add(chatPanel);
		}
	}

	private synchronized void createChat(String code) {
		if (!this.activeChats.containsKey(code)) {
			if (this.activeChats.size() >= this.r * this.c) {
				this.removeAll();
				if (this.r < this.c) {
					this.r++;
				} else {
					this.c++;
				}
				this.setLayout(new GridLayout(this.r, this.c));
				for (ChatPanel chatPanel : this.activeChats.values()) {
					this.add(chatPanel);
				}
			}
			ChatPanel chat = new ChatPanel(this.model.getChatFor(code));
			chat.addTypeActionListener(l -> {
				synchronized (chat) {
					System.out.println("Sending message from typefield");
					this.controller.notifyMessageSending(code, chat.consumeTypeField());
				}
			});
			this.activeChats.put(code, chat);
			this.add(chat);
			this.revalidate();
		}
	}

	public void deleteChat(String code) {
		if (this.activeChats.containsKey(code)) {
			this.remove(this.activeChats.get(code));
			this.activeChats.remove(code);
			if (this.r == this.c) {
				if ((r - 1) * c <= this.activeChats.size()) {
					this.r--;
					this.checkDisplay();
				}
			} else {
				if (r * (c - 1) <= this.activeChats.size()) {
					this.c--;
					this.checkDisplay();
				}
			}
			this.revalidate();
		}
	}

	public synchronized void refreshChat(String code) {
		System.out.println("Refreshing chat");
		if (this.activeChats.containsKey(code)) {
			this.activeChats.get(code).getTextArea().setText(this.model.getHistoryFor(code).printHistory());
			this.revalidate();

		} else {
			this.createChat(code);
		}
	}
}
