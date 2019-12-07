package org.clav.debug;

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
}