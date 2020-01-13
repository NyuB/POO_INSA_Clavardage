package org.clav.ui;

import org.clav.AppHandler;
import org.clav.chat.Chat;
import org.clav.ui.components.CLVComponentFactory;
import org.clav.ui.mvc.*;

import javax.swing.*;
import java.awt.*;


public class GUIManager {
	private AppHandler appHandler;
	private CLVModel model;
	private CLVView view;
	private CLVController controller;

	public GUIManager(AppHandler appHandler, CLVModel model) {
		this.appHandler = appHandler;
		this.model = model;
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		this.controller = new DefaultCLVController(appHandler,model);
		this.view = new CLVFrame(controller,model,CLVComponentFactory.DEFAULT_FACTORY);
	}

	public void start(){
		this.view.turnOn();
		this.view.refreshAll();
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
