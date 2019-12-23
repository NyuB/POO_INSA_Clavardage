package org.clav.user;

import java.io.Serializable;
import java.util.Date;

/**
 * A data class containing the pseudo concerned by the conflict and the date when the conflicting user chose it
 */
public class PseudoRejection implements Serializable {
	private String pseudo;
	private Date date;

	public PseudoRejection(String pseudo, Date date) {
		this.pseudo = pseudo;
		this.date = date;
	}

	public String getPseudo() {
		return pseudo;
	}

	public Date getDate() {
		return date;
	}
}
