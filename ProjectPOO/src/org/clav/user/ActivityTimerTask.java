package org.clav.user;

import java.util.TimerTask;

/**
 * Set up a counter on an user id, and notify the related UserManager by calling the removeActiveUser methods when the counter reaches 0
 * The counter can be dynamically reset from outside the thread, without having to interrupt and restart it
 */
public class ActivityTimerTask extends TimerTask {
	private Integer counter;
	private String id;
	private UserManager userManager;

	public ActivityTimerTask(int counter,String id, UserManager userManager) {
		this.counter = counter;
		this.id = id;
		this.userManager = userManager;
	}

	@Override
	public void run() {
		synchronized (this.counter) {
			if (this.counter > 0) {
				this.counter--;
				//System.out.println("[TIM]Counting " + this.counter);
			} else {
				System.out.println("[TIM]Expiration user "+this.id);
				this.userManager.removeActiveUser(this.id);
				cancel();
			}
		}

	}

	public void setCounter(Integer counter) {
		synchronized (this.counter) {
			this.counter = counter;
		}
	}
}
