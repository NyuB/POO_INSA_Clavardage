package org.clav.debug;

public interface DebugPlugin {
	void receiveChatMessageFrom(String user, String message);
	void writeChatMessageTo(String user,String message);
	void log(String message);
	void displayUsers(Iterable<String> users);
}