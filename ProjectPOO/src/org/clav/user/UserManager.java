package org.clav.user;

import org.clav.Agent;
import org.clav.AppHandler;
import org.clav.utils.constants.Delays;

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

	public UserManager(AppHandler appHandler, User mainUser) {
		this.appHandler = appHandler;
		this.mainUser = mainUser;
		this.activeUsers = new HashMap<>();
		this.activityTasks = new HashMap<>();
	}

	public boolean changeMainUserPseudo(String newPseudo){
		//TODO
		return false;
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
	public User getMainUser() {
		return mainUser;
	}

	public void setMainUser(User mainUser) {
		this.mainUser = mainUser;
	}

	public void processActive(String identifier, String pseudo) {
		this.log("Updating user "+identifier+" "+pseudo);
		if(!this.activeUsers.containsKey(identifier)){
			this.activeUsers.put(identifier,new User(identifier,pseudo));
			Timer timer = new Timer();
			ActivityTimerTask task = new ActivityTimerTask(Delays.INACTIVE_DELAY_SEC,this);
			this.activityTasks.put(identifier,task);
			timer.schedule(task,0,1000);

		}
		else{
			this.activityTasks.get(identifier).setCounter(Delays.INACTIVE_DELAY_SEC);
		}
	}
}
