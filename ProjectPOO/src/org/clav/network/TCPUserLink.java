package org.clav.network;


import java.io.*;
import java.net.Socket;

import static org.clav.network.CLVHeader.STR;

public class TCPUserLink {//TODO Implement object streams instead of string
	private String relatedUser;
	private Socket distant;
	PrintWriter outWriter;
	BufferedReader inReader;


	private ObjectOutputStream objOut;
	private ObjectInputStream objIn;

	public TCPUserLink(String relatedUser, Socket distant) {
		this.relatedUser = relatedUser;
		this.distant = distant;
		try {
			//this.outWriter = new PrintWriter(distant.getOutputStream(),true);
			//this.inReader = new BufferedReader(new InputStreamReader(distant.getInputStream()));
			this.objOut = new ObjectOutputStream(distant.getOutputStream());
			this.objIn = new ObjectInputStream(distant.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setRelatedUser(String relatedUser) {
		this.relatedUser = relatedUser;
	}

	public void send(String message) {
		this.send(new CLVPacket(STR, message));
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
				e.printStackTrace();
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
