package org.clav.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DefaultComponentFactory implements ComponentFactory {
	private static Font defaultFont = new Font("Georgia",Font.PLAIN,15);
	private DefaultComponentFactory(){

	}
	public static DefaultComponentFactory DEFAULT = new DefaultComponentFactory();
	@Override
	public JLabel createLabel(String title) {
		return new JLabel(title);
	}

	@Override
	public JTextArea createTextArea() {
		return new JTextArea();
	}

	@Override
	public JTextField createTextField() {
		return new JTextField();
	}

	@Override
	public JCheckBox createCheckBox() {
		return new JCheckBox();
	}

	@Override
	public JButton createButton(String title) {
		return new JButton(title);
	}

	@Override
	public JButton createCloseButton() {
		return new JButton("X");
	}

	@Override
	public LabeledTickBox createLabeledTickBox(JLabel label) {
		return new LabeledTickBox(label);
	}

	@Override
	public CloseLabel createTabCloseLabel(String title, ActionListener l) {
		CloseLabel closeLabel = new CloseLabel(new JLabel(title),createCloseButton());
		closeLabel.addCloseAction(l);
		return closeLabel;
	}

	@Override
	public Color getMainColor() {
		return Color.BLUE;
	}

	@Override
	public void setMainColor(Color mainColor) {
		//TODO

	}

	@Override
	public Font getMainFontItalicBold() {
		//TODO
		return defaultFont;
	}

	@Override
	public void setMainFontItalicBold(Font mainFontItalicBold) {
		//TODO

	}

	@Override
	public Color getDangerColor() {
		//TODO
		return Color.RED;
	}

	@Override
	public void setDangerColor(Color dangerColor) {
		//TODO

	}

	@Override
	public Color getBorderColor() {
		//TODO
		return Color.BLACK;
	}

	@Override
	public void setBorderColor(Color borderColor) {
		//TODO

	}

	@Override
	public Color getTextColor() {
		//TODO
		return Color.BLACK;
	}

	@Override
	public void setTextColor(Color textColor) {
		//TODO

	}

	@Override
	public Font getMainFont() {
		//TODO
		return defaultFont;
	}

	@Override
	public void setMainFont(Font mainFont) {
		//TODO

	}

	@Override
	public Font getMainFontBold() {
		//TODO
		return defaultFont;
	}

	@Override
	public void setMainFontBold(Font mainFontBold) {
		//TODO

	}

	@Override
	public Font getMainFontItalic() {
		//TODO
		return defaultFont;
	}

	@Override
	public void setMainFontItalic(Font mainFontItalic) {
		//TODO

	}
}
