package org.clav.network.server;

import org.clav.user.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpPresenceServer extends PresenceServer {
	private URL url;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	public HttpPresenceServer(String url) {
		super(url);
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	private void objSend(Object o){
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) (this.url.openConnection());
			connection.setRequestMethod("POST");
			this.objIn = new ObjectInputStream(connection.getInputStream());
			this.objOut = new ObjectOutputStream(connection.getOutputStream());
			objOut.writeObject(o);
			objOut.flush();
			objOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void subscribe(String id) {
		this.objSend(new ServerSubcription(id));
	}

	@Override
	public void publish(User activeUser) {
		this.objSend(new ServerPublication(activeUser));
	}
}
