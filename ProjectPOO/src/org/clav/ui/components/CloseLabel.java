package org.clav.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CloseLabel extends JPanel{
	private JLabel label;
	private JButton closeButton;

	public CloseLabel(JLabel label,JButton button) {
		super(new GridBagLayout());
		this.label = label;
		this.closeButton = button;
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.9;
		gbc.weighty  = 1;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(label,gbc);

		gbc.gridx=1;
		gbc.weightx = 0.1;
		this.add(closeButton,gbc);

	}
	public void addCloseAction(ActionListener l){
		this.closeButton.addActionListener(l);
	}

	public JLabel getLabel() {
		return label;
	}

	public JButton getCloseButton() {
		return closeButton;
	}
}
