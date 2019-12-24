package org.clav.ui.components;

import javax.swing.*;
import java.awt.*;

public class LabeledTickBox extends JPanel {
	private JCheckBox checkBox;
	private JLabel label;
	public LabeledTickBox(JLabel label) {
		super(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.weighty = 1;
		gbc.weightx = 0.9;
		gbc.fill = GridBagConstraints.BOTH;
		this.checkBox = new JCheckBox();
		this.label = label;
		this.add(this.label,gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.1;
		this.add(this.checkBox,gbc);
	}

	public boolean isSelected(){
		return this.checkBox.isSelected();
	}

	public JCheckBox getCheckBox() {
		return checkBox;
	}

	public JLabel getLabel() {
		return label;
	}
}
