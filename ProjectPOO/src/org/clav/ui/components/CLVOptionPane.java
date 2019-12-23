package org.clav.ui.components;

import org.clav.ui.mvc.CLVModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class CLVOptionPane {
	public static ArrayList<String> showUserSelectionDialog(JFrame frame, CLVModel model){
		UserSelectionPanel selectionPanel = new UserSelectionPanel(model.getActiveUsers().values());
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
}
