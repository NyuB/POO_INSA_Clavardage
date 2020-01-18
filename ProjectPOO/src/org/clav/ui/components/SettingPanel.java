package org.clav.ui.components;

import org.clav.config.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SettingPanel extends JPanel {
	
	private JButton validateButton;
	private Config config ;
	private JTextField localAddrField;
	private JTextField broadcastAddrField;
	private JTextField serverUrlField;
	private LabeledTickBox udpSignalBox;
	private LabeledTickBox udpListenBox;
	private LabeledTickBox tcpListenBox;
	private LabeledTickBox presenceServerBox;
	
	
	public SettingPanel(Config configArg, ComponentFactory componentFactory) {
		super(new GridLayout(36,2));
		this.config = configArg ;
		
		this.add(componentFactory.createLabel("Local address")) ;
		this.add(localAddrField = componentFactory.createTextField(config.getLocalAddr().getHostAddress())) ;
		
		this.add(componentFactory.createLabel("Broadcast address")) ;
		this.add(broadcastAddrField = componentFactory.createTextField(config.getBroadcastAddr().getHostAddress())) ;

		this.add(componentFactory.createLabel("Remote presence server url"));
		this.add(serverUrlField = componentFactory.createTextField(config.getServerUrl()));
		udpSignalBox = componentFactory.createLabeledTickBox(new JLabel("[*]Signal presence to other users",SwingConstants.CENTER));
		udpSignalBox.getCheckBox().setSelected(config.isAutoSignalUDP()) ;
		this.add(udpSignalBox) ;
		
		udpListenBox = componentFactory.createLabeledTickBox(new JLabel("[*]Listen other users signals",SwingConstants.CENTER));
		udpListenBox.getCheckBox().setSelected(config.isAutoListenUDP()) ;
		this.add(udpListenBox) ;
		
		tcpListenBox = componentFactory.createLabeledTickBox(new JLabel("[*]Accept new TCP connections(messages)",SwingConstants.CENTER));
		tcpListenBox.getCheckBox().setSelected(config.isAutoListenTCP()) ;
		this.add(tcpListenBox) ;

		this.presenceServerBox = componentFactory.createLabeledTickBox(new JLabel("Use remote presence server",SwingConstants.CENTER));
		presenceServerBox.getCheckBox().setSelected(config.isAutoConnectServlet());
		this.add(presenceServerBox);

		this.validateButton = componentFactory.createButton("Save(options marked with a * are applied only when restarting the application))");
		this.addValidateButtonAction(l->saveSetting()) ;
		this.add(this.validateButton);
	}
	
	private void saveSetting() {
		try {
			config.setLocalAddr(InetAddress.getByName(localAddrField.getText()));
			config.setBroadcastAddr(InetAddress.getByName(broadcastAddrField.getText()));
			config.setServerUrl(serverUrlField.getText());
			config.setAutoSignalUDP(udpSignalBox.isSelected()) ;
			config.setAutoListenUDP(udpListenBox.isSelected()) ;
			config.setAutoListenTCP(tcpListenBox.isSelected()) ;
			config.setAutoConnectServlet(presenceServerBox.isSelected());
			config.save() ;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void addValidateButtonAction(ActionListener l){
		this.validateButton.addActionListener(l);
	}

}
