package org.clav.user;

import org.clav.AppHandler;
import org.clav.utils.constants.DelayConstants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;

public class UserManager {

	private AppHandler appHandler;
	private HashMap<String, User> activeUsers;
	private HashSet<String> knownUsers;
	private HashMap<String,ActivityTimerTask> activityTasks;
	private User mainUser;

	private void  log(String txt){
		System.out.println("[USR]"+txt);
	}

	public UserManager(User mainUser) {
		this.mainUser = mainUser;
		this.activeUsers = new HashMap<>();
		this.activeUsers.put(mainUser.getIdentifier(),mainUser);
		this.activityTasks = new HashMap<>();
	}

	public void setAppHandler(AppHandler appHandler) {
		this.appHandler = appHandler;
	}

	public boolean changeMainUserPseudo(String newPseudo){
		//TODO
		return false;
	}

	public HashMap<String, User> getActiveUsers() {
		return activeUsers;
	}
	public synchronized void removeUser(String id){
		this.activeUsers.remove(id);
		this.appHandler.processUserInaction(id);
	}
	public boolean isActiveUser(String identifier){
		return this.getActiveUsers().containsKey(identifier);
	}
	public User getMainUser() {
		return mainUser;
	}
	public void processActive(String identifier, String pseudo) {
		//this.log("Updating user "+identifier+" "+pseudo);
		if(!this.activeUsers.containsKey(identifier)){
			this.activeUsers.put(identifier,new User(identifier,pseudo));
			Timer timer = new Timer();
			ActivityTimerTask task = new ActivityTimerTask(DelayConstants.INACTIVE_DELAY_SEC,identifier,this);
			this.activityTasks.put(identifier,task);
			timer.schedule(task,0,1000);

		}
		else{
			if(!identifier.equals(this.mainUser.getIdentifier()))
			this.activityTasks.get(identifier).setCounter(DelayConstants.INACTIVE_DELAY_SEC);
		}
	}
}
