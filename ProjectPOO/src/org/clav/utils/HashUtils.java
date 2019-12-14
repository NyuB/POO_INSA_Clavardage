package org.clav.utils;

import org.clav.user.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Used to generate unique IDs from diverse structures
 * Hashing is computed with the SHA-256 algorithm, which offers a good non-collision assurance
 * Particularly useful to generate an id from a list of strings to identify a same chat across multiple agents
 */
public class HashUtils {

	public static String hashStringList(ArrayList<String> stringArrayList){
		StringBuilder sb = new StringBuilder();
		for(String s :stringArrayList){
			sb.append(s);
		}
		byte[] hash = new byte[0];
		try {
			hash = MessageDigest.getInstance("SHA-256").digest(sb.toString().getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new String(hash,0,hash.length);
	}
	public static String hashUserList(ArrayList<User> members){
		ArrayList<String> identifiers = new ArrayList<>();
		for(User u:members){
			identifiers.add(u.getIdentifier());
		}
		identifiers.sort(Comparator.naturalOrder());
		return hashStringList(identifiers);
	}
}
