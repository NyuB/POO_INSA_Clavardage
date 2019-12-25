package org.clav.ui.components;

import org.clav.chat.History;
import org.clav.ui.mvc.CLVController;
import org.clav.ui.mvc.CLVModel;
import org.clav.user.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class CLVChatTabPanel extends JTabbedPane implements CLVMultiChatDisplay {
	private CLVController controller;
	private CLVModel model;
	private HashMap<String, ChatPanel> activeChats;
	private ComponentFactory componentFactory = DefaultComponentFactory.DEFAULT;

	public CLVChatTabPanel(CLVController controller, CLVModel model) {
		super();
		this.controller = controller;
		this.model = model;
		this.activeChats = new HashMap<>();

	}

	public CLVChatTabPanel(CLVController controller, CLVModel model, ComponentFactory componentFactory) {
		this(controller,model);
		this.componentFactory = componentFactory;
	}

	@Override
	public void deleteChat(String code) {
		if (this.activeChats.containsKey(code)) {
			this.remove(this.activeChats.get(code));
			this.activeChats.remove(code);
		}
	}

	@Override
	public synchronized void updateChat(String code) {
		if (this.activeChats.containsKey(code)) {
			ChatPanel chat = this.activeChats.get(code);
			chat.getTextArea().setText(History.historyRepr(this.model.getHistoryFor(code), this.model.getActiveUsers()));
			StringBuilder sb = new StringBuilder();
			for (User u : this.model.getChatFor(code).getMembers()) {
				sb.append(u.getPseudo() + " | ");
			}
			chat.getTitle().setText(sb.toString());
			for (int i = 0; i < this.getTabCount(); i++) {
				if (this.getComponentAt(i) == chat) {
					((CloseLabel) this.getTabComponentAt(i)).getLabel().setText(sb.toString());
				}
			}

			this.revalidate();
		} else {
			ChatPanel chat = new ChatPanel(this.model.getChatFor(code),this.componentFactory);
			chat.getTextArea().setText(History.historyRepr(this.model.getHistoryFor(code), this.model.getActiveUsers()));
			chat.addTypeActionListener(l -> {
				synchronized (chat) {
					System.out.println("Sending message from typefield");
					this.controller.notifyMessageSending(code, chat.consumeTypeField());
				}
			});
			this.activeChats.put(code, chat);
			this.add(chat.getTitle().getText(), chat);
			ActionListener l = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					deleteChat(code);
					controller.notifyChatClosedByUser(code);
				}
			};
			this.setTabComponentAt(this.getTabCount()-1,this.componentFactory.createTabCloseLabel(chat.getTitle().getText(),l));
			this.revalidate();
		}

	}

	@Override
	public JComponent getComponent() {
		return this;
	}
}
