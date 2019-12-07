package org.clav.debug.graphic;

import org.clav.utils.components.JTextAreaOutputStream;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

public class DebugChatPanel extends JPanel {
	private JTextField distantIDField;
	private JTextField messageField;
	public PrintStream out;
	private JTextArea chatArea;
	private DebugModel model = null;//TODO
	public DebugChatPanel(String distantID) {
		super(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;


		constraints.gridy = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 0.15;
		this.distantIDField = new JTextField(distantID);
		this.add(distantIDField,constraints);

		constraints.gridy = 1;
		constraints.weighty = 0.7;
		this.chatArea = new JTextArea();
		chatArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.add(chatArea,constraints);

		constraints.gridy = 2;
		constraints.weighty = 0.15;
		this.messageField = new JTextField();
		messageField.addActionListener(a->{
			model.writeChatMessageTo(distantIDField.getText(),messageField.getText());
			messageField.setText("");
		});
		this.add(messageField,constraints);

		this.out = new PrintStream(new JTextAreaOutputStream(this.chatArea));

	}

	public void assignModel(DebugModel model){
		this.model = model;
	}

}
