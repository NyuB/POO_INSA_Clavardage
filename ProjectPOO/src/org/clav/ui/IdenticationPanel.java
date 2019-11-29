package org.clav.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IdenticationPanel extends JPanel implements ActionListener {
	
	//cf gridLayout
	//cf gridBagLayout
	
	GridBagLayout grid;
	GridBagConstraints gdc ;
	JButton button ;
	JLabel labelID ;
	JLabel labelPS ;
	TextField login ;
	TextField pseudo ;
	
	public IdenticationPanel() {
		super() ;
		
		//creation components
		this.button= new JButton("connect") ; 
		this.grid = new GridBagLayout() ;
		this.gdc = new GridBagConstraints() ;
		this.login = createTextField("");
		this.pseudo = createTextField("");
		this.labelID = createLabel("Identifiant : ") ;;
		this.labelPS = createLabel("Pseudo: ") ;
		
		//action listenner connect
		button.addActionListener(this) ;
		
		//config display
		this.setLayout(grid) ;
		
		//c1
		gdc.gridx = 0 ;
		gdc.gridy = 0 ;
		gdc.weightx = 0.5;
		gdc.anchor= GridBagConstraints.LINE_END ;
		this.add(labelID,gdc) ;
		
		//c2
		gdc.weightx = 0.5 ;
		gdc.anchor= GridBagConstraints.LINE_START ;
		gdc.gridx = GridBagConstraints.RELATIVE ;
		this.add(login, gdc) ;
		
		//c3
		gdc.weightx = 0.0 ;
		gdc.gridx = 0;
		gdc.gridy = 1;
		gdc.anchor= GridBagConstraints.LINE_END ;
		this.add(labelPS, gdc) ;
		
		//c4
		gdc.weightx = 0.5 ;
		gdc.gridx = GridBagConstraints.RELATIVE ;;
		gdc.anchor= GridBagConstraints.LINE_START ;
		gdc.gridy = 1;
		this.add(pseudo, gdc) ;
		
		//c5
		gdc.gridwidth = 2 ;
		gdc.anchor= GridBagConstraints.CENTER ;
		gdc.weightx = 0.0 ;
		gdc.gridx = 0;
		gdc.gridy = 2 ;
		this.add(button, gdc) ;
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("Hello World !") ;
	}
	
	public TextField createTextField(String text) {
		TextField field = new TextField() ;
		field.setColumns(12) ;
		field.setText(text) ;
		return field ;
	}
	
	public JLabel createLabel(String label) {
		JLabel myLabel = new JLabel() ;
		myLabel.setText(label) ;
		return myLabel ;
	}
	

}
