package org.clav.user;

import java.io.Serializable;

public class User implements Serializable {
	private String identifier;
	private String pseudo;
	public User(String identifier, String pseudo) {
		this.identifier = identifier;
		this.pseudo = pseudo;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getPseudo() {
		return pseudo;
	}


}
