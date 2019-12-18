package org.clav.ui;

import javax.swing.*;

public class AgentWindow extends JFrame {
	
	private String title ;
	
	public AgentWindow() {
		super("Clavardage") ;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		
		//fenetre de connect bonne dimension : 250 400
		this.setSize(400,400) ;
		this.title = "Clavardage" ;
	}	
	
}
