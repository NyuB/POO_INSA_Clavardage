package org.clav.debug.graphic;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class DebugChatGrid extends JPanel {
	private static String DEFAULT_ID = "ID";
	private int r;
	private int c;
	HashMap<String, DebugChatPanel> save;

	public DebugChatGrid() {
		super(new GridLayout(1, 1));
		this.save = new HashMap<>();
		DebugChatPanel chat = new DebugChatPanel(DEFAULT_ID);
		save.put(DEFAULT_ID,chat);
		this.add(chat);
		this.r = 1;
		this.c = 1;

	}

	public void createChat(String distant) {
		if (!this.save.containsKey(distant)) {
			if (this.save.size() >= this.r * this.c) {
				this.removeAll();
				this.r++;
				this.setLayout(new GridLayout(this.r, this.c));
				for (DebugChatPanel chatPanel  : this.save.values()) {
					this.add(chatPanel);
				}
			}
			DebugChatPanel chat = new DebugChatPanel(distant);
			this.save.put(distant,chat);
			this.add(chat);
			this.revalidate();
		}
	}
}
