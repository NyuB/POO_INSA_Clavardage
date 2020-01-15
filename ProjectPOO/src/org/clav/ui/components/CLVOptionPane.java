package org.clav.ui.components;

import org.clav.ui.mvc.CLVModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * Pure static class to handle dialogs in CLV application, following the model of JOptionPane static methods
 */
public final class CLVOptionPane {
	private CLVOptionPane(){
		//Ensure no instance of this class can be created
		assert(false);
	}
	public static ArrayList<String> showUserSelectionDialog(JFrame frame, CLVModel model,ComponentFactory factory){
		UserSelectionPanel selectionPanel = new UserSelectionPanel(model.getActiveUsers().values(),factory);
		JDialog dialog = new JDialog(frame,"Select users",true);
		dialog.setSize(500,500);
		dialog.setContentPane(new ScrollComponent<UserSelectionPanel>(selectionPanel));
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		selectionPanel.addValidateButtonAction(l->{
			dialog.dispose();
		});
		dialog.setVisible(true);
		return selectionPanel.getSelected();
	}
	
	public static void showSettingDialog(JFrame frame, CLVModel model,  ComponentFactory factory) {
		SettingPanel settingPanel = new SettingPanel(model.getConfig(), factory) ;
		JDialog dialog = new JDialog(frame,"Select Settings",true);
		dialog.setSize(500,500);
		dialog.setContentPane(new ScrollComponent<SettingPanel>(settingPanel));
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
}
