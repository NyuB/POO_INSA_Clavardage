package org.clav.debug.graphic;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class DebugChatGridPanel extends JPanel {
	private static String DEFAULT_ID = "ID";
	private int r;
	private int c;
	HashMap<String, DebugChatPanel> activeChats;
	private DebugModel model;

	public DebugChatGridPanel() {
		super(new GridLayout(1, 1));
		this.activeChats = new HashMap<>();
		DebugChatPanel chat = new DebugChatPanel(DEFAULT_ID);
		activeChats.put(DEFAULT_ID,chat);
		this.add(chat);
		this.r = 1;
		this.c = 1;

	}

	public void createChat(String distant) {
		if (!this.activeChats.containsKey(distant)) {
			if (this.activeChats.size() >= this.r * this.c) {
				this.removeAll();
				if(this.r<this.c) {
					this.r++;
				}
				else{
					this.c++;
				}
				this.setLayout(new GridLayout(this.r, this.c));
				for (DebugChatPanel chatPanel  : this.activeChats.values()) {
					this.add(chatPanel);
				}
			}
			DebugChatPanel chat = new DebugChatPanel(distant);
			this.activeChats.put(distant,chat);
			this.add(chat);
			this.revalidate();
		}
	}
	public void writeMsg(String id,String msg){
		if(!this.activeChats.containsKey(id)){
			this.createChat(id);
		}
		this.activeChats.get(id).out.println(msg);
	}
}
