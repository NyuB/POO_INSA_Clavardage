package org.clav.ui.mvc;

import org.clav.AppHandler;
import org.clav.chat.Message;
import org.clav.user.User;

import java.util.ArrayList;

public class DefaultCLVController implements CLVController {
	private AppHandler appHandler;
	private CLVModel model;
	private CLVView view;



	private void log(String txt) {
		System.out.println("[CTRL]" + txt);
	}

	public DefaultCLVController(AppHandler appHandler, CLVModel model) {
		this.appHandler = appHandler;
		this.model = model;
	}

	@Override
	public void assignView(CLVView view) {
		this.view = view;
	}

	@Override
	public void notifyMessageSending(String code, String txt) {
		for (User u : this.model.getChatFor(code).getMembers()) {
			this.appHandler.sendMessage(new Message(this.appHandler.getMainUser().getIdentifier(), code, txt));
		}
	}

	@Override
	public void notifyMessageReception(Message message) {
		this.log("Message received");
		this.view.refreshChat(message.getChatHashCode());
	}

	@Override
	public void notifyChatInitiationFromUser(ArrayList<String> identifiers) {
		ArrayList<User> userArrayList = new ArrayList<>();
		for (String s : identifiers) {
			userArrayList.add(this.model.getActiveUsers().get(s));
		}
		this.appHandler.initiateChat(userArrayList);

	}

	@Override
	public void notifyChatInitiationFromDistant(String code) {
		this.log("Chat initiation with users : ");
		for (User u : this.model.getChatFor(code).getMembers()) {
			System.out.println("\t" + u.getIdentifier());
		}
		this.view.refreshChat(code);
	}

	@Override
	public void notifyNewActiveUser(User user) {
		//this.log("New user " + user.getIdentifier() + " | " + user.getPseudo());
		this.view.refreshUsers();

	}

	@Override
	public void notifyInactiveUser(User user) {
		this.log("User " + user.getIdentifier() + " | " + user.getPseudo() + " is now inactive");
		this.view.refreshUsers();

	}

	@Override
	public void notifyPseudoChangeFromDistant(User user) {
		System.out.println("[MODEL]Changing " + user.getIdentifier() + " pseudo to " + user.getPseudo());
	}

	@Override
	public void notifyMainUserPseudoChange(String pseudo) {
		this.log("Main user changed pseudo to " + pseudo);
	}

	@Override
	public void notifyMainUserPseudoRejected(String old) {
		this.log("Rejection of pseudo change");
	}
}
