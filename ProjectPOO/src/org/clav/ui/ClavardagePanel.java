package org.clav.ui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ClavardagePanel extends JPanel implements ActionListener {
	
	GridBagLayout grid;
	GridBagConstraints gdc ;
	JTextArea messenger ;
	JTextArea activeUsersPanel ;
	
	public ClavardagePanel() {
		super() ;
		this.grid = new GridBagLayout() ;
		this.gdc = new GridBagConstraints() ;
		
		//area messenger
		messenger = new JTextArea() ;
		activeUsersPanel = new JTextArea("Bob\n"+"Michel\n"+"Jean\n") ;
		
		//config area
		messenger.setFont(new Font("Serif", Font.ITALIC, 16));
		messenger.setLineWrap(true);
		messenger.setWrapStyleWord(true);
		activeUsersPanel.setFont(new Font("Serif", Font.ITALIC, 16));
		activeUsersPanel.setLineWrap(true);
		activeUsersPanel.setWrapStyleWord(true);
		
		//config display
		this.setLayout(grid) ;
		
		
		//display
		gdc.gridx = 0 ;
		gdc.gridy = 0 ;
		gdc.gridwidth = 1 ;
		gdc.gridheight = 3 ;
		gdc.weightx = 0.5 ;
		gdc.anchor= GridBagConstraints.LINE_START ;
		gdc.fill = GridBagConstraints.VERTICAL ;
		this.add(activeUsersPanel, gdc) ;
		
		
		gdc.gridx = 2 ;
		gdc.gridy = 0 ;
		gdc.gridwidth = 3 ;
		gdc.gridheight = 3 ;
		gdc.weightx = 0.5 ;
		gdc.fill = GridBagConstraints.BOTH ;
		this.add(messenger, gdc) ;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
