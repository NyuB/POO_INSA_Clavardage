package org.clav.ui;

import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel {
	private ChatTopBar topBar;
	private JLabel title;
	private ScrollTextArea textArea;
	private JTextField typeField;

	public ChatPanel() {
		super(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		//Top bar
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0.1;
		gbc.fill = GridBagConstraints.BOTH;
		this.topBar = new ChatTopBar();
		this.add(topBar,gbc);

		//Title
		gbc.gridy = 1;
		gbc.weighty = 0.15;
		this.title = new JLabel("CHAT TITLE");
		this.add(this.title,gbc);

		//TextArea
		gbc.gridy = 2;
		gbc.weighty = 0.6;
		this.textArea = new ScrollTextArea();
		this.add(textArea,gbc);

		//TypeField
		gbc.gridy = 3;
		gbc.weighty = 0.15;
		this.typeField = new JTextField();
		this.add(typeField,gbc);
	}
}
