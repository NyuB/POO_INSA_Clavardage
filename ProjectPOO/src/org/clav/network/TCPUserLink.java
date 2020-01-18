package org.clav.network;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPUserLink {
	private String relatedUser;
	private Socket distant;
	private ObjectOutputStream objOut;
	private ObjectInputStream objIn;

	public TCPUserLink(String relatedUser, Socket distant) {
		this.relatedUser = relatedUser;
		this.distant = distant;
		try {
			this.objOut = new ObjectOutputStream(distant.getOutputStream());
			this.objIn = new ObjectInputStream(distant.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setRelatedUser(String relatedUser) {
		this.relatedUser = relatedUser;
	}

	public void send(CLVPacket packet) {
		synchronized (this.objOut) {
			System.out.println("[TCP]Linksend to " + this.getRelatedUserID());
			try {
				this.objOut.writeObject(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String readStr() {
			return (String) ( this.read().data);
	}

	public CLVPacket read() {
		synchronized (this.objIn) {
			try {
				return (CLVPacket) this.objIn.readObject();
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("[TCPLINK]Error reading object");
				return new CLVPacket(CLVHeader.ERR, null);
			}
		}
	}

	public String getRelatedUserID() {
		return relatedUser;
	}

	public Socket getDistant() {
		return distant;
	}
}
