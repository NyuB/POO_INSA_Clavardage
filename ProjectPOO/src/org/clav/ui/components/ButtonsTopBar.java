package org.clav.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonsTopBar extends JPanel {
	public ButtonsTopBar(int nbButtons) {
		super(new GridLayout(1,nbButtons));
		for(int i = 0;i<nbButtons;i++){
			this.add(CLVComponentFactory.createButton("VOID"));
		}
	}
	public void setUpButton(int index, String text,ActionListener l){
		JButton button = ((JButton) this.getComponent(index));
		button.setText(text);
		button.addActionListener(l);
	}
}
