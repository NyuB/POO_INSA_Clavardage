package org.clav.ui.components;

import org.clav.user.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class UserSelectionPanel extends JPanel {
	ArrayList<User> usersArrayList;
	public UserSelectionPanel(Collection<User> users) {
		super(new GridLayout(users.size(),2));
		this.usersArrayList = new ArrayList<>(users);
		for(User u : this.usersArrayList){
			this.add(new JCheckBox());
			this.add(new JLabel(u.getPseudo()));
		}
	}
	public ArrayList<String> getSelected(){
		ArrayList<String> selected = new ArrayList<>();
		for(int i = 0;i<this.usersArrayList.size();i++){
			JCheckBox checkBox = ((JCheckBox) this.getComponent(2*i));
			if(checkBox.isSelected()){
				selected.add(this.usersArrayList.get(i).getIdentifier());
			}

		}
		return selected;
	}
}
