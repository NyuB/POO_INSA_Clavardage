package org.clav.ui.components;

import org.clav.utils.JTextAreaOutputStream;

import javax.swing.*;
public class ScrollComponent<T extends JComponent> extends JScrollPane {
	private T component;
	public JTextAreaOutputStream out;
	public ScrollComponent(T component) {
		super(component);
		this.component = component;
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
	}
	public T getComponent() {
		return component;
	}


}
