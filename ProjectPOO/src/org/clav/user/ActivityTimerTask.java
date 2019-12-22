package org.clav.user;

import java.util.TimerTask;

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
				this.userManager.removeUser(this.id);
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
