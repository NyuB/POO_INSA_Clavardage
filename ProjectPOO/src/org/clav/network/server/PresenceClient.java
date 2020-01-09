package org.clav.network.server;
import org.clav.user.User;

/**
 * Represents a connection with a remote presence server
 */
public abstract class PresenceClient {

	/**
	 * Inform the presence server than a user wants to be notified of other users activity
	 * @param id The id representing the user willing to be notified of other users activity by the presence server
	 */
	public abstract void subscribe(String id);

	/**
	 * Signal active state of an user to the presence server.
	 * @param activeUser The user signaling its activity to the presence server
	 */
	public abstract void publish(User activeUser);

	public PresenceClient(String connection){

	}



}
