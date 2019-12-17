package org.clav;

import org.clav.ui.AgentWindow;
import org.clav.ui.IdenticationPanel;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class TestAgentWindow {
	
	public static void main(String[] args) {
		JFrame myFrame = new AgentWindow() ;
		myFrame.getContentPane().add(new IdenticationPanel(), BorderLayout.CENTER) ;
		//myFrame.setContentPane(new ChatPanel());
		myFrame.setVisible(true) ;
	}

}
