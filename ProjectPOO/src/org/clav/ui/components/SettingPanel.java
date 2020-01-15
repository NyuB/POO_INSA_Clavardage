package org.clav.ui.components;

import javax.swing.*;

import org.clav.config.Config;

import java.awt.*;
import java.awt.event.ActionListener;

public class SettingPanel extends JPanel {
	
	JButton validateButton;
	Config config ;
	
	private enum Setting {addressLocale, addressBroadcast, userID, autoSignalUDP, autoListenUDP, autoListenTCP} ;
	
	public SettingPanel(Config configArg, ComponentFactory componentFactory) {
		super(new GridLayout(36,2));
		this.config = configArg ;
		for (Setting setting : Setting.values()) {
			this.add((componentFactory.createLabel(setting.toString()))) ;
			this.add(componentFactory.createTextField("Test"));
		}
		this.validateButton = componentFactory.createButton("Save");
		this.add(this.validateButton);
	}

}
