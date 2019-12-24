package org.clav.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CLVComponentFactory {
	private static Color MAIN_COLOR = new Color(50,150,150);
	private static Color BORDER_COLOR = new Color(100,150,150);
	private static Color TEXT_COLOR = new Color(145, 23, 255);
	private static Font MAIN_FONT = new Font("Arial",Font.PLAIN,15);
	private static Font MAIN_FONT_BOLD = new Font("Arial",Font.BOLD,15);
	private static Font MAIN_FONT_ITALIC = new Font("Arial",Font.ITALIC,15);
	private static Font MAIN_FONT_ITALIC_BOLD = new Font("Arial Bold",Font.ITALIC,15);

	public static JLabel createLabel(String title){
		JLabel label = new JLabel(title);
		label.setForeground(TEXT_COLOR);
		label.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
		label.setFont(MAIN_FONT_ITALIC);
		return label;
	}

	public static JTextArea createTextArea(){
		JTextArea textArea = new JTextArea();
		textArea.setForeground(TEXT_COLOR);
		textArea.setFont(MAIN_FONT_BOLD);
		return textArea;
	}


	public static JTextField createTextField(){
		JTextField textField = new JTextField();
		textField.setCaretColor(Color.BLUE);
		textField.setFont(MAIN_FONT_ITALIC_BOLD);
		textField.setForeground(TEXT_COLOR);
		return textField;
	}

	public static JCheckBox createCheckBox(){
		JCheckBox checkBox = new JCheckBox();
		return checkBox;
	}

	public static JButton createButton(String title){
		JButton button = new JButton(title);
		button.setBackground(MAIN_COLOR);
		button.setForeground(TEXT_COLOR);
		button.setFont(MAIN_FONT_BOLD);
		return button;
	}

	public static LabeledTickBox createLabeledTickBox(JLabel label){
		LabeledTickBox labeledTickBox = new LabeledTickBox(label);
		labeledTickBox.getLabel().setBackground(MAIN_COLOR);
		labeledTickBox.getLabel().setForeground(TEXT_COLOR);
		labeledTickBox.getLabel().setFont(MAIN_FONT_BOLD);
		return labeledTickBox;
	}
	public static CloseLabel createTabCloseLabel(String title, ActionListener l){
		CloseLabel closeLabel = new CloseLabel(createLabel(title));
		closeLabel.addCloseAction(l);
		return closeLabel;
	}


	public static Color getMainColor(){
		return MAIN_COLOR;
	}

	public static Color getTextColor() {
		return TEXT_COLOR;
	}

	public static Color getBorderColor() {
		return BORDER_COLOR;
	}

	public static Font getMainFont() {
		return MAIN_FONT;
	}

	public static Font getMainFontBold() {
		return MAIN_FONT_BOLD;
	}

	public static Font getMainFontItalic() {
		return MAIN_FONT_ITALIC;
	}

	public static Font getMainFontItalicBold() {
		return MAIN_FONT_ITALIC_BOLD;
	}

	public static void setMainColor(Color c){
		MAIN_COLOR = c;
	}
	public static void setBorderColor(Color borderColor) {
		BORDER_COLOR = borderColor;
	}

	public static void setTextColor(Color textColor) {
		TEXT_COLOR = textColor;
	}

	public static void setMainFont(Font mainFont) {
		MAIN_FONT = mainFont;
	}

	public static void setMainFontBold(Font mainFontBold) {
		MAIN_FONT_BOLD = mainFontBold;
	}

	public static void setMainFontItalic(Font mainFontItalic) {
		MAIN_FONT_ITALIC = mainFontItalic;
	}

	public static void setMainFontItalicBold(Font mainFontItalicBold) {
		MAIN_FONT_ITALIC_BOLD = mainFontItalicBold;
	}
}
