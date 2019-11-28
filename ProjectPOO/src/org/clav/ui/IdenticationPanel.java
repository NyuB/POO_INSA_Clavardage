package org.clav.ui;

import java.awt.Component;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IdenticationPanel extends JPanel {
	
	//cf gridLayout
	//cf gridBagLayout
	
	JButton button ;
	JLabel labelID ;
	JLabel labelPS ;
	TextField login ;
	TextField pseudo ;
	
	public IdenticationPanel() {
		super() ;
		this.button= new JButton("connect") ; 
		this.login = createTextField("");
		this.pseudo = createTextField("");
		this.labelID = createLabel("Identifiant : ") ;
		this.labelPS = createLabel("Pseudo: ") ;
		this.add(labelID) ;
		this.add(login) ;
		this.add(labelPS) ;
		this.add(pseudo) ;
		this.add(button) ;
	}
	
	TextField createTextField(String text) {
		TextField field = new TextField() ;
		field.setColumns(12) ;
		field.setText(text) ;
		return field ;
	}
	
	JLabel createLabel(String label) {
		JLabel myLabel = new JLabel() ;
		myLabel.setText(label) ;
		return myLabel ;
	}
	

}
