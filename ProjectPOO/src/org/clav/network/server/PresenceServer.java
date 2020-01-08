package org.clav.network.server;
import org.clav.user.User;

public abstract class PresenceServer {

	public abstract void subscribe(String id);

	public abstract void publish(User activeUser);

	public PresenceServer(String url){

	}



}
