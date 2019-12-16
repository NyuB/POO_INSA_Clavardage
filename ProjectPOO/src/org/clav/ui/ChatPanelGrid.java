package org.clav.ui;

import org.clav.AppHandler;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ChatPanelGrid extends JPanel {

	private CLVModel model;
	private HashMap<String,ChatPanel> activeChats;
	private int r;
	private int c;

	public ChatPanelGrid() {
		super(new GridLayout(1, 0));
		this.model = model;
		this.activeChats = new HashMap<>();
		this.r = 0;
		this.c = 0;
	}
}
