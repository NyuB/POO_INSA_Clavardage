package org.clav.debug.graphic;

import javax.swing.*;
import java.awt.*;

public class DebugFrame extends JFrame {
	DebugChatGridPanel chatGrid;
	private DebugModel model;
	public DebugFrame() throws HeadlessException {
		super("Debug CLV");
		this.setSize(1700,1000);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.chatGrid = new DebugChatGridPanel();
		this.setContentPane(this.chatGrid);


	}
	public void addChat(String user){
		this.chatGrid.createChat(user);
	}
	public void writeMsg(String user,String msg){
		this.chatGrid.writeMsg(user,msg);
	}
}
