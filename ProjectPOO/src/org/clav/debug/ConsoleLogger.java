package org.clav.debug;


/**
 * Basic debug module equivalent to a decorated System.out.println
 */
public class ConsoleLogger implements DebugPlugin {
	@Override
	public void receiveChatMessageFrom(String user, String message) {
		System.out.println("[MSG][RCV]["+user+"]"+message);
	}

	@Override
	public void writeChatMessageTo(String user, String message) {
		System.out.println("[MSG][SND]["+user+"]"+message);
	}

	@Override
	public void log(String message) {
		System.out.println("[LOG]"+message);

	}

	@Override
	public void displayUsers(Iterable<String> users) {
		System.out.println("[ACTIVEUSERS]");
		for(String s : users){
			System.out.println("\t"+s);
		}

	}

	@Override
	public void detectNewUser(String identifier) {
		System.out.println("[USER]New User detected : "+identifier);


	}

	@Override
	public void detectDisconnection(String identifier) {
		System.out.println("[USER]"+identifier+"disconnected");

	}
}
