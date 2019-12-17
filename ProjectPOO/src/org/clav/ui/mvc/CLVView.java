package org.clav.ui.mvc;

public interface CLVView {
	void refreshChat(String code);
	void turnOn();
	void turnOff();
	void refreshUsers();
	void refreshAll();
}
