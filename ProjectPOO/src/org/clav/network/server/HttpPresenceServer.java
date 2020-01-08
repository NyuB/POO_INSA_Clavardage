package org.clav.network.server;

import org.clav.network.CLVHeader;
import org.clav.network.CLVPacket;
import org.clav.network.CLVPacketFactory;
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
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) (this.url.openConnection());
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			this.objOut = new ObjectOutputStream(connection.getOutputStream());
			objOut.writeObject(o);
			objOut.flush();
			objOut.close();
			this.objIn = new ObjectInputStream(connection.getInputStream());
			CLVPacket ack = (CLVPacket)objIn.readObject();
			if(ack.header!= CLVHeader.ACK)System.out.println("Invalid ack message from server");
			objIn.close();
			System.out.println("Http obj sent");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if(connection!=null) connection.disconnect();
		}
	}
	@Override
	public void subscribe(String id) {
		this.objSend(CLVPacketFactory.gen_SUB(id));
	}

	@Override
	public void publish(User activeUser) {
		this.objSend(CLVPacketFactory.gen_PUB(activeUser));
	}
}
