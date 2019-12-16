package org.clav.ui;

import javax.swing.*;
public class ScrollTextArea extends JScrollPane {
	private JTextArea textArea;
	public ScrollTextArea(JTextArea textArea) {
		super(textArea);
		this.textArea = textArea;
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
	}
	public ScrollTextArea(){
		this(new JTextArea());
	}

	public JTextArea getTextArea() {
		return textArea;
	}
}
