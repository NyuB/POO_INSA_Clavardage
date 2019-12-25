package org.clav.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CLVComponentFactory implements ComponentFactory {
	private static Color MAIN_COLOR = new Color(50,150,150);
	private static Color DANGER_COLOR = new Color(219, 22, 63);
	private static Color BORDER_COLOR = new Color(100,150,150);
	private static Color TEXT_COLOR = new Color(145, 23, 255);

	private static Font MAIN_FONT = new Font("Calibri",Font.PLAIN,15);
	private static Font MAIN_FONT_BOLD = new Font("Calibri",Font.BOLD,15);
	private static Font MAIN_FONT_ITALIC = new Font("Calibri",Font.ITALIC,15);
	private static Font MAIN_FONT_ITALIC_BOLD = new Font("Calibri Bold",Font.ITALIC,15);

	private Color mainColor;
	private Color dangerColor;
	private Color borderColor;
	private Color textColor;
	private Font mainFont;
	private Font mainFontBold;
	private Font mainFontItalic;
	private Font mainFontItalicBold;

	public CLVComponentFactory(Color mainColor, Color dangerColor, Color borderColor, Color textColor, Font mainFont, Font mainFontBold, Font mainFontItalic, Font mainFontItalicBold) {
		this.mainColor = mainColor;
		this.dangerColor = dangerColor;
		this.borderColor = borderColor;
		this.textColor = textColor;
		this.mainFont = mainFont;
		this.mainFontBold = mainFontBold;
		this.mainFontItalic = mainFontItalic;
		this.mainFontItalicBold = mainFontItalicBold;
	}

	public static CLVComponentFactory DEFAULT_FACTORY = new CLVComponentFactory(MAIN_COLOR,DANGER_COLOR,BORDER_COLOR,TEXT_COLOR,MAIN_FONT,MAIN_FONT_BOLD,MAIN_FONT_ITALIC,MAIN_FONT_ITALIC_BOLD);



	@Override
	public JLabel createLabel(String title){
		JLabel label = new JLabel(title,SwingConstants.CENTER);
		label.setForeground(this.textColor);
		label.setBackground(this.mainColor);
		label.setBorder(BorderFactory.createLineBorder(this.borderColor));
		label.setFont(this.mainFontItalic);
		return label;
	}

	@Override
	public JTextArea createTextArea(){
		JTextArea textArea = new JTextArea();
		textArea.setForeground(this.textColor);
		textArea.setFont(this.mainFontBold);
		return textArea;
	}


	@Override
	public JTextField createTextField(){
		JTextField textField = new JTextField();
		textField.setCaretColor(Color.BLUE);
		textField.setFont(MAIN_FONT_ITALIC_BOLD);
		textField.setForeground(this.textColor);
		return textField;
	}

	@Override
	public  JCheckBox createCheckBox(){
		JCheckBox checkBox = new JCheckBox();
		checkBox.setForeground(this.mainColor);
		return checkBox;
	}

	@Override
	public  JButton createButton(String title){
		JButton button = new JButton(title);
		button.setBackground(this.mainColor);
		button.setForeground(this.textColor);
		button.setFont(this.mainFontBold);
		return button;
	}
	@Override
	public  JButton createCloseButton(){
		JButton button = new JButton("X");
		button.setBackground(this.dangerColor);
		button.setForeground(this.textColor);
		button.setFont(this.mainFontBold);
		return button;
	}

	@Override
	public  LabeledTickBox createLabeledTickBox(JLabel label){
		LabeledTickBox labeledTickBox = new LabeledTickBox(label);
		labeledTickBox.getLabel().setBackground(this.mainColor);
		labeledTickBox.getLabel().setForeground(this.textColor);
		labeledTickBox.getLabel().setFont(this.mainFontBold);
		return labeledTickBox;
	}
	@Override
	public  CloseLabel createTabCloseLabel(String title, ActionListener l){
		CloseLabel closeLabel = new CloseLabel(createLabel(title),createCloseButton());
		closeLabel.addCloseAction(l);
		return closeLabel;
	}


	@Override
	public Color getMainColor() {
		return mainColor;
	}

	@Override
	public void setMainColor(Color mainColor) {
		this.mainColor = mainColor;
	}

	@Override
	public Font getMainFontItalicBold() {
		return mainFontItalicBold;
	}

	@Override
	public void setMainFontItalicBold(Font mainFontItalicBold) {
		this.mainFontItalicBold = mainFontItalicBold;
	}

	@Override
	public Color getDangerColor() {
		return dangerColor;
	}

	@Override
	public void setDangerColor(Color dangerColor) {
		this.dangerColor = dangerColor;
	}

	@Override
	public Color getBorderColor() {
		return borderColor;
	}

	@Override
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	@Override
	public Color getTextColor() {
		return textColor;
	}

	@Override
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	@Override
	public Font getMainFont() {
		return mainFont;
	}

	@Override
	public void setMainFont(Font mainFont) {
		this.mainFont = mainFont;
	}

	@Override
	public Font getMainFontBold() {
		return mainFontBold;
	}

	@Override
	public void setMainFontBold(Font mainFontBold) {
		this.mainFontBold = mainFontBold;
	}

	@Override
	public Font getMainFontItalic() {
		return mainFontItalic;
	}

	@Override
	public void setMainFontItalic(Font mainFontItalic) {
		this.mainFontItalic = mainFontItalic;
	}
}
