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
			this.outWriter = new PrintWriter(distant.getOutputStream(),true);
			this.inReader = new BufferedReader(new InputStreamReader(distant.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setRelatedUser(String relatedUser) {
		this.relatedUser = relatedUser;
	}

	public void send(String message){
		System.out.println("[TCP]Linksend to "+this.getRelatedUserID());
		this.outWriter.println(message);
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
