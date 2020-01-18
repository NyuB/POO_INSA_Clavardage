package org.clav;

import org.clav.config.Installer;

public class CLVApp {
	public static void main(String[] args) {
		new Installer().install();
		Agent.launchAgent();
		while(true);
	}
}
