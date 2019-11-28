package org.clav.user;

public class User {
	private String identifier;
	private String pseudo;

	public String getIdentifier() {
		return identifier;
	}

	public User(String identifier, String pseudo) {
		this.identifier = identifier;
		this.pseudo = pseudo;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
}
