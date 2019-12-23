package org.clav.ui.components;

import org.clav.chat.Chat;
import org.clav.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChatPanel extends JPanel {
	private ButtonsTopBar topBar;

	public JLabel getTitle() {
		return title;
	}

	private JLabel title;
	private ScrollComponent<JTextArea> textArea;
	private JTextField typeField;

	public ChatPanel(Chat chat) {
		super(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		this.setBorder(BorderFactory.createLineBorder(Color.RED));
		//Top bar
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0.05;
		gbc.fill = GridBagConstraints.BOTH;
		this.topBar = new ButtonsTopBar(4);
		this.add(topBar,gbc);

		//Title
		gbc.gridy = 1;
		gbc.weighty = 0.15;
		StringBuilder sb = new StringBuilder();
		for(User u:chat.getMembers()){
			sb.append(u.getPseudo());
			sb.append(" | ");
		}
		this.title = new JLabel(sb.toString());
		this.add(this.title,gbc);

		//TextArea
		gbc.gridy = 2;
		gbc.weighty = 0.65;
		this.textArea = new ScrollComponent<>(new JTextArea());
		this.add(textArea,gbc);


		//TypeField
		gbc.gridy = 3;
		gbc.weighty = 0.15;
		this.typeField = new JTextField();
		this.add(typeField,gbc);
	}
	public JTextArea getTextArea() {
		return textArea.getComponent();
	}
	public void addTypeActionListener(ActionListener l){
		this.typeField.addActionListener(l);
	}
	public String consumeTypeField(){
		String res = this.typeField.getText();
		this.typeField.setText("");
		return res;
	}
}
