package org.clav.user;

/**
 * Provides methods to interact with an entity managing user activity
 * @see ActivityTimerTask
 */
public interface ActivityHandler {
	void removeActiveUser(String id);
}
