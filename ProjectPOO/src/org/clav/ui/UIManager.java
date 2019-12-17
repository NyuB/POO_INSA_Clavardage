package org.clav.ui;

import org.clav.AppHandler;
import org.clav.chat.Chat;
import org.clav.ui.mvc.*;

public class UIManager {
	private AppHandler appHandler;
	private CLVModel model;
	private CLVView view;
	private CLVController controller;

	public UIManager(AppHandler appHandler,CLVModel model) {
		this.appHandler = appHandler;
		this.model = model;
		this.controller = new DefaultCLVController(appHandler,model);
		this.view = new CLVFrame(controller,model);
	}

	public void start(){
		this.view.turnOn();
		//Notify the view of each initial chat
		for(Chat chat:this.getModel().getActiveChats().values()){
			this.view.refreshChat(chat.getChatHashCode());
		}
	}

	public CLVModel getModel() {
		return model;
	}

	public CLVView getView() {
		return view;
	}

	public CLVController getController() {
		return controller;
	}
}
