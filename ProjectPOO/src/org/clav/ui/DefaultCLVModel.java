package org.clav.ui;

import org.clav.AppHandler;
import org.clav.chat.Message;
import org.clav.user.User;

import java.util.ArrayList;

public class DefaultCLVModel implements CLVModel {
	private AppHandler appHandler;

	public DefaultCLVModel(AppHandler appHandler) {
		this.appHandler = appHandler;
	}

	@Override
	public void notifyMessageSending(Message message) {
		this.appHandler.sendMessage(message);
	}

	@Override
	public void notifyMessageReception(Message message) {
		this.appHandler.processMessage(message);
	}

	@Override
	public void notifyChatInitiationFromUser(ArrayList<String> identifiers) {
		//TODO
	}

	@Override
	public void notifyChatCreationFromDistant(ArrayList<String> identifiers) {
		System.out.println("[MODEL]Chat initiation whit users : ");
		for(String s : identifiers){
			System.out.println("\t"+s);
		}
	}

	@Override
	public void notifyNewActiveUser(User user) {
		System.out.println("[MODEL]New user "+user.getIdentifier()+ " | "+user.getPseudo());

	}

	@Override
	public void notifyInactiveUser(User user) {
		System.out.println("[MODEL]User "+user.getIdentifier()+" | "+user.getPseudo()+" is now inactive");

	}

	@Override
	public void notifyPseudoChange(User user) {
		System.out.println("[MODEL]Changing "+user.getIdentifier()+" pseudo to "+user.getPseudo());
	}
}
