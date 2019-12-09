package org.clav.debug.graphic;

import org.clav.Agent;
import org.clav.debug.DebugPlugin;


/**
 * Model of a debug UI. Delegate main application messages to the graphic interface and display log messages to the console
 */
public class DebugModel implements DebugPlugin {
	private Agent agent;
	public DebugFrame debugFrame;

	public void sendMsg(String distant,String message){
		this.agent.getNetworkManager().TCP_IP_send(distant,message);
	}

	public DebugModel(Agent agent) {
		this.agent = agent;
		this.debugFrame = new DebugFrame(this);
	}

	public void runFrame(){
		this.debugFrame.setVisible(true);
	}

	@Override
	public void receiveChatMessageFrom(String user, String message) {
		this.debugFrame.writeMsg(user,"@:"+message);
	}

	@Override
	public void writeChatMessageTo(String user, String message) {
		try {
			this.debugFrame.writeMsg(user, "Me:"+message);
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

	@Override
	public void detectNewUser(String identifier) {
		this.debugFrame.addChat(identifier);

	}
}
