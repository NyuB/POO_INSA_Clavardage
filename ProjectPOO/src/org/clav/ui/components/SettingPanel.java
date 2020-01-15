package org.clav.ui.components;

import javax.accessibility.AccessibleText;
import javax.swing.*;

import org.clav.config.Config;

import java.awt.*;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SettingPanel extends JPanel {
	
	private JButton validateButton;
	private Config config ;
	private JTextField text1 ;
	private JTextField  text2 ;
	//private Component text3 ;
	private LabeledTickBox box1 ;
	private LabeledTickBox box2 ;
	private LabeledTickBox box3 ;
	
	
	public SettingPanel(Config configArg, ComponentFactory componentFactory) {
		super(new GridLayout(36,2));
		this.config = configArg ;
		
		this.add((componentFactory.createLabel("Local address"))) ;
		this.add(text1 = componentFactory.createTextField(config.getLocalAddr().toString())) ;
		
		this.add((componentFactory.createLabel("Broadcast address"))) ;
		this.add(text2 = componentFactory.createTextField(config.getBroadcastAddr().toString())) ;
		
		/*
		this.add((componentFactory.createLabel("User ID"))) ;
		this.add(componentFactory.createTextField(config.getUserID())) ;
		*/
		
		box1 = componentFactory.createLabeledTickBox(new JLabel("auto Signal UDP",SwingConstants.CENTER));
		System.out.println(config.isAutoSignalUDP()) ;
		box1.getCheckBox().setSelected(config.isAutoSignalUDP()) ;
		this.add(box1) ;
		
		box2 = componentFactory.createLabeledTickBox(new JLabel("auto Listen UDP",SwingConstants.CENTER));
		System.out.println(config.isAutoListenUDP()) ;
		box2.getCheckBox().setSelected(config.isAutoListenUDP()) ;
		this.add(box2) ;
		
		box3 = componentFactory.createLabeledTickBox(new JLabel("auto Listen TCP",SwingConstants.CENTER));
		System.out.println(config.isAutoListenTCP()) ;
		box3.getCheckBox().setSelected(config.isAutoListenTCP()) ;
		this.add(box3) ;
		
		this.validateButton = componentFactory.createButton("Save");
		this.addValidateButtonAction(l->saveSetting()) ;
		this.add(this.validateButton);
	}
	
	private void saveSetting() {
		try {
			String[] addr = text1.getText().split("/") ;
			System.out.println(InetAddress.getByName(addr[1])) ;
			config.setLocalAddr(InetAddress.getByName(addr[1])) ;
			config.setBroadcastAddr(InetAddress.getByName(text2.getText().split("/")[1])) ;
			//config.setBroadcastAddr(InetAddress.getByName("10.1.255.255"));
			config.setAutoSignalUDP(box1.isSelected()) ;
			config.setAutoListenUDP(box2.isSelected()) ;
			config.setAutoListenTCP(box3.isSelected()) ;
			config.save() ;
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//config.setLocalAddr(text1.getText()) ;
	}
	
	public void addValidateButtonAction(ActionListener l){
		this.validateButton.addActionListener(l);
	}

}
