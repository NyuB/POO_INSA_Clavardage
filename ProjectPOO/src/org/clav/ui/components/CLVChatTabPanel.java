package org.clav.ui.components;

import org.clav.chat.History;
import org.clav.ui.mvc.CLVController;
import org.clav.ui.mvc.CLVModel;
import org.clav.user.User;

import javax.swing.*;
import java.util.HashMap;

public class CLVChatTabPanel extends JTabbedPane implements CLVChatDisplayer {
	private CLVController controller;
	private CLVModel model;
	private HashMap<String,ChatPanel> activeChats;

	public CLVChatTabPanel(CLVController controller,CLVModel model) {
		super();
		this.controller = controller;
		this.model = model;
		this.activeChats = new HashMap<>();
	}

	@Override
	public void deleteChat(String code) {
		if(this.activeChats.containsKey(code)){
			this.remove(this.activeChats.get(code));
			this.activeChats.remove(code);
		}
	}

	@Override
	public void updateChat(String code) {
		if(this.activeChats.containsKey(code)){
			this.activeChats.get(code).getTextArea().setText(History.historyRepr(this.model.getHistoryFor(code),this.model.getActiveUsers()));
			StringBuilder sb = new StringBuilder();
			for(User u : this.model.getChatFor(code).getMembers()){
				sb.append(u.getPseudo()+" | ");
			}
			this.activeChats.get(code).getTitle().setText(sb.toString());
			this.revalidate();
		}
		else{
			ChatPanel chat = new ChatPanel(this.model.getChatFor(code));
			chat.getTextArea().setText(History.historyRepr(this.model.getHistoryFor(code),this.model.getActiveUsers()));
			chat.addTypeActionListener(l -> {
				synchronized (chat) {
					System.out.println("Sending message from typefield");
					this.controller.notifyMessageSending(code, chat.consumeTypeField());
				}
			});
			this.activeChats.put(code, chat);
			this.add(chat.getTitle().getText(),chat);
			this.revalidate();
		}

	}

	@Override
	public JComponent getComponent() {
		return this;
	}
}
