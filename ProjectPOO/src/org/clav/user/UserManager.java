package org.clav.user;

import org.clav.Agent;

import java.util.HashMap;

public class UserManager {
	private Agent relatedAgent;
	private HashMap<String, User> activeUsers;
	private User mainUser;

	public UserManager(Agent agent, User mainUser) {
		this.relatedAgent = agent;
		this.mainUser = mainUser;
		this.activeUsers = new HashMap<>();
	}

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
	public synchronized void addUserEntry(User user){
		this.activeUsers.put(user.getIdentifier(),user);
	}
	public boolean isActiveUser(String identifier){
		return this.getActiveUsers().containsKey(identifier);
	}

	public void setRelatedAgent(Agent relatedAgent) {
		this.relatedAgent = relatedAgent;
	}

	public User getMainUser() {
		return mainUser;
	}

	public void setMainUser(User mainUser) {
		this.mainUser = mainUser;
	}

	public void createIfAbsent(String identifier,String pseudo) {
		//TODO
		if(!this.activeUsers.containsKey(identifier)){
			this.activeUsers.put(identifier,new User(identifier,pseudo));
		}
	}
}
