package org.clav.ui.components;

import org.clav.utils.components.JTextAreaOutputStream;

import javax.swing.*;
public class ScrollTextArea extends JScrollPane {
	private JTextArea textArea;
	public JTextAreaOutputStream out;
	public ScrollTextArea(JTextArea textArea) {
		super(textArea);
		this.textArea = textArea;
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		this.out = new JTextAreaOutputStream(this.textArea);
	}
	public ScrollTextArea(){
		this(new JTextArea());
	}

	public JTextArea getTextArea() {
		return textArea;
	}
}
