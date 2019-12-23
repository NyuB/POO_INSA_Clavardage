package org.clav.utils;

import org.clav.chat.Message;
import org.clav.user.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Used to generate unique IDs from diverse structures
 * Hashing is computed via the SHA-256 algorithm, which offers a good non-collision assurance
 * Particularly useful to generate an id from a list of strings to identify a same chat across multiple agents
 */
public final class HashUtils {
	private static MessageDigest hashAlgorithm;
	static {
		try {
			hashAlgorithm = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	private HashUtils(){
		//Ensure no instance of this class can be created
		assert(false);
	}

	public static String hashStringList(ArrayList<String> stringArrayList) {
		ArrayList<String> copy = new ArrayList<>(stringArrayList);
		copy.sort(Comparator.naturalOrder());
		StringBuilder sb = new StringBuilder();
		for (String s : copy) {
			sb.append(s);
		}
		byte[] hash = new byte[0];
		hash = hashAlgorithm.digest(sb.toString().getBytes());
		return new String(hash, 0, hash.length);
	}

	public static String hashUserList(ArrayList<User> members) {
		ArrayList<String> identifiers = new ArrayList<>();
		for (User u : members) {
			identifiers.add(u.getIdentifier());
		}
		return hashStringList(identifiers);
	}
}
