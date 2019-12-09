package org.clav.debug.graphic;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;


/**
 * Display and updates dynamically a grid of DebugChatPanel. Provides methods to write in the outputstream of a specific chat
 */
public class DebugChatGridPanel extends JPanel {
	private static String DEFAULT_ID = "ID";
	private int r;
	private int c;
	HashMap<String, DebugChatPanel> activeChats;
	private DebugModel model;

	public DebugChatGridPanel(DebugModel model) {
		super(new GridLayout(1, 1));
		this.model = model;
		this.activeChats = new HashMap<>();
		DebugChatPanel chat = new DebugChatPanel(DEFAULT_ID, model);
		activeChats.put(DEFAULT_ID, chat);
		this.add(chat);
		this.r = 1;
		this.c = 1;

	}

	private void checkDisplay() {
		this.removeAll();
		this.setLayout(new GridLayout(r, c));
		for (DebugChatPanel chatPanel : this.activeChats.values()) {
			this.add(chatPanel);
		}
	}

	public void createChat(String distant) {
		if (!this.activeChats.containsKey(distant)) {
			if (this.activeChats.size() >= this.r * this.c) {
				this.removeAll();
				if (this.r < this.c) {
					this.r++;
				} else {
					this.c++;
				}
				this.setLayout(new GridLayout(this.r, this.c));
				for (DebugChatPanel chatPanel : this.activeChats.values()) {
					this.add(chatPanel);
				}
			}
			DebugChatPanel chat = new DebugChatPanel(distant, model);
			this.activeChats.put(distant, chat);
			this.add(chat);
			this.revalidate();
		}
	}

	public void deleteChat(String distant) {
		if (this.activeChats.containsKey(distant)) {
			this.remove(this.activeChats.get(distant));
			this.activeChats.remove(distant);
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

	public void writeMsg(String id, String msg) {
		if (!this.activeChats.containsKey(id)) {
			this.createChat(id);
		}
		this.activeChats.get(id).out.println(msg);
	}
}
