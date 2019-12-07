package org.clav.debug.graphic;

import org.clav.Agent;
import org.clav.debug.DebugPlugin;

public class DebugModel implements DebugPlugin {
	private Agent agent;
	public DebugFrame debugFrame;

	public DebugModel(Agent agent) {
		this.agent = agent;
		this.debugFrame = new DebugFrame();
	}

	@Override
	public void receiveChatMessageFrom(String user, String message) {
		this.debugFrame.writeMsg(user,message);
	}

	@Override
	public void writeChatMessageTo(String user, String message) {
		try {
			this.agent.getNetworkManager().TCP_IP_send(user, message);
			this.debugFrame.writeMsg(user, message);
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public void log(String message) {
		System.out.println("[LOG]"+message);

	}

	@Override
	public void displayUsers(Iterable<String> users) {
		System.out.println("[USERLIST]");
		for(String s:users){
			System.out.println("\t"+s);
		}

	}
}
