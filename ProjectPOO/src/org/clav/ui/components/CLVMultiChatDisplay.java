package org.clav.ui.components;

import javax.swing.*;

public interface CLVMultiChatDisplay {
	/**
	 * If present, delete the chat from the display
	 * @param code chat unique hashcode
	 */
	void deleteChat(String code);

	/**
	 * Create or refresh the chat corresponding to a hashcode
	 * @param code The chat unique hashcode
	 */
	void updateChat(String code);

	/**
	 * @return The component related to this chat display, to be integrated to Swing interfaces
	 */
	JComponent getComponent();
}
