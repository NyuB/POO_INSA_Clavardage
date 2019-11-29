package org.clav.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class ClavardagePanel extends JPanel implements ActionListener {
	
	GridBagLayout grid;
	GridBagConstraints gdc ;
	TextArea messenger ;
	TextArea activeUsersPanel ;
	
	public ClavardagePanel() {
		super() ;
		this.grid = new GridBagLayout() ;
		this.gdc = new GridBagConstraints() ;
		
		//area messenger
		messenger = new TextArea() ;
		activeUsersPanel = new TextArea() ;
		
		//config display
		this.setLayout(grid) ;
		
		
		//display
		gdc.gridx = 0 ;
		gdc.gridy = 0 ;
		gdc.gridheight = 3 ;
		gdc.fill = GridBagConstraints.VERTICAL ;
		this.add(messenger) ;
		
		gdc.gridx = 1 ;
		gdc.gridy = 0 ;
		gdc.gridwidth = 3 ;
		gdc.gridheight = 3 ;
		gdc.fill = GridBagConstraints.BOTH ;
		this.add(activeUsersPanel) ;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
