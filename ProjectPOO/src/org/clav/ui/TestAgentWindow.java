package org.clav.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class TestAgentWindow {
	
	public static void main(String[] args) {
		JFrame myFrame = new AgentWindow() ;
		myFrame.getContentPane().add(new ClavardagePanel(), BorderLayout.CENTER) ;
		myFrame.setVisible(true) ;
	}

}
