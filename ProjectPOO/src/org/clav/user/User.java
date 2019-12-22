package org.clav.user;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private String identifier;
	private String pseudo;
	private Date date;
	public User(String identifier, String pseudo) {
		this.identifier = identifier;
		this.pseudo = pseudo;
		this.date = new Date();
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void changePseudo(String pseudo) {
		this.pseudo = pseudo;
		this.date = new Date();
	}

	public Date getDate() {
		return date;
	}
	public void syncPseudo(User mirror){
		this.pseudo = mirror.getPseudo();
		this.date = mirror.getDate();
	}
}
