package org.clav.network;
import org.clav.user.User;

public abstract class PresenceServer {

	public abstract void subscribe(String id);

	public abstract void publish(User activeUser);

	protected abstract void notify(User activeUser, String id);

}
