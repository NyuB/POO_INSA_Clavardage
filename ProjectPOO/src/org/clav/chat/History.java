package org.clav.chat;

import org.clav.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class History {

	private ArrayList<Message> messageHistory;

	public History() {
		this.messageHistory = new ArrayList<>();
	}

	public static String historyRepr(History history, HashMap<String, User> activeMap) {
		StringBuilder sb = new StringBuilder();
		synchronized (history) {
			for (Message m : history.getMessageHistory()) {
				sb.append(((activeMap.containsKey(m.getUserID())) ? activeMap.get(m.getUserID()).getPseudo() : m.getUserID()) + " " + m.getDate() + "\n");
				sb.append(m.getText() + "\n");
			}
			return sb.toString();
		}
	}

	public synchronized void insertMessage(Message message) {
		int n = 0;
		for (Message m : messageHistory) {
			if (message.getDate().after(m.getDate())) {
				n++;
			}
		}
		this.messageHistory.add(n, message);
	}

	@Override
	public String toString() {
		return this.printHistory();
	}

	public synchronized String printHistory() {
		String textHist = "";
		for (Message m : messageHistory) {
			textHist += m.getUserID() + " " + m.getDate() + "\n";
			textHist += "\t" + m.getText() + "\n";
		}
		return textHist;
	}

	public ArrayList<Message> getMessageHistory() {
		return messageHistory;
	}
}
