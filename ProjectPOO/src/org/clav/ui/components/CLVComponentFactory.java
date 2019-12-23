package org.clav.ui.components;

import javax.swing.*;
import java.awt.*;

public final class CLVComponentFactory {
	private CLVComponentFactory() {
		assert(false);
	}
	public static JTextArea createTextArea(){
		JTextArea textArea = new JTextArea();
		textArea.setForeground(CLVColors.CLV_DEFAULT_COLOR);
		return textArea;
	}
	public static JTextField createTextField(){
		JTextField textField = new JTextField();
		textField.setCaretColor(Color.BLUE);
		textField.setFont(new Font("Arial",Font.BOLD,15));
		textField.setForeground(CLVColors.CLV_DEFAULT_COLOR);
		return textField;
	}
	public static JCheckBox createCheckBox(){
		JCheckBox checkBox = new JCheckBox();
		return checkBox;
	}
	public static JButton createButton(String title){
		JButton button = new JButton(title);
		button.setBackground(CLVColors.CLV_DEFAULT_COLOR);
		return button;
	}
}
