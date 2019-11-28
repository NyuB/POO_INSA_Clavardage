package org.clav.user;

import java.util.HashMap;

public class UserManager {
	private HashMap<String, User> activeUsers;
	private User mainUser;

	public boolean changeMainUserPseudo(String newPseudo){
		//TODO
		return false;
	}

	public void updateActives(){
		//TODO
	}

	public HashMap<String, User> getActiveUsers() {
		return activeUsers;
	}
	public void addUser(User user){
		this.activeUsers.put(user.getIdentifier(),user);
	}

	public User getMainUser() {
		return mainUser;
	}

	public void setMainUser(User mainUser) {
		this.mainUser = mainUser;
	}
}
