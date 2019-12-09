package org.clav.debug.graphic;

import javax.swing.*;

public class DebugFrame extends JFrame {
	DebugChatGridPanel chatGrid;
	private DebugModel model;
	public DebugFrame(DebugModel model)  {
		super("Debug CLV");
		this.model = model;
		this.setSize(1700,1000);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.chatGrid = new DebugChatGridPanel(model);
		this.setContentPane(this.chatGrid);


	}
	public void addChat(String user){
		this.chatGrid.createChat(user);
	}
	public void delChat(String user){this.chatGrid.deleteChat(user);}
	public void writeMsg(String user,String msg){
		this.chatGrid.writeMsg(user,msg);
	}
}
