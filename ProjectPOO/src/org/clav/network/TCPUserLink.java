package org.clav.network;

import org.clav.user.User;

import java.io.*;
import java.net.Socket;

public class TCPUserLink {
	private String relatedUser;
	private Socket distant;
	PrintWriter outWriter;
	BufferedReader inReader;

	public TCPUserLink(String relatedUser, Socket distant) {
		this.relatedUser = relatedUser;
		this.distant = distant;
		try {
			this.outWriter = new PrintWriter(distant.getOutputStream());
			this.inReader = new BufferedReader(new InputStreamReader(distant.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public synchronized void send(String message){
		this.outWriter.write(message);
	}
	public synchronized String read(){
		try {
			return this.inReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return "ERR";
		}

	}

	public String getRelatedUserID() {
		return relatedUser;
	}

	public Socket getDistant() {
		return distant;
	}
}
