package org.clav.user;

import org.clav.AppHandler;

import java.util.TimerTask;

public class ActivityTimerTask extends TimerTask {
	private Integer counter;
	private UserManager userManager;

	public ActivityTimerTask(int counter, UserManager userManager) {
		this.counter = counter;
		this.userManager = userManager;
	}

	@Override
	public void run() {
		synchronized (this.counter) {
			if (this.counter > 0) {
				this.counter--;
				System.out.println("[TIM]Counting " + this.counter);
			} else {
				System.out.println("[TIM]EXPIRATION");//TODO implement inactive management
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
