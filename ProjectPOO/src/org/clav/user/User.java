package org.clav.user;
import java.io.Serializable;
import java.util.Date;

/**
 * Represents a CLV user : Unique id, modifiable pseudo, and the timestamp corresponding to this pseudo choice
 */
public class User implements Serializable {
	private String identifier;
	private String pseudo;
	private Date pseudoDate;
	public User(String identifier, String pseudo) {
		this.identifier = identifier;
		this.pseudo = pseudo;
		this.pseudoDate = new Date();
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void changePseudo(String pseudo) {
		this.pseudo = pseudo;
		this.pseudoDate = new Date();
	}


	public Date getPseudoDate() {
		return pseudoDate;
	}
	public void syncPseudo(User mirror){
		this.pseudo = mirror.getPseudo();
		this.pseudoDate = mirror.getPseudoDate();
	}
}
