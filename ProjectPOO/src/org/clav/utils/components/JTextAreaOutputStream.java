package org.clav.utils.components;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Directly copied from StackOverflow
 */
public class JTextAreaOutputStream extends OutputStream {
	private JTextArea textArea;
	private StringBuilder stringBuilder;

	public JTextAreaOutputStream(JTextArea textArea) {
		this.textArea = textArea;
		this.stringBuilder = new StringBuilder();
	}
	@Override
	public void write(int b) {
		switch (b) {
			case '\r':
				break;
			case '\n':
				this.textArea.append(this.stringBuilder.toString() + "\n");
				this.textArea.revalidate();
				this.stringBuilder.setLength(0);
				break;
			default:
				stringBuilder.append((char) b);
				break;
		}

	}
}
