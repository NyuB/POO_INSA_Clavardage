package org.clav.chat;

import java.util.ArrayList;

public class History {
	
	private ArrayList<Message> messageHistory;
	
	public History() { 
		this.messageHistory = new ArrayList<>() ;
	}
	
	public synchronized void insertMessage(Message message){
		int n = 0 ;
		for (Message m : messageHistory) {
			if (message.getDate().after(m.getDate())) {
				n++ ;
			}
		}
		this.messageHistory.add(n, message) ;
	}

	@Override
	public String toString() {
		return this.printHistory();
	}

	public synchronized String printHistory() {
		String textHist = "" ;
		for(Message m : messageHistory) {
			textHist += m.getUserID() + " " + m.getDate()+ "\n" ;
			textHist += m.getText() + "\n" ;
		}
		return textHist;
	}

	public ArrayList<Message> getMessageHistory() {
		return messageHistory;
	}
}
