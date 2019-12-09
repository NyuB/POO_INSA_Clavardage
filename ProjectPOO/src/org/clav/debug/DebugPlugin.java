package org.clav.debug;


/**
 * Provides methods to track specific events during application run
 */
public interface DebugPlugin {
	void receiveChatMessageFrom(String user, String message);
	void writeChatMessageTo(String user,String message);
	void log(String message);
	void displayUsers(Iterable<String> users);
	void detectNewUser(String identifier);
}
