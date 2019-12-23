package org.clav.ui.mvc;
import java.util.ArrayList;

public interface CLVView {
	void refreshChat(String code);
	void turnOn();
	void turnOff();
	void refreshUsers();
	void refreshAll();
	ArrayList<String> popUserSelectionDialog();
	String popInvalidPseudoDialog();

}
