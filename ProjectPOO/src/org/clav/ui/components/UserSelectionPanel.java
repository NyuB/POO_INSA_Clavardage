package org.clav.ui.components;

import org.clav.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

public class UserSelectionPanel extends JPanel {
	ArrayList<User> usersArrayList;
	JButton validateButton;
	public UserSelectionPanel(Collection<User> users) {
		super(new GridLayout(users.size()+1+30,1));
		this.usersArrayList = new ArrayList<>(users);
		for(User u : this.usersArrayList){
			this.add(new LabeledTickBox(new JLabel(u.getPseudo(),SwingConstants.CENTER)));
		}
		for(int i = 0;i<30;i++){
			this.add(new JButton("FILLER"));
		}
		this.validateButton = new JButton("V");
		this.add(this.validateButton);
	}
	public ArrayList<String> getSelected(){
		ArrayList<String> selected = new ArrayList<>();
		for(int i = 0;i<this.usersArrayList.size();i++){
			LabeledTickBox checkBox = ((LabeledTickBox) this.getComponent(i));
			if(checkBox.isSelected()){
				selected.add(this.usersArrayList.get(i).getIdentifier());
			}

		}
		return selected;
	}
	public void addValidateButtonAction(ActionListener l){
		this.validateButton.addActionListener(l);
	}
}
