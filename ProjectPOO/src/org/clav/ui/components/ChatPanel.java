package org.clav.ui.components;

import org.clav.chat.Chat;
import org.clav.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChatPanel extends JPanel {
	private ButtonsTopBar topBar;
	private JLabel title;
	private ScrollComponent<JTextArea> textArea;
	private JTextField typeField;

	public ChatPanel() {
		this(DefaultComponentFactory.DEFAULT);
	}
	public ChatPanel(ComponentFactory componentFactory) {
		super(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		this.setBorder(BorderFactory.createLineBorder(componentFactory.getBorderColor()));
		//Top bar
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0.05;
		gbc.fill = GridBagConstraints.BOTH;
		this.topBar = new ButtonsTopBar(4,componentFactory);
		this.add(topBar,gbc);

		//Title
		gbc.gridy = 1;
		gbc.weighty = 0.05;
		this.title = componentFactory.createLabel(toString());
		this.add(this.title,gbc);

		//TextArea
		gbc.gridy = 2;
		gbc.weighty = 0.8;
		JTextArea textArea = componentFactory.createTextArea();
		this.textArea = new ScrollComponent<>(textArea);
		this.add(textArea,gbc);


		//TypeField
		gbc.gridy = 3;
		gbc.weighty = 0.1;
		this.typeField = componentFactory.createTextField();
		this.add(typeField,gbc);
	}
	public void addTypeActionListener(ActionListener l){
		this.typeField.addActionListener(l);
	}
	public String consumeTypeField(){
		String res = this.typeField.getText();
		this.typeField.setText("");
		return res;
	}
	
	public JTextArea getTextArea() {
		return textArea.getComponent();
	}
	public JLabel getTitle() {
		return title;
	}


}
