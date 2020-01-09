package org.clav.ui.components;

import org.clav.chat.History;
import org.clav.ui.mvc.CLVController;
import org.clav.ui.mvc.CLVModel;
import org.clav.user.User;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Displays chats on a multi tabs environment
 * Handles linking its chat panels components to the clv application, by adding action listeners to their internal components
 */
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
			ChatPanel chatPanel = this.activeChats.get(code);
			chatPanel.getTextArea().setText(History.historyRepr(this.model.getHistoryFor(code), this.model.getActiveUsers()));
			StringBuilder sb = new StringBuilder();
			for (String id : this.model.getChatFor(code).getMembers()) {
				sb.append(this.model.getPseudoFor(id) + " | ");
			}
			chatPanel.getTitle().setText(sb.toString());
			for (int i = 0; i < this.getTabCount(); i++) {
				if (this.getComponentAt(i) == chatPanel) {
					((CloseLabel) this.getTabComponentAt(i)).getLabel().setText(sb.toString());
					break;
				}

			}
			this.revalidate();
		} else if(this.model.containsChat(code)) {
			ChatPanel chatPanel = new ChatPanel(this.componentFactory);
			StringBuilder sb = new StringBuilder();
			for (String id : this.model.getChatFor(code).getMembers()) {
				sb.append(this.model.getPseudoFor(id) + " | ");
			}
			chatPanel.getTitle().setText(sb.toString());
			chatPanel.getTextArea().setText(History.historyRepr(this.model.getHistoryFor(code), this.model.getActiveUsers()));
			chatPanel.addTypeActionListener(l -> {
				synchronized (chatPanel) {
					System.out.println("Sending message from typefield");
					this.controller.notifyMessageSending(code, chatPanel.consumeTypeField());
				}
			});
			chatPanel.setupButton(0,"STORE",e->{
				this.controller.notifyChatStorage(code);
			});
			this.activeChats.put(code, chatPanel);
			this.add(chatPanel.getTitle().getText(), chatPanel);
			ActionListener l = e -> {
				deleteChat(code);
				controller.notifyChatClosedByUser(code);
			};
			this.setTabComponentAt(this.getTabCount()-1,this.componentFactory.createTabCloseLabel(chatPanel.getTitle().getText(),l));
			this.revalidate();
		}

	}

	@Override
	public JComponent getComponent() {
		return this;
	}
}
