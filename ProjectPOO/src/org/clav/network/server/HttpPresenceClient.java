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

/**
 * Represents a connection with an HTTP presence server
 * Each request follows the same pattern :
 * 1)Send a CLVPacket via a Http POST request to the distant server
 * 2)Wait a CLV Ack packet from the distant server
 * To be able to interact with this class, a servlet must at least implement a doPost method which reads an object fromp the request first, then sends an object in response
 * @see HttpPresenceServlet
 */
public class HttpPresenceClient extends PresenceClient {

	private URL url;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;

	public HttpPresenceClient(String url) {
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
			System.out.println("[HTTP]Obj sent");
			this.objIn = new ObjectInputStream(connection.getInputStream());
			CLVPacket ack = (CLVPacket)objIn.readObject();

			if (ack.header!=CLVHeader.ACK) {
				System.out.println("[HTTP]Invalid ack message from server");
			} else {
				System.out.println("[HTTP]Receiving ack message from server");
			}
			objIn.close();

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
