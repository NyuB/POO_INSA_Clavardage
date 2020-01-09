package org.clav.user;

import org.clav.AppHandler;
import org.clav.utils.constants.DelayConstants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;

public class UserManager implements ActivityHandler {

	private AppHandler appHandler;
	private final HashMap<String, User> activeUsers;
	private final HashSet<String> pseudoSet;
	private final HashMap<String, ActivityTimerTask> activityTasks;

	public HashSet<String> getPseudoSet() {
		return pseudoSet;
	}

	private User mainUser;

	private void log(String txt) {
		System.out.println("[USR]" + txt);
	}

	public UserManager(User mainUser) {
		this.mainUser = mainUser;
		this.activeUsers = new HashMap<>();
		this.activeUsers.put(mainUser.getIdentifier(), mainUser);
		this.activityTasks = new HashMap<>();
		this.pseudoSet = new HashSet<>();
		this.pseudoSet.add(mainUser.getPseudo());
	}

	public void setAppHandler(AppHandler appHandler) {
		this.appHandler = appHandler;
	}

	public boolean changeMainUserPseudo(String newPseudo) {
		synchronized (this.pseudoSet) {
			if (!this.pseudoSet.contains(newPseudo)) {
				this.pseudoSet.remove(this.getMainUser().getPseudo());
				this.pseudoSet.add(newPseudo);
				this.mainUser.changePseudo(newPseudo);
				return true;
			} else {
				return false;
			}
		}

	}

	public HashMap<String, User> getActiveUsers() {
		return activeUsers;
	}

	/**
	 * Removes the related user from the active users list and his pseudo from the pseudo set
	 *
	 * @param id User identifier
	 */
	@Override
	public synchronized void removeActiveUser(String id) {
		if (this.isActiveUser(id)) {
			User u = this.activeUsers.get(id);
			this.pseudoSet.remove(u.getPseudo());
			this.activeUsers.remove(id);
			this.appHandler.processUserInaction(id);
		}
	}

	public boolean isActiveUser(String identifier) {
		return this.getActiveUsers().containsKey(identifier);
	}

	public User getMainUser() {
		return mainUser;
	}

	private User findUserByPseudo(String pseudo) {
		for (User u : this.getActiveUsers().values()) {
			if (u.getPseudo().equals(pseudo)) {
				return u;
			}
		}
		return null;
	}

	/**
	 * Handles the reception of an activity signal from a distant user. Resolves conflicting pseudos following a "first chosen" priority rule.
	 *
	 * @param activeUser The user signaling his presence
	 * @return true if the user ids and pseudos have been accepted and added to the active users list, false otherwise
	 */
	public boolean processActive(User activeUser) {
		String identifier = activeUser.getIdentifier();
		String pseudo = activeUser.getPseudo();
		boolean valid = true;
		if (this.pseudoSet.contains(pseudo)) {//Resolve pseudo conflicts
			if (!isActiveUser(identifier) || !this.getActiveUsers().get(identifier).getPseudo().equals(pseudo)) {//If the user owning this pseudo isn't the one signaling
				User conflicting = this.findUserByPseudo(pseudo);
				if (conflicting.getPseudoDate().after(activeUser.getPseudoDate())) {
					if (conflicting == this.getMainUser()) {
						this.appHandler.processPseudoRejection(new PseudoRejection(pseudo, activeUser.getPseudoDate()));
					} else {
						this.removeActiveUser(conflicting.getIdentifier());
					}
				} else {
					valid = false;
				}
			}
		}
		//Add or update user
		if (valid) {
			if (!this.activeUsers.containsKey(identifier)) {//If the signaling user isn't already known by the agent introduce it and set up an inactivity timer
				this.activeUsers.put(identifier, activeUser);
				this.pseudoSet.add(pseudo);
				Timer timer = new Timer();
				ActivityTimerTask task = new ActivityTimerTask(DelayConstants.INACTIVE_DELAY_SEC, identifier, this);
				this.activityTasks.put(identifier, task);
				timer.schedule(task, 0, 1000);

			} else if (!identifier.equals(this.mainUser.getIdentifier())) {//If the user is already considered active, update pseudo if necessary and reset it's inactivity timer
				User user = this.getActiveUsers().get(identifier);
				if (!user.getPseudo().equals(pseudo)) {
					this.pseudoSet.remove(user.getPseudo());
					this.pseudoSet.add(pseudo);
					user.syncPseudo(activeUser);
				}
				this.activityTasks.get(identifier).setCounter(DelayConstants.INACTIVE_DELAY_SEC);
			}
		}
		return valid;
	}
}
