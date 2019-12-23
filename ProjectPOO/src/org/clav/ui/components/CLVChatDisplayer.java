package org.clav.ui.components;

import javax.swing.*;

public interface CLVChatDisplayer {
	void deleteChat(String code);

	void updateChat(String code);

	JComponent getComponent();
}
